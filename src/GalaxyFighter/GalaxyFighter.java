package GalaxyFighter;
import GalaxyFighter.objects.ship.BaseShip;
import GalaxyFighter.objects.ship.Bombardier;
import GalaxyFighter.objects.ship.EdgeLiner;
import GalaxyFighter.objects.ship.Enemy.BaseEnemyShip;
import engine.window.Game;

import java.awt.*;

public class GalaxyFighter extends Game {
    public GalaxyFighter() {
        super(640, 480);
        setTitle("Galaxy Fighter");

        BaseShip ship;

        ship = new Bombardier(this);
        ship.setPosition(240, 480-48);
        objectList.add(ship);

        ship = new EdgeLiner(this);
        ship.setPosition(380, 480-48);
        objectList.add(ship);

        ship = new BaseEnemyShip(this);
        ship.setPosition(320, 15);
        objectList.add(ship);

        start();
    }
}
