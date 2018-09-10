package GalaxyFighter.objects.bullets;

import engine.graphics.Sprite;
import engine.main.GameObject;
import engine.main.MotionObject;
import engine.util.Event;

public class BaseBullet extends MotionObject {
    Event<GameObject> OnHitShip = new Event<>();

    public double baseSpeed = 500;
    public double maxSpeed = 5000;
    public double baseAcceleration = 0;
    public int damage = 5;

    public BaseBullet() {
        isSolid = true;
    }

    public void fire(int direction) {
        this.speed.y = baseSpeed * direction;
        this.acceleration.y = baseAcceleration * direction;
    }

    @Override
    public void update(double secondsElapsed) {
        super.update(secondsElapsed);
        if (speed.y > 0 && speed.y > maxSpeed) {
            speed.y = maxSpeed;
        }else if (speed.y < 0 && speed.y < -maxSpeed) {
            speed.y = -maxSpeed;
        }

        position.y = Math.round(position.y);

        if (this.position.y > this.currentStage.size.height) {
            this.destroy();
        }
    }



    public Sprite explodeSprite;
    private boolean exploding = false;
    public boolean isExploding() {
        return exploding;
    }

    public void explode() {
        speed.scale(0.15);
        acceleration.set(0,0);

        currentSprite = explodeSprite;
        explodeSprite.onAnimationEnd.addListener(o -> this.destroy());

        exploding = true;
    }
}
