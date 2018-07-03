package GalaxyFighter.objects.score;

import engine.resources.ResourceLoader;

import java.awt.*;

public class Score {

    protected int value = 0;

    private Score(){}

    private static Score instance;

    public static Score getInstance(){
        if( instance == null){

            instance = new Score();
        }

        return instance;

    }

    public int getValue(){

        return value;
    }

    public void modify(int addedValue){

        value += addedValue;
    }

    public void draw(Graphics2D graphics){

        graphics.setColor(Color.white);
        graphics.setFont(ResourceLoader.loadFont("/fonts/pixelmix.ttf").deriveFont( (float)15) );
        graphics.drawString("Score: "+getValue(),1100,700  );

    }
}
