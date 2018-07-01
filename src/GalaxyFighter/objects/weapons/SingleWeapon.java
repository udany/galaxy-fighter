package GalaxyFighter.objects.weapons;

import GalaxyFighter.objects.bullets.SimpleBullet;
import GalaxyFighter.objects.ship.BaseShip;
import engine.base.Vector;
import engine.sound.SoundEffect;
import engine.sound.SoundEffectPool;
import engine.window.Game;

public class SingleWeapon extends BaseWeapon {

    SoundEffectPool shootSound;
    public SingleWeapon() {
        cadence = .1;
        shootSound = new SoundEffectPool("/sound/sfx/Shoot_01.wav");
        shootSound.setVolume(.25);
    }

    public void fire(Game game, BaseShip ship) {
        SimpleBullet b = new SimpleBullet();
        b.setPosition(ship.getCenter().add(-3, -13));
        b.fire(-1);
        b.setType(5);

        game.addObject(b);

        shootSound.get().start();
    }
}