package GalaxyFighter.objects.ship.Enemy;

import GalaxyFighter.objects.ship.BaseShip;
import GalaxyFighter.objects.weapons.SingleWeapon;
import engine.graphics.Sprite;
import engine.window.Game;

public class BaseEnemyShip extends BaseShip {

    public BaseEnemyShip(Game game){
        super(game);

        weapon = new SingleWeapon();

        baseAcceleration = 2000;
        maxSpeed = 200;
        brakeRatio = 1.6;

        size.set(10 , 28);

        currentSprite = new Sprite(64, 48, "/images/Ship_Bombardier.png");
        currentSprite.origin.set(23,8);
        currentSprite.rotate(180);
    }
}
