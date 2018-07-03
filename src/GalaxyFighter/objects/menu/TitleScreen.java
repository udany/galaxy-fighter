package GalaxyFighter.objects.menu;

import GalaxyFighter.objects.score.Score;
import GalaxyFighter.objects.ship.Bombardier;
import GalaxyFighter.objects.ship.EdgeLiner;
import GalaxyFighter.objects.ship.PlayerShip;
import GalaxyFighter.objects.stage.Stage1;
import engine.base.Vector;
import engine.graphics.Color;
import engine.graphics.Sprite;
import engine.main.Stage;
import engine.sound.Music;
import engine.window.Transition.FadeIn;

import java.awt.*;
import java.util.Arrays;

public class TitleScreen extends Stage {
    static Music bgm = new Music("/sound/music/01_Interstellar.mp3");

    public TitleScreen() {
        Menu menu = new Menu(Arrays.asList(
                new MenuOption("Choose Your Ship:"),
                new MenuOption("Edgeliner", ()->{
                    Score.getInstance().reset();
                    gameStart(new EdgeLiner());
                }),
                new MenuOption("Bombardier", ()->{
                    Score.getInstance().reset();
                    gameStart(new Bombardier());
                })
        )).setFont("/fonts/pixelmix.ttf", 35);

        addObject(menu);

        bgm.setVolume(.05);

        onAdd.addListener(x -> {
            bgm.start();
        });

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
        transitionTo(new Stage1(player));
    }

    @Override
    public void draw(Graphics2D graphics) {
        super.draw(graphics);

        titleSprite.draw(graphics, titlePosition);
    }
}
