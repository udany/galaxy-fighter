package GalaxyFighter.objects.bullets;

import engine.main.GameObject;
import engine.main.MotionObject;
import engine.util.Event;

public class BaseBullet extends MotionObject {
    Event<GameObject> OnHitShip = new Event<>();

    public double baseSpeed = 500;
    public double maxSpeed = 5000;
    public double baseAcceleration = 0;

    public BaseBullet() {

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

        if (this.position.y < -this.size.height) {
            this.destroy();
        }
    }
}
