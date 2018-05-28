package GalaxyFighter.objects.weapons;

import GalaxyFighter.objects.bullets.SimpleBullet;
import GalaxyFighter.objects.ship.BaseShip;
import engine.window.Game;

public class DoubleWeapon extends BaseWeapon {
    public void fire(Game game, BaseShip ship) {
        SimpleBullet b;

        b = new SimpleBullet();
        b.setPosition(ship.getCenter().add(-13, -13));
        b.fire(-1);

        game.addObject(b);


        b = new SimpleBullet();
        b.setPosition(ship.getCenter().add(7, -13));
        b.fire(-1);

        game.addObject(b);
    }
}