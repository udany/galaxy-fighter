package GalaxyFighter.objects.ship.Enemy;

import GalaxyFighter.objects.bullets.BaseBullet;
import GalaxyFighter.objects.bullets.PlayerBullet;
import GalaxyFighter.objects.util.HP;
import GalaxyFighter.objects.ship.BaseShip;
import GalaxyFighter.objects.weapons.SingleWeapon;
import engine.graphics.Sprite;
import engine.input.Keyboard;
import engine.sound.SoundEffect;
import engine.sound.SoundEffectPool;

import java.awt.*;
import java.awt.event.KeyEvent;

public class BaseEnemyShip extends BaseShip {

    protected boolean immune = false;

    static SoundEffectPool damageSound = new SoundEffectPool("/sound/sfx/Damage_01.wav").setVolume(.1);
    static SoundEffectPool explosionSound = new SoundEffectPool("/sound/sfx/Explosion_01.wav").setVolume(.5);

    Sprite explosion = new Sprite(32,32, "/images/Explosion.png").setFramesPerFrame(5).setOrigin(-4,0);

    public BaseEnemyShip(){
        hp = new HP(10);

        weapon = new SingleWeapon();

        baseAcceleration = 2000;
        maxSpeed = 200;
        brakeRatio = 1.6;

        size.set(38, 28);

        checkForCollisions = true;

        currentSprite = new Sprite(64, 48, "/images/Ship_Bombardier.png");
        currentSprite.origin.set(13,8);
        currentSprite.rotate(180);

        onCollision.addListener(gameObject -> {
            if (gameObject instanceof PlayerBullet) {
                hit((PlayerBullet) gameObject);
            }
        });

        weapon = new EnemySingleWeapon();

        Keyboard.getInstance().onKeyDown.addListener(key -> {
            if (key == KeyEvent.VK_B) {
                shoot();
            }
        });
    }

    protected long timeLastHit = 0;
    protected int drawHpInterval = 500;
    public void hit(PlayerBullet bullet) {
        if (bullet.isExploding()) return;
        if (immune) return;

        bullet.explode();

        hp.add(-bullet.damage);
        timeLastHit = currentStage.getGameTime();

        if (hp.current > 0) {
            damageSound.get().start();
            return;
        }

        explosionSound.get().start();
        currentSprite = explosion;
        explosion.onAnimationEnd.addListener(o -> this.destroy());

        immune = true;
    }

    @Override
    public void draw(Graphics2D graphics) {
        long elapsedSinceLastHit = currentStage.getGameTime()  - timeLastHit;
        if  (elapsedSinceLastHit <= drawHpInterval && hp.current > 0) {
            hp.draw(graphics, position.clone().add(5, -10), 1 - ((double)elapsedSinceLastHit/drawHpInterval));
        }

        super.draw(graphics);
    }
}
