package GalaxyFighter.objects.ship.Enemy;

import GalaxyFighter.objects.bullets.BaseBullet;
import engine.graphics.Sprite;

public class EnemyBullet extends BaseBullet {
    public EnemyBullet() {
        super();

        baseSpeed = -500;

        size.set(6, 6);


        currentSprite = new Sprite(16, 16, "/images/Bullets.png");
        currentSprite.origin.set(5, 5);

        explodeSprite = new Sprite(16, 16, "/images/Explosion_small.png");
        explodeSprite.origin.set(5, 5);
        explodeSprite.setFramesPerFrame(6);
    }

    public void setType(int type) {
        currentSprite.setState(type);
    }
}
