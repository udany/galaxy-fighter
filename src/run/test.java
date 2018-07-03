package run;

import GalaxyFighter.GalaxyFighter;
import GalaxyFighter.objects.ship.EdgeLiner;
import GalaxyFighter.objects.stage.Stage1;
import engine.window.Game;

public class test {
    public static void main( String[] args ) {
        Game g = new GalaxyFighter(
                new Stage1(
                        new EdgeLiner()
                )
        );
        g.open();
    }
}
