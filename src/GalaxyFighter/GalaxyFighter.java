package GalaxyFighter;
import GalaxyFighter.objects.ship.BaseShip;
import GalaxyFighter.objects.ship.Bombardier;
import GalaxyFighter.objects.ship.EdgeLiner;
import GalaxyFighter.objects.ship.Enemy.BaseEnemyShip;
import engine.sound.Music;
import engine.window.Game;

public class GalaxyFighter extends Game {
    public GalaxyFighter() {
        super(1280, 720);
        setTitle("Galaxy Fighter");

        BaseShip ship;

        ship = new Bombardier(this);
        ship.setPosition(580, 720-48);
        addObject(ship);

        ship = new EdgeLiner(this);
        ship.setPosition(680, 720-48);
        addObject(ship);

        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 5; j++) {
                ship = new BaseEnemyShip(this);
                ship.setPosition((128 * i) +40, (j * 72) + 15);
                addObject(ship);
            }
        }

        Music bgm = new Music("/sound/music/01_Interstellar.mp3");
        bgm.setVolume(.3);
        bgm.start();

        start();
    }
}
