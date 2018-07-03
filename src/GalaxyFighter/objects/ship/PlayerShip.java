package GalaxyFighter.objects.ship;

import GalaxyFighter.objects.ship.Enemy.EnemyBullet;
import GalaxyFighter.objects.util.HP;
import engine.base.Vector;
import engine.graphics.Color;
import engine.graphics.Sprite;
import engine.input.GenericInput;
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

        onCollision.addListener(obj -> {
            if (obj instanceof EnemyBullet) {
                hit((EnemyBullet) obj);
            }
        });

        onAdd.addListener(stage -> {
            setupHp();
            bindKeys();
        });
        onRemove.addListener(stage -> {
            unbindKeys();
        });
    }

    private void bindKeys() {
        currentStage.onButtonDown.addListener(code -> {
            switch (code) {
                case GenericInput.Button.Up:
                    movementKeyDown(KeyEvent.VK_UP);
                    break;
                case GenericInput.Button.Down:
                    movementKeyDown(KeyEvent.VK_DOWN);
                    break;
                case GenericInput.Button.Left:
                    movementKeyDown(KeyEvent.VK_LEFT);
                    break;
                case GenericInput.Button.Right:
                    movementKeyDown(KeyEvent.VK_RIGHT);
                    break;
                case GenericInput.Button.A:
                    shootStart();
                    break;
            }
        }, id.toString());

        currentStage.onButtonUp.addListener(code -> {
            switch (code) {
                case GenericInput.Button.Up:
                    movementKeyUp(KeyEvent.VK_UP);
                    break;
                case GenericInput.Button.Down:
                    movementKeyUp(KeyEvent.VK_DOWN);
                    break;
                case GenericInput.Button.Left:
                    movementKeyUp(KeyEvent.VK_LEFT);
                    break;
                case GenericInput.Button.Right:
                    movementKeyUp(KeyEvent.VK_RIGHT);
                    break;
                case GenericInput.Button.A:
                    shootStop();
                    break;
            }
        }, id.toString());
    }
    private void unbindKeys() {
        currentStage.onButtonDown.removeListener(id.toString());
        currentStage.onButtonUp.removeListener(id.toString());
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

    protected void shootStart() {
        shouldFire = true;
        timeSinceLastShot = -1;
    }
    protected void shootStop() {
        shouldFire = false;
        timeSinceLastShot = -1;
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
