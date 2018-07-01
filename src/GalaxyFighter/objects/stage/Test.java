package GalaxyFighter.objects.stage;

import GalaxyFighter.objects.ship.BaseShip;
import GalaxyFighter.objects.ship.EdgeLiner;
import GalaxyFighter.objects.ship.Enemy.BaseEnemyShip;
import engine.main.Stage;
import engine.window.Game;

public class Test extends Stage {
    public Test(Game game) {
        super(game);

        BaseShip ship;

        ship = new EdgeLiner(this);
        ship.setPosition(680, 720-48);
        addObject(ship);

        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 5; j++) {
                ship = new BaseEnemyShip(this);
                ship.setPosition((128 * i) +40, (j * 72) + 15);
                addObject(ship);
            }
        }

        //Music bgm = new Music("/sound/music/01_Interstellar.mp3");
        //bgm.setVolume(.3);
        //bgm.start();

        start();
    }
}
