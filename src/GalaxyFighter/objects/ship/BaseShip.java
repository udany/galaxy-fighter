package GalaxyFighter.objects.ship;

import GalaxyFighter.objects.util.HP;
import GalaxyFighter.objects.weapons.BaseWeapon;
import engine.graphics.Sprite;
import engine.main.MotionObject;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

abstract public class BaseShip extends MotionObject {
    Sprite engineFireSprite;

    protected BaseWeapon weapon;
    protected boolean shouldFire;
    protected double timeSinceLastShot = -1;

    public BaseShip() {
    }

    protected HP hp;

    public void shoot() {
        if (weapon != null) {
            weapon.fire(currentStage, this);
        }
    }
}
