package GalaxyFighter.objects.util;

import engine.base.Size;
import engine.base.Vector;

import java.awt.*;
import java.util.Arrays;
import java.util.List;

public class HP {
    Color borderColor = new Color(255, 255, 255, 120);
    Color backgroundColor = new Color(0, 0, 0, 120);
    List<Color> mainColors = Arrays.asList(Color.red, Color.green);
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

        graphics.setColor(setAlpha(backgroundColor, alpha));
        graphics.fill(box);

        graphics.setColor(setAlpha(borderColor, alpha));
        graphics.draw(box);

        box = new Rectangle((int) position.x+1, (int) position.y+1, (int) ((size.width-1)*ratio), size.height-1);

        graphics.setColor(setAlpha(getColor(ratio), alpha));
        graphics.fill(box);
    }

    protected Color getColor(double ratio) {
        double colorLength = (double)1 / (mainColors.size()-1);

        for (int l = 0; l < mainColors.size(); l++) {
            if (l*colorLength <= ratio && (l+1)*colorLength >= ratio) {
                ratio -= l*colorLength;
                ratio = ratio / colorLength;

                return transition(mainColors.get(l), mainColors.get(l+1), ratio);
            }
        }

        return mainColors.get(mainColors.size()-1);
    }

    protected Color transition(Color from, Color to, double ratio) {
        float red = (float)Math.abs((ratio * to.getRed()) + ((1 - ratio) * from.getRed()));
        float green = (float)Math.abs((ratio * to.getGreen()) + ((1 - ratio) * from.getGreen()));
        float blue = (float)Math.abs((ratio * to.getBlue()) + ((1 - ratio) * from.getBlue()));

        return new Color(red/255, green/255, blue/255);
    }

    protected Color setAlpha(Color c, double alpha) {
        return new Color(c.getRed(), c.getGreen(), c.getBlue(), (int) (c.getAlpha() * alpha));
    }
}
