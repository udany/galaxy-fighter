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
        ship.setPosition(580, 480-48);
        objectList.add(ship);

        ship = new EdgeLiner(this);
        ship.setPosition(680, 480-48);
        objectList.add(ship);

        ship = new BaseEnemyShip(this);
        ship.setPosition(320, 15);
        objectList.add(ship);

        Music bgm = new Music("/sound/music/03_Field_force.mp3");
        bgm.start();

        start();
    }
}
