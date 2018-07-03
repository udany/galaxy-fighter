package GalaxyFighter.objects.ship.Enemy;

import GalaxyFighter.objects.score.Score;
import GalaxyFighter.objects.bullets.PlayerBullet;
import GalaxyFighter.objects.util.HP;
import GalaxyFighter.objects.ship.BaseShip;
import engine.base.Vector;
import engine.graphics.Sprite;
import engine.sound.SoundEffectPool;

import java.awt.*;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class BaseEnemyShip extends BaseShip {
    protected boolean immune = false;

    static SoundEffectPool damageSound = new SoundEffectPool("/sound/sfx/Damage_01.wav").setVolume(.1);
    static SoundEffectPool explosionSound = new SoundEffectPool("/sound/sfx/Explosion_01.wav").setVolume(.5);

    Sprite explosion = new Sprite(32, 32, "/images/Explosion.png").setFramesPerFrame(5).setOrigin(-4, 0);

    public BaseEnemyShip() {
        weapon = new EnemySingleWeapon();

        size.set(38, 28);

        checkForCollisions = true;

        currentSprite = new Sprite(64, 48, "/images/Ship_Bombardier.png");
        currentSprite.origin.set(13, 8);
        currentSprite.rotate(180);

        onCollision.addListener(gameObject -> {
            if (gameObject instanceof PlayerBullet) {
                hit((PlayerBullet) gameObject);
            }
        });


        onAdd.addListener(stage -> {
           setHp();
        });

        motions = Arrays.asList(
                new EnemyMotion(new Vector(0, 0), 1)
        );
    }

    protected long timeLastHit = 0;
    protected int drawHpInterval = 1000;

    protected void setHp(){
        hp = new HP(5, currentStage.getGame());
    }

    public void hit(PlayerBullet bullet) {
        if (bullet.isExploding()) return;
        if (immune) return;

        bullet.explode();

        hp.add(-bullet.damage);
        timeLastHit = currentStage.getGameTime();

        if (hp.getCurrent() > 0) {

            damageSound.get().start();

        } else {

            Score.getInstance().modify(getScore());

            explosionSound.get().start();
            currentSprite = explosion;
            explosion.onAnimationEnd.addListener(o -> this.destroy());

            immune = true;
        }
    }

    protected double timetoSinceLastShoot = 0;
    protected double timeMinShoot = 2;
    protected double timeMaxShoot = 5;
    protected double timetoNextShoot = 0;
    Random random = new Random();

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

    protected double timeAlive = 0;
    protected double initialScore = 100;
    protected double decayAmount = 50;
    protected double decayTime = 15;

    public int getScore() {
        double ratio = timeAlive / decayTime;

        if (ratio > 1) {

            ratio = 1;
        }
        return (int)(initialScore - (ratio * decayAmount));
    }

    @Override
    public void draw(Graphics2D graphics) {
        long elapsedSinceLastHit = currentStage.getGameTime() - timeLastHit;
        if (elapsedSinceLastHit <= drawHpInterval) {
            hp.draw(graphics, position.clone().add(5, -10), 1 - ((double) elapsedSinceLastHit / drawHpInterval));
        }

        super.draw(graphics);
    }

    protected List<EnemyMotion> motions;
    private EnemyMotion currentMotion;
    private double timeSinceMotionChange = 0;

    protected void updateMotion(double secondsElapsed){

        if(currentMotion == null) {

            currentMotion = motions.get(0);

        } else {

            timeSinceMotionChange += secondsElapsed;

            if( timeSinceMotionChange >= currentMotion.duration){

                int index = motions.indexOf(currentMotion);

                if(index < motions.size() - 1){

                    currentMotion = motions.get(index + 1);

                } else {

                    currentMotion = motions.get(0);
                }

                timeSinceMotionChange = 0;

            }
        }

        speed.set(currentMotion.speed.x, currentMotion.speed.y);

    }
}
