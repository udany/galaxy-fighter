package GalaxyFighter.objects.ship.Enemy;

import GalaxyFighter.objects.util.HP;
import engine.base.Vector;
import engine.graphics.Sprite;

import java.util.Arrays;

public class Enemy03 extends BaseEnemyShip {

    public Enemy03(){
        super();

        currentSprite = new Sprite(64, 48, "/images/Ship_Edgeliner.png");
        currentSprite.origin.set(13, 8);
        currentSprite.rotate(180);
        currentSprite.setState(1);

        timeMinShoot = 1;
        timeMaxShoot = 3;

        motions = Arrays.asList(
                new EnemyMotion(new Vector(200, 0), 0.25),
                new EnemyMotion(new Vector(-100, 200), 0.5),
                new EnemyMotion(new Vector(-100, -200), 0.5),
                new EnemyMotion(new Vector(200, 0), 0.25)
        );
    }

    @Override
    protected void setHp() {
        hp = new HP(15, currentStage.getGame());
    }
}
