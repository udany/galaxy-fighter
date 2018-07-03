package GalaxyFighter.objects.ship.Enemy;

import GalaxyFighter.objects.util.HP;
import engine.base.Vector;

import java.util.Arrays;

public class Enemy01 extends BaseEnemyShip {

    public Enemy01(){
        super();

        motions = Arrays.asList(
                new EnemyMotion(new Vector(100, 0), 1),
                new EnemyMotion(new Vector(-100, 0), 2),
                new EnemyMotion(new Vector(100, 0), 1)
        );
    }

    @Override
    protected void setHp() {

        hp = new HP(10, currentStage.getGame());

    }
}
