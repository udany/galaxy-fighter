package GalaxyFighter.objects.ship.Enemy;

import GalaxyFighter.objects.bullets.BaseBullet;
import GalaxyFighter.objects.ship.BaseShip;
import GalaxyFighter.objects.weapons.SingleWeapon;
import engine.graphics.Sprite;
import engine.sound.SoundEffect;
import engine.window.Game;

public class BaseEnemyShip extends BaseShip {

    protected boolean immune = false;

    public BaseEnemyShip(Game game){
        super(game);

        weapon = new SingleWeapon();

        baseAcceleration = 2000;
        maxSpeed = 200;
        brakeRatio = 1.6;

        size.set(38, 28);

        checkForCollisions = true;

        currentSprite = new Sprite(64, 48, "/images/Ship_Bombardier.png");
        currentSprite.origin.set(13,8);
        currentSprite.rotate(180);

        SoundEffect shootSound = new SoundEffect("/sound/sfx/Explosion_01.wav");
        shootSound.setVolume(.5);

        Sprite explosion = new Sprite(32,32, "/images/Explosion.png");
        explosion.setFramesPerFrame(5);
        explosion.origin.set(-4,0);

        onCollision.addListener(gameObject -> {

            if (gameObject instanceof BaseBullet) {
                BaseBullet bullet = ((BaseBullet) gameObject);

                if (bullet.isExploding()) return;

                bullet.explode();

                if (immune) return;

                shootSound.start();
                currentSprite = explosion;
                explosion.onAnimationEnd.addListener(o -> this.destroy());

                immune = true;
            }
        });
    }
}
