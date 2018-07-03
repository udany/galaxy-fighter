package GalaxyFighter.objects.ship.Enemy;

import GalaxyFighter.objects.util.HP;
import engine.base.Size;
import engine.base.Vector;
import engine.graphics.Sprite;

import java.util.Arrays;

public class EnemyBoss extends BaseEnemyShip {

    public EnemyBoss() {
        super();


        weapon = new BossWeapon();
        timeMinShoot = 0.5;
        timeMaxShoot = 1;

        size.set(264, 88);
        currentSprite = new Sprite(264, 88, "/images/Boss.png");
        currentSprite.setState(0);

        motions = Arrays.asList(
                new EnemyMotion(new Vector(-150, 0), 2),
                new EnemyMotion(new Vector(0, 150), 1.5),
                new EnemyMotion(new Vector(0, -150), 1.5),
                new EnemyMotion(new Vector(150, 0), 4),
                new EnemyMotion(new Vector(0, 150), 1.5),
                new EnemyMotion(new Vector(0, -150), 1.5),
                new EnemyMotion(new Vector(-150, 0), 2)

        );

    }

    @Override
    public void update(double secondsElapsed) {
        super.update(secondsElapsed);

        if (timetoNextShoot == 0) {
            timetoNextShoot = timeMinShoot + random.nextDouble() * (timeMaxShoot - timeMinShoot);
        }

        super.update(secondsElapsed);
        timetoSinceLastShoot += secondsElapsed;

        if (timetoSinceLastShoot >= timetoNextShoot) {
            shoot();
            timetoSinceLastShoot = 0;
            timetoNextShoot = 0;

        }

        timeAlive += secondsElapsed;

        updateMotion(secondsElapsed);
    }

    @Override
    protected void setHp() {
        hp = new HP(500, currentStage.getGame());
        hp.size = new Size(100,20);
    }
}
