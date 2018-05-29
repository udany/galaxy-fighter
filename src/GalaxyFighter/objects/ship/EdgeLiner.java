package GalaxyFighter.objects.ship;

import GalaxyFighter.objects.weapons.SingleWeapon;
import engine.graphics.Palette;
import engine.graphics.Sprite;
import engine.window.Game;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class EdgeLiner extends PlayerShip {

    public EdgeLiner(Game game) {
        super(game);

        weapon = new SingleWeapon();

        baseAcceleration = 3000;
        maxSpeed = 500;
        brakeRatio = 5.6;

        size.set(16, 32);

        currentSprite = new Sprite(64, 48, "/images/Ship_Edgeliner.png");
        currentSprite.origin.set(23,8);
        currentSprite.setBasePalette(new Palette("/images/Ship_Edgeliner.palette.0.png"));


        List<Palette> paletteList = new ArrayList<>();
        paletteList.add(new Palette("/images/Ship_Edgeliner.palette.1.png"));

        currentSprite.applyPalette(paletteList.get(0));

        engineFireSprite = new Sprite(16, 16, "/images/EngineFire.png");
        engineFireSprite.setFramesPerFrame(10);
    }


    @Override
    public void draw(Graphics2D graphics) {
        super.draw(graphics);

        engineFireSprite.animate(true).draw(graphics, position.clone().add(1, 30));
    }
}
