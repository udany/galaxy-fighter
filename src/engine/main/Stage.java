package engine.main;

import engine.base.Size;
import engine.input.Keyboard;
import engine.util.Event;
import engine.window.Game;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class Stage {
    public Stage() {
        this.size = game.size;
        objectList = new ArrayList<>();
    }

    protected UUID id = UUID.randomUUID();
    protected Game game;
    public Game getGame() { return game; }
    public long getGameTime() { return game.getGameTime(); }

    public Size size;
    public Event<Graphics2D> onPaint = new Event<>();
    public Event<Long> onUpdate = new Event<>();
    public Event<Game> onAdd = new Event<Game>().addListener(g -> {
        game = g;
        bindToKeyboard();
        unpause();
    });
    public Event<Game> onRemove = new Event<Game>().addListener(g -> {
        game = null;
        unbindToKeyboard();
        pause();
    });


    public Event<Integer> onKeyDown = new Event<>();
    public Event<Integer> onKeyUp = new Event<>();

    protected void bindToKeyboard() {
        Keyboard kb = Keyboard.getInstance();

        kb.onKeyDown.addListener(key -> {
            onKeyDown.emit(key);
        }, id.toString());

        kb.onKeyUp.addListener(key -> {
            onKeyUp.emit(key);
        }, id.toString());
    }

    protected void unbindToKeyboard() {
        Keyboard kb = Keyboard.getInstance();

        kb.onKeyDown.removeListener(id.toString());
        kb.onKeyUp.removeListener(id.toString());
    }

    private QuadTree tree;
    private List<GameObject> objectList;

    public void start() {
        tree = new QuadTree(size.width, size.height);
    }

    protected boolean paused = false;
    public Stage pause() {
        paused = true;
        return this;
    }
    public Stage unpause() {
        paused = false;
        return this;
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
        objectsToAdd.forEach(obj -> obj.onRemove.emit(this));

        objectsToRemove = objectsToRemove.stream().filter(GameObject::isSolid).collect(Collectors.toList());
        tree.removeAll(objectsToRemove);

        objectList.addAll(objectsToAdd);
        tree.insertAll(objectsToAdd.stream().filter(GameObject::isSolid).collect(Collectors.toList()));
        objectsToAdd.forEach(obj -> obj.onAdd.emit(this));
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
