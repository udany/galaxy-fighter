package GalaxyFighter.objects.bullets;

import engine.graphics.Sprite;

public class SimpleBullet extends BaseBullet {
    public SimpleBullet() {
        super();

        baseAcceleration = .05;
        baseSpeed = 1;
        maxSpeed = 2;

        size.set(6, 6);

        currentSprite = new Sprite(16, 16, "/images/Bullets.png");
        currentSprite.origin.set(5, 5);
    }
}
