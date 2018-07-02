package GalaxyFighter.objects.ship.Enemy;

import GalaxyFighter.objects.ship.BaseShip;
import GalaxyFighter.objects.weapons.BaseWeapon;
import engine.main.Stage;
import engine.sound.SoundEffectPool;

public class EnemySingleWeapon extends BaseWeapon {

    SoundEffectPool shootSound;
    public EnemySingleWeapon() {
        cadence = .1;
        shootSound = new SoundEffectPool("/sound/sfx/Shoot_01.wav");
        shootSound.setVolume(.05);
    }

    public void fire(Stage game, BaseShip ship) {
        EnemyBullet b = new EnemyBullet();
        b.setPosition(ship.getCenter().add(-3, -13));
        b.fire(-1);
        b.setType(7);

        game.addObject(b);

        shootSound.get().start();
    }
}