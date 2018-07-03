package GalaxyFighter.objects.ship.Enemy;

import engine.base.Vector;

public class EnemyMotion {

    protected Vector speed;
    protected double duration;

    EnemyMotion(Vector speed, double duration){

        this.speed = speed;
        this.duration = duration;
    }

}
