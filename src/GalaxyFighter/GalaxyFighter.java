package GalaxyFighter;
import GalaxyFighter.objects.background.GalaxyBackground;
import GalaxyFighter.objects.menu.TitleScreen;
import GalaxyFighter.objects.stage.SampleStage;
import engine.main.Stage;
import engine.window.Game;

public class GalaxyFighter extends Game {
    public GalaxyFighter() {
        super(1280, 720);
        setTitle("Galaxy Fighter");
        debug = true;
        frameRate = 60;

        addObject(new GalaxyBackground());

        Stage stage = new TitleScreen();
        addStage(stage);

        start();
    }
}
