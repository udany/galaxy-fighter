package GalaxyFighter.objects.background;

import engine.graphics.Sprite;
import engine.main.GameObject;

import java.awt.*;

public class GalaxyBackground extends GameObject {
    protected static Sprite bck = new Sprite(1280,1175, "/images/Background.png");

    double scrollSpeed = 50;

    @Override
    public void update(double secondsElapsed) {
        this.position.y += scrollSpeed * secondsElapsed;
        if (this.position.y >= bck.getHeight()) {
            this.position.y = 0;
        }
    }

    @Override
    public void draw(Graphics2D graphics) {
        bck.draw(graphics, this.position.clone().subtract(0, bck.getHeight()));
        bck.draw(graphics, this.position);
    }
}
