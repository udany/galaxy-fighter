package GalaxyFighter;
import GalaxyFighter.objects.ship.BaseShip;
import engine.window.Game;

import java.awt.*;

public class GalaxyFighter extends Game {
    public GalaxyFighter() {
        super(640, 480);
        setTitle("Galaxy Fighter");

        panel.setBackground(Color.BLACK);

        BaseShip ship = new BaseShip(this);
        ship.setPosition(320, 480-48);
        objectList.add(ship);
    }

    @Override
    protected void update() {
        super.update();
    }
}
