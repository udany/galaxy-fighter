package GalaxyFighter.objects.stage;

import GalaxyFighter.objects.ship.BaseShip;
import GalaxyFighter.objects.ship.Enemy.Enemy01;
import GalaxyFighter.objects.ship.Enemy.Enemy03;
import GalaxyFighter.objects.ship.Enemy.EnemyBoss;
import GalaxyFighter.objects.ship.PlayerShip;

import engine.sound.Music;

public class Stage1 extends GalaxyStage {
    public Stage1(PlayerShip p) {
        super(p);

        bgm = new Music("/sound/music/01_Interstellar.mp3");
        bgm.setVolume(.15);

        onAdd.addListener(x -> {
            BaseShip ship;

            for (int j = 0; j < 3; j++) {
                for (int i = 0; i < (10 - j); i++) {
                    ship = new Enemy01();
                    ship.setPosition((128 * i) + 45 * (j + 1), (j * 72) + 15);
                    addObject(ship);
                }
            }

        });

        onRemove.addListener(x -> {
        });
    }

    public void nextStage() {
        transitionTo(new Stage2(player));
    }
}
