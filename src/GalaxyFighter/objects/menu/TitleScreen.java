package GalaxyFighter.objects.menu;

import engine.base.Vector;
import engine.graphics.Sprite;
import engine.main.Stage;
import engine.window.Game;

import java.awt.*;

public class TitleScreen extends Stage {

    public TitleScreen(Game game) {
        super(game);

        start();
    }

    Sprite titleSprite = new Sprite(573, 189, "/images/Title.png");
    Vector titlePosition = new Vector(340, 100);

    public void start() {
        super.start();

        //Music bgm = new Music("/sound/music/01_Interstellar.mp3");
        //bgm.setVolume(.3);
        //bgm.start();
    }

    @Override
    public void draw(Graphics2D graphics) {
        super.draw(graphics);

        titleSprite.draw(graphics, titlePosition);
    }
}
