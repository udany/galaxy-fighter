package GalaxyFighter.objects.ship.Enemy;

import GalaxyFighter.objects.util.HP;
import engine.base.Vector;

import java.util.Arrays;

public class Enemy02 extends BaseEnemyShip {

    public Enemy02(){
        super();
        currentSprite.setState(1);

        motions = Arrays.asList(
                new EnemyMotion(new Vector(100, 100), 0.5),
                new EnemyMotion(new Vector(-100, 0), 1),
                new EnemyMotion(new Vector(100, 100), 0.5),
                new EnemyMotion(new Vector(-100, -100), 0.5),
                new EnemyMotion(new Vector(100, 0), 1),
                new EnemyMotion(new Vector(-100, -100), 0.5)
        );
    }

    @Override
    protected void setHp() {

        hp = new HP(10, currentStage.getGame());

    }
}
