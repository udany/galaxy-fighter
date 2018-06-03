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

    protected double baseAcceleration = 1;
    protected double maxSpeed = 3;
    protected double brakeRatio = 1;

    protected Game game;
    protected BaseWeapon weapon;
    protected boolean shouldFire;
    protected double timeSinceLastShot = -1;

    public BaseShip(Game game) {
        this.game = game;
    }

    protected List<Integer> keys = new ArrayList<>();

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

        // keep within bounds
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
