package GalaxyFighter.objects.ship;

import GalaxyFighter.objects.weapons.BaseWeapon;
import engine.graphics.Sprite;
import engine.input.Keyboard;
import engine.main.MotionObject;
import engine.window.Game;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

abstract public class PlayerShip extends BaseShip {
    public PlayerShip(Game game) {
        super(game);

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
                    currentSprite.setState(currentSprite.state + 1);
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

    public void shoot() {
        if (weapon != null) {
            weapon.fire(game, this);
        }
    }
}
