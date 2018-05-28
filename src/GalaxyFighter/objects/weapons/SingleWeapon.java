package GalaxyFighter.objects.weapons;

import GalaxyFighter.objects.bullets.SimpleBullet;
import GalaxyFighter.objects.ship.BaseShip;
import engine.base.Vector;
import engine.window.Game;

public class SingleWeapon extends BaseWeapon {
    public void fire(Game game, BaseShip ship) {
        SimpleBullet b = new SimpleBullet();

        b.setPosition(ship.getCenter().add(-3, -13));

        game.addObject(b);

        b.fire(-1);
    }
}