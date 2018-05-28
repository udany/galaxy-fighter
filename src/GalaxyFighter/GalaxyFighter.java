package GalaxyFighter;
import GalaxyFighter.objects.ship.BaseShip;
import GalaxyFighter.objects.ship.Bombardier;
import GalaxyFighter.objects.ship.EdgeLiner;
import engine.window.Game;

import java.awt.*;

public class GalaxyFighter extends Game {
    public GalaxyFighter() {
        super(640, 480);
        setTitle("Galaxy Fighter");

        BaseShip ship = new EdgeLiner(this);
        ship.setPosition(380, 480-48);
        objectList.add(ship);

        start();
    }

    @Override
    protected void update(long elapsedMs) {
        super.update(elapsedMs);
    }
}
