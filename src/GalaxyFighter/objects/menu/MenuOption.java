package GalaxyFighter.objects.menu;

import java.awt.*;
import java.util.Objects;

public class MenuOption {

    public MenuOption(String t) {
        this(t, null);
    }

    public MenuOption(String t, MenuAction a) {
        text = t;
        action = a;
    }

    String text;
    MenuAction action;
    public boolean isSelectable() { return action != null; }

    public void select() {
        this.action.execute();
    }

    public interface MenuAction<T> {
        void execute();
    }

    static void drawCenteredString(Graphics g, String text, Rectangle rect, Font font) {
        // Get the FontMetrics
        FontMetrics metrics = g.getFontMetrics(font);
        // Determine the X coordinate for the text
        int x = rect.x + (rect.width - metrics.stringWidth(text)) / 2;
        // Determine the Y coordinate for the text (note we add the ascent, as in java 2d 0 is top of the screen)
        int y = rect.y + ((rect.height - metrics.getHeight()) / 2) + metrics.getAscent();
        // Set the font
        g.setFont(font);
        // Draw the String
        g.drawString(text, x, y);
    }


    public void draw(Graphics2D graphics, Rectangle rect, Font font) {
        drawCenteredString(graphics, text, rect, font);
    }
}