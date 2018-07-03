package GalaxyFighter.objects.stage;

import GalaxyFighter.objects.menu.TitleScreen;
import GalaxyFighter.objects.ship.BaseShip;
import GalaxyFighter.objects.ship.Enemy.Enemy03;
import GalaxyFighter.objects.ship.PlayerShip;
import engine.graphics.Color;
import engine.sound.Music;
import engine.sound.SoundEffect;
import engine.window.Transition.FadeIn;
import engine.window.Transition.FadeOut;

public class Stage4 extends GalaxyStage {

    Music realBgm;
    static SoundEffect winJingle = new SoundEffect("/sound/sfx/Win_Jingle.wav").setVolume(.5);

    public Stage4(PlayerShip p) {
        super(p);

        realBgm = new Music("/sound/music/04_Boss_Main.mp3").loop().setVolume(.15);

        bgm = new Music("/sound/music/04_Boss_Intro.mp3").setVolume(.15).next(realBgm);

        onAdd.addListener(x -> {
            BaseShip ship;

            for (int j = 0; j < 2; j++) {
                for (int i = 0; i < (1 - j); i++) {
                    ship = new Enemy03();
                    ship.setPosition((120 * i) + (70 * j) + 45, 100 + (j * 100));
                    addObject(ship);
                }
            }
        });

        onRemove.addListener(x -> {
            realBgm.stop();
        });
    }

    public void nextStage() {
        FadeOut fadeOut = new FadeOut(new engine.graphics.Color(0,0,0), 4, game);
        FadeIn fadeIn = new FadeIn(new Color(0,0,0), .5, game);
        fadeOut.chain(fadeIn);

        fadeOut.onEnd.addListener(x -> {
            swapFor(new TitleScreen());
        });

        realBgm.stop();
        winJingle.start();

        transitioning = true;
        game.transition(fadeOut);
    }
}
