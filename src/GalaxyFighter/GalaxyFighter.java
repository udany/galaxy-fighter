package GalaxyFighter;
import GalaxyFighter.objects.background.GalaxyBackground;
import GalaxyFighter.objects.ship.BaseShip;
import GalaxyFighter.objects.ship.Bombardier;
import GalaxyFighter.objects.ship.EdgeLiner;
import GalaxyFighter.objects.ship.Enemy.BaseEnemyShip;
import GalaxyFighter.objects.stage.Test;
import engine.sound.Music;
import engine.window.Game;

public class GalaxyFighter extends Game {
    public GalaxyFighter() {
        super(1280, 720);
        setTitle("Galaxy Fighter");
        debug = true;
        frameRate = 60;

        addObject(new GalaxyBackground());

        Test t = new Test(this);
        addStage(t);

        start();
    }
}
