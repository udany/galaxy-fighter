package GalaxyFighter.objects.bullets;

import engine.graphics.Sprite;

public class SimpleBullet extends BaseBullet {
    public SimpleBullet() {
        super();

        baseAcceleration = 0;
        baseSpeed = 500;

        size.set(6, 6);

        currentSprite = new Sprite(16, 16, "/images/Bullets.png");
        currentSprite.origin.set(5, 5);
        currentSprite.setState(5);
    }
}
