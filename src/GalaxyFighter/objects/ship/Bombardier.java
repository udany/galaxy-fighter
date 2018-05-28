package GalaxyFighter.objects.ship;

import GalaxyFighter.objects.weapons.SimpleWeapon;
import engine.graphics.Palette;
import engine.graphics.Sprite;
import engine.window.Game;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Bombardier extends BaseShip {

    public Bombardier(Game game) {
        super(game);

        weapon = new SimpleWeapon();

        baseAcceleration = .5;
        maxSpeed = 3;
        brakeRatio = 0.9;

        size.set(16, 32);

        currentSprite = new Sprite(64, 48, "/images/Ship_Bombardier.png");
        currentSprite.origin.set(23,8);

        currentSprite.setBasePalette(new Palette("/images/Ship_Bombardier.palette.0.png"));


        List<Palette> paletteList = new ArrayList<>();
        paletteList.add(new Palette("/images/Ship_Bombardier.palette.1.png"));

        currentSprite.applyPalette(paletteList.get(0));

        engineFire = new Sprite(16, 16, "/images/EngineFire.png");
        engineFire.setFramesPerFrame(3);
    }


    @Override
    public void draw(Graphics2D graphics) {
        super.draw(graphics);

        engineFire.animate(true).draw(graphics, position.clone().add(1, 30));
    }
}
