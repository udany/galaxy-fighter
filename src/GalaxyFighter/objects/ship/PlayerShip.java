package GalaxyFighter.objects.ship;

import engine.base.Vector;
import engine.input.Keyboard;

import java.awt.*;
import java.awt.event.KeyEvent;

abstract public class PlayerShip extends BaseShip {
    public PlayerShip() {
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
    @Override
    public void draw(Graphics2D graphics) {
        double speedRation = speed.x / maxSpeed;

        currentSprite.rotationCenter = new Vector(32, 0);
        currentSprite.rotate(speedRation * maxRotation);
        super.draw(graphics);
    }
}
