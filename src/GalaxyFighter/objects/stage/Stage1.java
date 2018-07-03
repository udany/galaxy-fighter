package GalaxyFighter.objects.stage;

import GalaxyFighter.objects.menu.TitleScreen;
import GalaxyFighter.objects.score.Score;
import GalaxyFighter.objects.ship.BaseShip;
import GalaxyFighter.objects.ship.Enemy.BaseEnemyShip;
import GalaxyFighter.objects.ship.Enemy.Boss01;
import GalaxyFighter.objects.ship.Enemy.Enemy02;
import GalaxyFighter.objects.ship.PlayerShip;

import engine.main.Stage;
import engine.sound.Music;

import java.awt.*;

public class Stage1 extends Stage {
    static Music bgm = new Music("/sound/music/03_Field_force.mp3");

    PlayerShip player;

    public Stage1(PlayerShip p) {
        player = p;
        player.setPosition(680, 720-48);
        addObject(player);

        bgm.setVolume(.15);

        onAdd.addListener(x -> {
            bgm.start();
        });

        onRemove.addListener(x -> {
            bgm.stop();
        });

        BaseShip ship;

        for (int j = 0; j < 3; j++) {
            for (int i = 0; i < (10 - j); i++) {
                ship = new BaseEnemyShip();
                ship.setPosition((128 * i) +45*(j+1), (j * 72) + 15);
                addObject(ship);
            }
        }

        player.onDeath.addListener(x -> {
            returnToTitle();
        });
    }

    public void returnToTitle() {
        transitionTo(new TitleScreen());
    }

    public void nextStage() {
        transitionTo(new Stage2(player));
    }

    @Override
    public void update(long elapsedMs) {
        super.update(elapsedMs);

        if (!transitioning && getObjectList(x -> x instanceof BaseEnemyShip).size() == 0) {
            nextStage();
        }
    }

    @Override
    public void draw(Graphics2D graphics) {

        super.draw(graphics);
        Score.getInstance().draw(graphics);
    }

}
