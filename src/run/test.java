package run;

import GalaxyFighter.GalaxyFighter;
import GalaxyFighter.objects.ship.EdgeLiner;
import GalaxyFighter.objects.stage.SampleStage;
import engine.window.Game;

public class test {
    public static void main( String[] args ) {
        Game g = new GalaxyFighter(
                new SampleStage(
                        new EdgeLiner()
                )
        );
        g.open();
    }
}
