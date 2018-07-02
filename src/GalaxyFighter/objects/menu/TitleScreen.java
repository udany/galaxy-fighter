package GalaxyFighter.objects.menu;

import GalaxyFighter.objects.ship.Bombardier;
import GalaxyFighter.objects.ship.EdgeLiner;
import GalaxyFighter.objects.ship.PlayerShip;
import GalaxyFighter.objects.stage.SampleStage;
import engine.base.Vector;
import engine.graphics.Color;
import engine.graphics.Sprite;
import engine.main.Stage;
import engine.sound.Music;
import engine.window.Transition.FadeIn;
import engine.window.Transition.FadeOut;

import java.awt.*;
import java.util.Arrays;

public class TitleScreen extends Stage {

    public TitleScreen() {
        Menu menu = new Menu(Arrays.asList(
                new MenuOption("Choose Your Ship:"),
                new MenuOption("Edgeliner", ()->{
                    gameStart(new EdgeLiner());
                }),
                new MenuOption("Bombardier", ()->{
                    gameStart(new Bombardier());
                })
        )).setFont("/fonts/pixelmix.ttf", 35);

        addObject(menu);

        Music bgm = new Music("/sound/music/01_Interstellar.mp3");
        bgm.setVolume(.05);
        bgm.start();

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

    protected void gameStart(PlayerShip player) {
        FadeOut fadeOut = new FadeOut(new Color(0,0,0), .5, game);
        FadeIn fadeIn = new FadeIn(new Color(0,0,0), .5, game);
        fadeOut.chain(fadeIn);

        fadeOut.onEnd.addListener(x -> {
            game.removeStage(this);
            game.addStage(new SampleStage(player));
        });

        game.transition(fadeOut);
    }

    @Override
    public void draw(Graphics2D graphics) {
        super.draw(graphics);

        titleSprite.draw(graphics, titlePosition);
    }
}
