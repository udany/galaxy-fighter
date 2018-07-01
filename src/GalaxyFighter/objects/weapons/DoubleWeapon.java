package GalaxyFighter.objects.weapons;

import GalaxyFighter.objects.bullets.SimpleBullet;
import GalaxyFighter.objects.ship.BaseShip;
import engine.sound.SoundEffectPool;
import engine.window.Game;

public class DoubleWeapon extends BaseWeapon {

    SoundEffectPool shootSound;
    public DoubleWeapon() {
        cadence = .1;
        shootSound = new SoundEffectPool("/sound/sfx/Shoot_02.wav");
        shootSound.setVolume(.25);
    }


    public void fire(Game game, BaseShip ship) {
        SimpleBullet b;

        b = new SimpleBullet();
        b.setPosition(ship.getCenter().add(-13, -13));
        b.fire(-1);
        b.setType(6);

        game.addObject(b);


        b = new SimpleBullet();
        b.setPosition(ship.getCenter().add(7, -13));
        b.fire(-1);
        b.setType(6);

        game.addObject(b);

        shootSound.get().start();
    }
}