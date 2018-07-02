package GalaxyFighter.objects.ship;

import GalaxyFighter.objects.ship.Enemy.EnemyBullet;
import GalaxyFighter.objects.util.HP;
import engine.base.Vector;
import engine.graphics.Color;
import engine.graphics.Sprite;
import engine.input.Keyboard;
import engine.sound.SoundEffect;
import engine.util.Event;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.geom.AffineTransform;
import java.util.Arrays;

abstract public class PlayerShip extends BaseShip {

    static SoundEffect explosionSound = new SoundEffect("/sound/sfx/Explosion_01.wav").setVolume(.5);
    static SoundEffect damageSound = new SoundEffect("/sound/sfx/Damage_01.wav").setVolume(.1);
    protected Sprite explosion = new Sprite(32,32, "/images/Explosion.png").setFramesPerFrame(5).setState(1).setOrigin(-4,0);

    protected Sprite hpOverlay = new Sprite(40,150, "/images/left_hbar.png");

    public PlayerShip() {

        checkForCollisions = true;

        /// KeyEvents
        Keyboard kb = Keyboard.getInstance();
        kb.onKeyDown.addListener(code -> {
            switch (code) {
                case KeyEvent.VK_LEFT:
                case KeyEvent.VK_RIGHT:
                case KeyEvent.VK_UP:
                case KeyEvent.VK_DOWN:
                    movementKeyDown(code);
                    break;
                case KeyEvent.VK_SPACE:
                    shouldFire = true;
                    timeSinceLastShot = -1;
                    break;
            }
        });
        kb.onKeyUp.addListener(code -> {
            switch (code) {
                case KeyEvent.VK_LEFT:
                case KeyEvent.VK_RIGHT:
                case KeyEvent.VK_UP:
                case KeyEvent.VK_DOWN:
                    movementKeyUp(code);
                    break;
                case KeyEvent.VK_SPACE:
                    shouldFire = false;
                    timeSinceLastShot = -1;
                    break;
                case KeyEvent.VK_ENTER:
                    currentSprite.setState(currentSprite.getState() + 1);
                    break;
            }
        });


        onCollision.addListener(obj -> {
            if (obj instanceof EnemyBullet) {
                hit((EnemyBullet) obj);
            }
        });

        onAdd.addListener(stage -> {
            setupHp();
        });
    }

    private void setupHp() {
        hp = new HP(20, currentStage.getGame());
        hp.size.set(105, 20);
        hp.setBackground(new Color(0, 0, 0, 0));
        hp.setBorder(new Color(0, 0, 0, 0));
        hp.setColors(Arrays.asList(
                new Color(255, 0, 0, 100),
                new Color(0, 255, 0, 100)
        ));
    }

    private void movementKeyDown(int key) {
        keys.add(key);
    }

    private void movementKeyUp(int key) {
        int idx = keys.indexOf(key);
        if (idx >= 0) {
            keys.remove(idx);
        }
    }

    public Event onDeath = new Event();
    public boolean exploding = false;
    protected void hit(EnemyBullet bullet) {
        if (bullet.isExploding()) return;
        if (exploding) return;

        this.hp.add(-bullet.damage);
        bullet.explode();

        damageSound.start();

        if (hp.getCurrent() == 0) {
            explosionSound.start();
            currentSprite = explosion;
            explosion.onAnimationEnd.addListener(o -> {
                onDeath.emit();
                this.destroy();
            });

            exploding = true;
        }
    }


    protected int maxRotation = 5;
    protected Vector hpBarOverlayPosition = new Vector(0, 570);
    protected Vector hpBarPosition = hpBarOverlayPosition.clone().add(9, 128);

    @Override
    public void draw(Graphics2D graphics) {
        AffineTransform baseTransform = graphics.getTransform();

        double speedRation = speed.x / maxSpeed;

        currentSprite.rotationCenter = new Vector(32, 0);
        currentSprite.rotate(speedRation * maxRotation);
        super.draw(graphics);

        graphics.setTransform(baseTransform);



        AffineTransform at = AffineTransform.getRotateInstance(
                Math.PI * 3/2, hpBarPosition.x, hpBarPosition.y);

        graphics.transform(at);
        hp.draw(graphics, hpBarPosition);

        graphics.setTransform(baseTransform);

        hpOverlay.draw(graphics, hpBarOverlayPosition);
    }
}
