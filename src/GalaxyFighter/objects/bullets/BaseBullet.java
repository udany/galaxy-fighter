package GalaxyFighter.objects.bullets;

import engine.graphics.Sprite;
import engine.main.GameObject;
import engine.main.MotionObject;
import util.Event;

public class BaseBullet extends MotionObject {
    Event<GameObject> OnHitShip = new Event<>();

    public double baseSpeed = 1;
    public double maxSpeed = 1;
    public double baseAcceleration = 0;

    public BaseBullet() {

    }

    public void fire(int direction) {
        this.speed.y = baseSpeed * direction;
        this.acceleration.y = baseAcceleration * direction;
    }

    @Override
    public void update() {
        super.update();
        if (speed.y > 0 && speed.y > maxSpeed) {
            speed.y = maxSpeed;
        }else if (speed.y < 0 && speed.y < -maxSpeed) {
            speed.y = -maxSpeed;
        }

        if (this.position.y < -this.size.height) {
            this.destroy();
        }
    }
}
