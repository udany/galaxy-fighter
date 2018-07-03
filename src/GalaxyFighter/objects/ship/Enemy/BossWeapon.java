package GalaxyFighter.objects.ship.Enemy;

import GalaxyFighter.objects.ship.BaseShip;
import engine.main.Stage;

public class BossWeapon extends EnemySingleWeapon {

    BossWeapon(){
        super();

    }

    @Override
    public void fire(Stage game, BaseShip ship) {
        EnemyBullet b1 = new EnemyBullet();
        EnemyBullet b2 = new EnemyBullet();

        b1.setPosition(ship.getCenter().add(85, 20));
        b1.fire(-1);
        b1.setType(7);

        b2.setPosition(ship.getCenter().add(-90, 20));
        b2.fire(-1);
        b2.setType(7);

        game.addObject(b1);
        game.addObject(b2);

        shootSound.get().start();
    }
}
