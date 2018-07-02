package GalaxyFighter.objects.util;

import engine.base.Size;
import engine.base.Vector;

import java.awt.*;
import java.util.Arrays;
import java.util.List;
import engine.graphics.Color;
import engine.window.Game;
import engine.window.TweenHelper;

public class HP {
    Color borderColor = new Color(255, 255, 255, 120);
    Color backgroundColor = new Color(0, 0, 0, 120);
    List<Color> mainColors = Arrays.asList(
            new Color(255, 0, 0, 120),
            new Color(0,255, 0, 120)
    );

    public Size size = new Size(32,4);

    private int current = 0;
    private double currentGap = 0;
    private double currentVisual = 0;
    protected TweenHelper tween;
    public int total = 0;

    public HP(int max, Game game) {
        total = max;

        tween = new TweenHelper(.5, progress -> {
            currentVisual = current - (currentGap * (1 - progress));
        }, game.onUpdate);

        add(max);
    }

    public HP add(int val) {
        current += val;

        if (current > total) {
            current = total;
        } else if (current < 0) {
            current = 0;
        }

        currentGap = current - currentVisual;
        tween.reset();
        tween.start();

        return this;
    }
    public int getCurrent() { return current; }

    public HP setColors(List<Color> colors) {
        mainColors = colors;
        return this;
    }
    public HP setBackground(Color color) {
        backgroundColor = color;
        return this;
    }
    public HP setBorder(Color color) {
        borderColor = color;
        return this;
    }


    boolean debug = false;

    public void draw(Graphics2D graphics, Vector position) {
        draw(graphics, position, 1);
    }
    public void draw(Graphics2D graphics, Vector position, double alpha) {
        double ratio =  currentVisual/total;

        Rectangle box = new Rectangle((int) position.x, (int) position.y, size.width, size.height);

        graphics.setColor(backgroundColor.scaleAlpha(alpha));
        graphics.fill(box);

        graphics.setColor(borderColor.scaleAlpha(alpha));
        graphics.draw(box);

        box = new Rectangle((int) position.x+1, (int) position.y+1, (int) ((size.width-1)*ratio), size.height-1);

        graphics.setColor(getColor(ratio).scaleAlpha(alpha));
        graphics.fill(box);


        if (debug) {
            Vector p = position.clone().subtract(10, 12);

            graphics.setFont( new Font( "Courier New", Font.PLAIN, 10 ) );
            graphics.setColor(new java.awt.Color(255, 15, 100,100 ));

            graphics.drawString(String.format("%s/%s", current, total), (int) p.x, (int) p.y-10 );
            graphics.drawString(String.format("%s, %s", currentVisual, currentGap), (int) p.x, (int) p.y );
        }
    }

    protected Color getColor(double ratio) {
        double colorLength = (double)1 / (mainColors.size()-1);

        for (int l = 0; l < mainColors.size(); l++) {
            if (l*colorLength <= ratio && (l+1)*colorLength >= ratio) {
                ratio -= l*colorLength;
                ratio = ratio / colorLength;

                return Color.transition(mainColors.get(l), mainColors.get(l+1), ratio);
            }
        }

        return mainColors.get(mainColors.size()-1);
    }
}
