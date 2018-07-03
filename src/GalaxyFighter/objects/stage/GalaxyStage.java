package GalaxyFighter.objects.stage;

import GalaxyFighter.objects.menu.TitleScreen;
import GalaxyFighter.objects.score.Score;
import GalaxyFighter.objects.ship.Enemy.BaseEnemyShip;
import GalaxyFighter.objects.ship.PlayerShip;
import engine.main.Stage;
import engine.sound.Music;

import java.awt.*;

public abstract class GalaxyStage extends Stage {
    protected PlayerShip player;
    protected Music bgm;

    public GalaxyStage(PlayerShip p) {
        player = p;

        onAdd.addListener(x -> {
            player.setPosition(680, 720-48);
            addObject(player);

            player.onDeath.addListener(z -> {
                returnToTitle();
            }, id.toString());

            bgm.start();
        });

        onRemove.addListener(x -> {
            bgm.stop();
            player.onDeath.removeListener(id.toString());
        });
    }

    public void returnToTitle() {
        transitionTo(new TitleScreen());
    }
    abstract public void nextStage();

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
