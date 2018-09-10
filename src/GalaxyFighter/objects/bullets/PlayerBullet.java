package GalaxyFighter.objects.bullets;

import engine.graphics.Sprite;

public class PlayerBullet extends BaseBullet {
    public PlayerBullet() {
        super();

        baseAcceleration = 0;
        baseSpeed = 500;

        size.set(6, 6);

        currentSprite = new Sprite(16, 16, "/images/Bullets.png");
        currentSprite.origin.set(5, 5);

        explodeSprite = new Sprite(16, 16, "/images/Explosion_small.png");
        explodeSprite.origin.set(5, 5);
        explodeSprite.setFramesPerFrame(6);
    }

    @Override
    public void update(double secondsElapsed) {
        super.update(secondsElapsed);

        if (this.position.y < -this.size.height) {
            this.destroy();
        }
    }

    public void setType(int type) {
        currentSprite.setState(type);
    }
}
