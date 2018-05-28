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

        panel.setBackground(Color.BLACK);

        BaseShip ship = new Bombardier(this);
        ship.setPosition(240, 480-48);
        objectList.add(ship);


        ship = new EdgeLiner(this);
        ship.setPosition(380, 480-48);
        objectList.add(ship);
    }

    @Override
    protected void update() {
        super.update();
    }
}
