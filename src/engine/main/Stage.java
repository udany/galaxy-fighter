package engine.main;

import engine.base.Size;
import engine.util.Event;
import engine.window.Game;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Stage {
    public Stage(Game game) {
        this.game = game;
        this.size = game.size;
        objectList = new ArrayList<>();
    }

    protected Game game;
    public Game getGame() { return game; }
    public long getGameTime() { return game.getGameTime(); }

    public Size size;
    public Event<Graphics2D> onPaint = new Event<>();
    public Event<Long> onUpdate = new Event<>();
    private QuadTree tree;
    private List<GameObject> objectList;

    public void start() {
        tree = new QuadTree(size.width, size.height);
    }

    protected boolean paused = false;
    public void pause() {
        paused = true;
    }
    public void unpause() {
        paused = false;
    }

    public void draw(Graphics2D graphics){
        AffineTransform baseTransform = graphics.getTransform();
        AffineTransform currentTransform;

        for(GameObject o : objectList){
            o.draw(graphics);

            currentTransform = graphics.getTransform();
            if (!currentTransform.equals(baseTransform)) {
                graphics.setTransform(baseTransform);
            }
        }

        onPaint.emit(graphics);
    }

    protected List<GameObject> objectsToAdd = new ArrayList<>();
    public void addObject(GameObject obj) {
        objectsToAdd.add(obj);
    }

    public void update(long elapsedMs){
        if (paused) return;

        List<GameObject> objectsToRemove = objectList.stream().filter(GameObject::isDestroyed).collect(Collectors.toList());
        objectList.removeAll(objectsToRemove);

        objectsToRemove = objectsToRemove.stream().filter(GameObject::isSolid).collect(Collectors.toList());
        tree.removeAll(objectsToRemove);

        objectList.addAll(objectsToAdd);
        tree.insertAll(objectsToAdd.stream().filter(GameObject::isSolid).collect(Collectors.toList()));
        objectsToAdd.clear();

        for(GameObject o : objectList){
            o.update(((double)elapsedMs) / 1000);

            if (o.hasMoved() && o.isSolid()) {
                tree.update(o);
            }
        }

        tree.prune();
        onUpdate.emit(elapsedMs);
        collisionChecking();
    }

    protected void collisionChecking(){
        List<GameObject> objectsToCheck = objectList.stream().filter(GameObject::checkForCollisions).collect(Collectors.toList());
        List<GameObject> potentialCollisions;

        for (GameObject obj : objectsToCheck){
            potentialCollisions = tree.retrieve(obj);

            for (GameObject solidObj : potentialCollisions){
                Shape objShape = obj.getCollisionArea();
                Shape solidShape = solidObj.getCollisionArea();

                if (objShape.intersects(solidShape.getBounds())){
                    obj.onCollision.emit(solidObj);
                }
            }
        }
    }
}
