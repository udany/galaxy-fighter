package GalaxyFighter;
import GalaxyFighter.objects.background.GalaxyBackground;
import GalaxyFighter.objects.menu.TitleScreen;
import GalaxyFighter.objects.stage.SampleStage;
import engine.main.Stage;
import engine.window.Game;

public class GalaxyFighter extends Game {
    public GalaxyFighter() {
        this(null);
    }
    public GalaxyFighter(Stage startingStage) {
        super(1280, 720);
        setTitle("Galaxy Fighter");
        debug = true;
        frameRate = 60;

        addObject(new GalaxyBackground());

        if (startingStage == null) {
            startingStage = new TitleScreen();
        }

        addStage(startingStage);

        start();
    }
}
