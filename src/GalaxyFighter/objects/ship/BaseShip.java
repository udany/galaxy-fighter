package GalaxyFighter.objects.ship;

import GalaxyFighter.objects.weapons.BaseWeapon;
import engine.graphics.Palette;
import engine.graphics.Sprite;
import engine.input.Keyboard;
import engine.main.MotionObject;
import engine.window.Game;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

abstract public class BaseShip extends MotionObject {
    Sprite engineFireSprite;

    double baseAcceleration = 1;
    double maxSpeed = 3;
    double brakeRatio = 1;

    Game game;
    BaseWeapon weapon;
    boolean shouldFire;
    double timeSinceLastShot = -1;

    public BaseShip(Game game) {
        this.game = game;

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

    private List<Integer> keys = new ArrayList<>();

    private void movementKeyDown(int key) {
        keys.add(key);
    }

    private void movementKeyUp(int key) {
        int idx = keys.indexOf(key);
        if (idx >= 0) {
            keys.remove(idx);
        }
    }

    @Override
    public void update(double secondsElapsed) {
        int idxUp = keys.indexOf(KeyEvent.VK_UP);
        int idxDown = keys.indexOf(KeyEvent.VK_DOWN);
        int idxLeft = keys.indexOf(KeyEvent.VK_LEFT);
        int idxRight = keys.indexOf(KeyEvent.VK_RIGHT);

        if (idxUp >= 0 || idxDown >= 0) {
            acceleration.y = 0;

            if (Math.max(idxDown, idxUp) == idxUp) {
                if (speed.y < -maxSpeed) {
                    speed.y = -maxSpeed;
                } else {
                    speed.y -= baseAcceleration * secondsElapsed;
                }
            } else {
                if (speed.y > maxSpeed) {
                    speed.y = maxSpeed;
                } else {
                    speed.y += baseAcceleration * secondsElapsed;
                }
            }
        } else {
            if (Math.round(speed.y) == 0) {
                speed.y = 0;
            }else {
                speed.y -= speed.y * (brakeRatio * secondsElapsed);
            }
        }

        if (idxLeft >= 0 || idxRight >= 0) {
            acceleration.x = 0;

            if (Math.max(idxLeft, idxRight) == idxLeft) {
                if (speed.x < -maxSpeed) {
                    speed.x = -maxSpeed;
                } else {
                    speed.x -= baseAcceleration * secondsElapsed;
                }
            } else {
                if (speed.x > maxSpeed) {
                    speed.x = maxSpeed;
                } else {
                    speed.x += baseAcceleration * secondsElapsed;
                }
            }
        } else {
            if (Math.round(speed.x) == 0) {
                speed.x = 0;
            }else {
                speed.x -= speed.x * brakeRatio * secondsElapsed;
            }
        }

        super.update(secondsElapsed);

        if (position.x <= 0) {
            position.x = 0;
        }
        if (position.x + size.width >= game.size.width) {
            position.x = game.size.width - size.width;
        }

        if (position.y <= 0) {
            position.y = 0;
        }
        if (position.y + size.height >= game.size.height) {
            position.y = game.size.height - size.height;
        }


        // SHOOT
        if (shouldFire) {
            if (timeSinceLastShot < 0 || timeSinceLastShot >= weapon.cadence) {
                shoot();
                timeSinceLastShot = 0;
            } else {
                timeSinceLastShot += secondsElapsed;
            }
        }
    }

    public void shoot() {
        if (weapon != null) {
            weapon.fire(game, this);
        }
    }
}
