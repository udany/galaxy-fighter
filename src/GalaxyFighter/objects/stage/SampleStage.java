package GalaxyFighter.objects.stage;

import GalaxyFighter.objects.menu.TitleScreen;
import GalaxyFighter.objects.ship.BaseShip;
import GalaxyFighter.objects.ship.Enemy.BaseEnemyShip;
import GalaxyFighter.objects.ship.PlayerShip;

import engine.graphics.Color;
import engine.main.Stage;
import engine.sound.Music;
import engine.window.Transition.FadeIn;
import engine.window.Transition.FadeOut;

public class SampleStage extends Stage {
    static Music bgm = new Music("/sound/music/03_Field_force.mp3");

    public SampleStage(PlayerShip player) {
        player.setPosition(680, 720-48);
        addObject(player);

        bgm.setVolume(.15);

        onAdd.addListener(x -> {
            bgm.start();
        });

        onRemove.addListener(x -> {
            bgm.stop();
        });

        BaseShip ship;
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 1; j++) {
                ship = new BaseEnemyShip();
                ship.setPosition((128 * i) +40, (j * 72) + 15);
                addObject(ship);
            }
        }

        player.onDeath.addListener(x -> {
            returnToTitle();
        });
    }

    public void returnToTitle() {
        FadeOut fadeOut = new FadeOut(new Color(0,0,0), .5, game);
        FadeIn fadeIn = new FadeIn(new Color(0,0,0), .5, game);
        fadeOut.chain(fadeIn);

        fadeOut.onEnd.addListener(x -> {
            game.removeStage(this);
            game.addStage(new TitleScreen());
        });

        game.transition(fadeOut);
    }
}
