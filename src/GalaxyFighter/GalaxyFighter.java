package GalaxyFighter;
import GalaxyFighter.objects.background.GalaxyBackground;
import GalaxyFighter.objects.input.GalaxyInput;
import GalaxyFighter.objects.menu.TitleScreen;
import engine.input.Controller;
import engine.input.GenericInput;
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

    static GalaxyInput controller;

    public GenericInput getInput() {
        if (controller == null) controller = new GalaxyInput();

        if (Controller.getCount() > 0) {
            controller.setController(Controller.getController(0));
        }

        return controller;
    }
}
