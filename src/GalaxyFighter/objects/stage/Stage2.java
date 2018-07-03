package GalaxyFighter.objects.stage;

import GalaxyFighter.objects.menu.TitleScreen;
import GalaxyFighter.objects.score.Score;
import GalaxyFighter.objects.ship.BaseShip;
import GalaxyFighter.objects.ship.Enemy.BaseEnemyShip;
import GalaxyFighter.objects.ship.PlayerShip;
import engine.main.Stage;
import engine.sound.Music;

import java.awt.*;

public class Stage2 extends Stage {
    static Music bgm = new Music("/sound/music/02_Fire_darer.mp3");

    PlayerShip player;

    public Stage2(PlayerShip p) {
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

        for (int j = 0; j < 4; j++) {
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

    @Override
    public void update(long elapsedMs) {
        super.update(elapsedMs);

        if (!transitioning && getObjectList(x -> x instanceof BaseEnemyShip).size() == 0) {
            returnToTitle();
        }
    }

    @Override
    public void draw(Graphics2D graphics) {

        super.draw(graphics);
        Score.getInstance().draw(graphics);
    }

}
