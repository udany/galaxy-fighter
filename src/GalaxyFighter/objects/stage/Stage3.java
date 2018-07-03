package GalaxyFighter.objects.stage;

import GalaxyFighter.objects.ship.BaseShip;
import GalaxyFighter.objects.ship.Enemy.Enemy02;
import GalaxyFighter.objects.ship.Enemy.Enemy03;
import GalaxyFighter.objects.ship.PlayerShip;
import engine.sound.Music;

public class Stage3 extends GalaxyStage {
    public Stage3(PlayerShip p) {
        super(p);

        bgm = new Music("/sound/music/02_Fire_darer.mp3");
        bgm.setVolume(.15);

        onAdd.addListener(x -> {
            BaseShip ship;

            for (int j = 0; j < 2; j++) {
                for (int i = 0; i < (10 - j); i++) {
                    ship = new Enemy03();
                    ship.setPosition((120 * i) + (70 * j) + 45, 100 + (j * 100));
                    addObject(ship);
                }
            }
        });

        onRemove.addListener(x -> {
        });
    }

    public void nextStage() {
        returnToTitle();
    }
}
