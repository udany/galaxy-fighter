package GalaxyFighter.objects.ship;

import GalaxyFighter.objects.util.HP;
import engine.base.Vector;
import engine.graphics.Color;
import engine.input.Keyboard;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.geom.AffineTransform;

abstract public class PlayerShip extends BaseShip {

    public PlayerShip() {
        hp = new HP(100);
        hp.size.set(500, 8);
        hp.setBackground(new Color(0, 0, 0, 120));
        hp.current = 50;

        /// KeyEvents
        Keyboard kb = Keyboard.getInstance();
        kb.onKeyDown.addListener(code -> {
            switch (code) {
                case KeyEvent.VK_LEFT:
                case KeyEvent.VK_RIGHT:
                case KeyEvent.VK_UP:
                case KeyEvent.VK_DOWN:
                    movementKeyDown(code);
                    break;
                case KeyEvent.VK_SPACE:
                    shouldFire = true;
                    timeSinceLastShot = -1;
                    break;
            }
        });
        kb.onKeyUp.addListener(code -> {
            switch (code) {
                case KeyEvent.VK_LEFT:
                case KeyEvent.VK_RIGHT:
                case KeyEvent.VK_UP:
                case KeyEvent.VK_DOWN:
                    movementKeyUp(code);
                    break;
                case KeyEvent.VK_SPACE:
                    shouldFire = false;
                    timeSinceLastShot = -1;
                    break;
                case KeyEvent.VK_ENTER:
                    currentSprite.setState(currentSprite.getState() + 1);
                    break;
            }
        });
    }

    private void movementKeyDown(int key) {
        keys.add(key);
    }

    private void movementKeyUp(int key) {
        int idx = keys.indexOf(key);
        if (idx >= 0) {
            keys.remove(idx);
        }
    }


    protected int maxRotation = 5;
    protected Vector hpBarPosition = new Vector(10, 700);
    @Override
    public void draw(Graphics2D graphics) {
        AffineTransform baseTransform = graphics.getTransform();

        double speedRation = speed.x / maxSpeed;

        currentSprite.rotationCenter = new Vector(32, 0);
        currentSprite.rotate(speedRation * maxRotation);
        super.draw(graphics);

        graphics.setTransform(baseTransform);
        hp.draw(graphics, hpBarPosition);
    }
}
