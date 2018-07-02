package GalaxyFighter.objects.util;

import engine.base.Size;
import engine.base.Vector;

import java.awt.*;
import java.util.Arrays;
import java.util.List;
import engine.graphics.Color;

public class HP {
    Color borderColor = new Color(255, 255, 255, 120);
    Color backgroundColor = new Color(0, 0, 0, 120);
    List<Color> mainColors = Arrays.asList(
            new Color(255, 0, 0, 0),
            new Color(0,255, 0, 0)
    );

    Size size = new Size(32,4);

    public int current = 0;
    public int total = 0;

    public HP(int max) {
        total = max;
        current = max;
    }

    public HP add(int val) {
        current += val;

        if (current > total) {
            current = total;
        } else if (current < 0) {
            current = 0;
        }

        return this;
    }

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


    public void draw(Graphics2D graphics, Vector position) {
        draw(graphics, position, 1);
    }
    public void draw(Graphics2D graphics, Vector position, double alpha) {
        double ratio =  (double)current/total;

        Rectangle box = new Rectangle((int) position.x, (int) position.y, size.width, size.height);

        graphics.setColor(backgroundColor.scaleAlpha(alpha));
        graphics.fill(box);

        graphics.setColor(borderColor.scaleAlpha(alpha));
        graphics.draw(box);

        box = new Rectangle((int) position.x+1, (int) position.y+1, (int) ((size.width-1)*ratio), size.height-1);

        graphics.setColor(getColor(ratio).scaleAlpha(alpha));
        graphics.fill(box);
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
