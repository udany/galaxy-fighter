package GalaxyFighter.objects.menu;

import engine.base.Vector;
import engine.graphics.Color;
import engine.graphics.Sprite;
import engine.main.Stage;
import engine.sound.Music;
import engine.window.Transition.FadeIn;

import java.awt.*;
import java.util.Arrays;

public class TitleScreen extends Stage {

    public TitleScreen() {
        Menu menu = new Menu(Arrays.asList(
                new MenuOption("Choose Your Ship:"),
                new MenuOption("Bombardier", ()->{
                }),
                new MenuOption("Edgeliner", ()->{
                })
        )).setFont("/fonts/pixelmix.ttf", 35);

        addObject(menu);

        Music bgm = new Music("/sound/music/01_Interstellar.mp3");
        bgm.setVolume(.05);
        //bgm.start();

        onAdd.addListener(x -> {
            FadeIn fadeIn = new FadeIn(new Color(0,0,0), 2, game);
            game.transition(fadeIn);
        });

        onRemove.addListener(x -> {
            bgm.stop();
        });
    }

    Sprite titleSprite = new Sprite(573, 189, "/images/Title.png");
    Vector titlePosition = new Vector(360, 100);

    @Override
    public void draw(Graphics2D graphics) {
        super.draw(graphics);

        titleSprite.draw(graphics, titlePosition);
    }
}
