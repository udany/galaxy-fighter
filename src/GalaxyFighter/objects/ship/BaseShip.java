package GalaxyFighter.objects.ship;

import engine.base.Vector;
import engine.graphics.Palette;
import engine.graphics.Sprite;
import engine.input.Keyboard;
import engine.main.MotionObject;
import engine.window.Game;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

public class BaseShip extends MotionObject {

    private Sprite engineFire;
    private double baseSpeed;
    private double brakeRatio;
    private Game game;

    public BaseShip(Game game) {
        this.game = game;

        debug = true;

        baseSpeed = 3.0;
        brakeRatio = 0.7;

        size.set(16, 32);

        currentSprite = new Sprite(64, 48, "/images/Ship_Edgeliner.png");
        currentSprite.origin.set(23,8);

        currentSprite.setBasePalette(new Palette("/images/Ship_Edgeliner.palette.0.png"));

        List<Palette> paletteList = new ArrayList<>();
        paletteList.add(new Palette("/images/Ship_Edgeliner.palette.1.png"));

        currentSprite.applyPalette(paletteList.get(0));


        engineFire = new Sprite(16, 16, "/images/EngineFire.png");
        engineFire.setFramesPerFrame(3);

        /// KeyEvents
        Keyboard kb = Keyboard.getInstance();
        kb.onKeyDown.addListener(code -> {
            switch (code){
                case KeyEvent.VK_LEFT:
                case KeyEvent.VK_RIGHT:
                case KeyEvent.VK_UP:
                case KeyEvent.VK_DOWN:
                    movementKeyDown(code);
                    break;
            }
        });
        kb.onKeyUp.addListener(code -> {
            switch (code){
                case KeyEvent.VK_LEFT:
                case KeyEvent.VK_RIGHT:
                case KeyEvent.VK_UP:
                case KeyEvent.VK_DOWN:
                    movementKeyUp(code);
                    break;
                case KeyEvent.VK_ENTER:
                    currentSprite.setState(currentSprite.state+1);
                    break;
            }
        });
    }


    private List<Integer> keys = new ArrayList<>();
    private void movementKeyDown(int key){
        keys.add(key);
    }
    private void movementKeyUp(int key){
        int idx = keys.indexOf(key);
        if (idx >= 0){
            keys.remove(idx);
        }
    }


    @Override
    public void update() {
        int idxUp = keys.indexOf(KeyEvent.VK_UP);
        int idxDown = keys.indexOf(KeyEvent.VK_DOWN);
        int idxLeft = keys.indexOf(KeyEvent.VK_LEFT);
        int idxRight = keys.indexOf(KeyEvent.VK_RIGHT);

        if (idxUp >= 0 || idxDown >= 0) {
            acceleration.y = 0;

            if (Math.max(idxDown, idxUp) == idxUp) {
                speed.y = -baseSpeed;
            } else {
                speed.y = baseSpeed;
            }
        } else {
            speed.y = speed.y * brakeRatio;
        }

        if (idxLeft >= 0 || idxRight >= 0) {
            acceleration.x = 0;

            if (Math.max(idxLeft, idxRight) == idxLeft) {
                speed.x = -baseSpeed;
            } else {
                speed.x = baseSpeed;
            }
        } else {
            speed.x = speed.x * brakeRatio;
        }

        super.update();

        //if (this.)
    }


    @Override
    public void draw(Graphics2D graphics) {
        super.draw(graphics);

        engineFire.animate(true).draw(graphics, position.clone().add(0, 30));
    }
}
