package GalaxyFighter.objects.weapons;

import GalaxyFighter.objects.bullets.PlayerBullet;
import GalaxyFighter.objects.ship.BaseShip;
import engine.main.Stage;
import engine.sound.SoundEffectPool;

public class DoubleWeapon extends BaseWeapon {

    SoundEffectPool shootSound;
    public DoubleWeapon() {
        cadence = .2;
        shootSound = new SoundEffectPool("/sound/sfx/Shoot_01.wav");
        shootSound.setVolume(.15);
    }


    public void fire(Stage game, BaseShip ship) {
        PlayerBullet b;

        b = new PlayerBullet();
        b.setPosition(ship.getCenter().add(-13, -13));
        b.fire(-1);
        b.setType(6);

        game.addObject(b);


        b = new PlayerBullet();
        b.setPosition(ship.getCenter().add(7, -13));
        b.fire(-1);
        b.setType(6);

        game.addObject(b);

        shootSound.get().start();
    }
}