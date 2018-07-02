package GalaxyFighter.objects.menu;

import engine.input.Keyboard;
import engine.main.GameObject;
import engine.resources.ResourceLoader;
import engine.sound.SoundEffect;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.List;


public class Menu extends GameObject {

    List<MenuOption> options;
    MenuOption selectedOption;
    Color color = Color.yellow;
    Color selectedColor = Color.yellow.brighter();
    Font font;
    boolean loopable = true;
    double blinkInterval = .2;
    double blinkLength = .2;
    double blinkTimeElapsed = 0;
    boolean isBlinking = false;

    SoundEffect select = new SoundEffect("/sound/sfx/Select.wav").setVolume(.1);
    SoundEffect success = new SoundEffect("/sound/sfx/Success.wav").setVolume(.25);

    public Menu(List<MenuOption> items) {
        super();

        this.options = items;
        move(1);

        Keyboard kb = Keyboard.getInstance();

        onAdd.addListener((stage) -> {
            currentStage.onKeyDown.addListener(key -> {
                if (key == KeyEvent.VK_DOWN) {
                    move(1);
                    select.start();
                } else if (key == KeyEvent.VK_UP) {
                    move(-1);
                    select.start();
                } else if (key == KeyEvent.VK_ENTER || key == KeyEvent.VK_SPACE) {
                    select();
                    success.start();
                }
            }, id.toString());
        });
    }

    public void move(int direction) {
        int idx = options.indexOf(selectedOption);

        if (idx+direction >= options.size()) {
            if (loopable) {
                selectedOption = options.get(0);
            }
        } else if (idx+direction < 0) {
            if (loopable) {
                selectedOption = options.get(options.size()-1);
            }
        } else {
            selectedOption = options.get(idx + direction);
        }

        if (!selectedOption.isSelectable()) {
            move(direction);
        }
    }

    public void select() {
        selectedOption.select();
    }

    public Menu setFont(String file, double size) {
        font = ResourceLoader.loadFont(file).deriveFont((float) size).deriveFont(Font.PLAIN);

        return this;
    }

    @Override
    public void update(double secondsElapsed) {
        super.update(secondsElapsed);
        blinkTimeElapsed += secondsElapsed;

        if (isBlinking) {
            if (blinkTimeElapsed >= blinkLength) {
                blinkTimeElapsed = 0;
                isBlinking = false;
            }
        } else {
            if (blinkTimeElapsed >= blinkInterval) {
                blinkTimeElapsed = 0;
                isBlinking = true;
            }
        }
    }

    @Override
    public void draw(Graphics2D graphics) {
        super.draw(graphics);

        Rectangle bounds = new Rectangle(0, 320, 1280, font.getSize());

        for (MenuOption o : options) {
            bounds.y += font.getSize() * 1.5;

            if (o == selectedOption) {
                if (isBlinking) continue;
                graphics.setColor(selectedColor);
            } else {
                graphics.setColor(color);
            }

            o.draw(graphics, bounds, font);
        }
    }
}
