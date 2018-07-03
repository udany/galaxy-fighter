package GalaxyFighter.objects.stage;

import GalaxyFighter.objects.menu.TitleScreen;
import GalaxyFighter.objects.score.Score;
import GalaxyFighter.objects.ship.BaseShip;
import GalaxyFighter.objects.ship.Enemy.BaseEnemyShip;
import GalaxyFighter.objects.ship.Enemy.Enemy02;
import GalaxyFighter.objects.ship.PlayerShip;
import engine.sound.Music;

import java.awt.*;

public class Stage2 extends GalaxyStage {
    public Stage2(PlayerShip p) {
        super(p);

        bgm = new Music("/sound/music/03_Field_force.mp3");
        bgm.setVolume(.15);

        onAdd.addListener(x -> {
            BaseShip ship;

            for (int j = 0; j < 4; j++) {
                for (int i = 0; i < (10 - j); i++) {
                    ship = new Enemy02();
                    ship.setPosition((128 * i) +45*(j+1), (j * 72) + 15);
                    addObject(ship);
                }
            }
        });

        onRemove.addListener(x -> {
        });
    }

    public void nextStage() {
        transitionTo(new Stage3(player));
    }
}
