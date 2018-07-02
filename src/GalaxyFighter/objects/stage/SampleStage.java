package GalaxyFighter.objects.stage;

import GalaxyFighter.objects.ship.BaseShip;
import GalaxyFighter.objects.ship.EdgeLiner;
import GalaxyFighter.objects.ship.Enemy.BaseEnemyShip;
import GalaxyFighter.objects.ship.PlayerShip;
import engine.main.Stage;

public class SampleStage extends Stage {
    public SampleStage(PlayerShip player) {
        player.setPosition(680, 720-48);
        addObject(player);


        BaseShip ship;
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 5; j++) {
                ship = new BaseEnemyShip();
                ship.setPosition((128 * i) +40, (j * 72) + 15);
                addObject(ship);
            }
        }
    }
}
