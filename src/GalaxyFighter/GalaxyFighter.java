package GalaxyFighter;
import GalaxyFighter.objects.background.GalaxyBackground;
import GalaxyFighter.objects.stage.SampleStage;
import engine.window.Game;

public class GalaxyFighter extends Game {
    public GalaxyFighter() {
        super(1280, 720);
        setTitle("Galaxy Fighter");
        debug = true;
        frameRate = 60;

        addObject(new GalaxyBackground());

        SampleStage t = new SampleStage(this);
        addStage(t);

        start();
    }
}
