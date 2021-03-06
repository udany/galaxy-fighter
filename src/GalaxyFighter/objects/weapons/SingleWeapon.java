package GalaxyFighter.objects.weapons;

import GalaxyFighter.objects.bullets.PlayerBullet;
import GalaxyFighter.objects.ship.BaseShip;
import engine.main.Stage;
import engine.sound.SoundEffectPool;

public class SingleWeapon extends BaseWeapon {

    SoundEffectPool shootSound;
    public SingleWeapon() {
        cadence = .1;
        shootSound = new SoundEffectPool("/sound/sfx/Shoot_01.wav");
        shootSound.setVolume(.15);
    }

    public void fire(Stage game, BaseShip ship) {
        PlayerBullet b = new PlayerBullet();
        b.setPosition(ship.getCenter().add(-3, -13));
        b.fire(-1);
        b.setType(5);

        game.addObject(b);

        shootSound.get().start();
    }
}