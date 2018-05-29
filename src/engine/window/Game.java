package engine.window;

import engine.base.Size;
import engine.input.Keyboard;
import engine.main.GameObject;
import engine.util.Event;
import engine.util.MyFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public abstract class Game extends MyFrame {
    public Size size = new Size();
    private Color background;
    private Canvas canvas;

    protected List<GameObject> objectList;

    protected Keyboard keyboard = Keyboard.getInstance();

    public Event<Graphics2D> onPaint = new Event<>();
    public Event<Long> onUpdate = new Event<>();

    public Game(int w, int h){
        super();

        background = Color.BLACK;

        setIgnoreRepaint( true );
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        size.height = h;
        size.width = w;
        setSize(size.width, size.height);
        setResizable(false);
        centerOnScreen();

        objectList = new ArrayList<>();
        onPaint.addListener(this::draw);
        onUpdate.addListener(this::update);

        // Create canvas for painting...
        canvas = new Canvas();
        canvas.setIgnoreRepaint( true );
        canvas.setSize( size.width, size.height);

        // Add canvas to game window...
        add(canvas);
        pack();
        setVisible(true);
    }


    private final int frameRate = 250;
    private BufferStrategy buffer;
    private Graphics2D g2d;
    private Graphics graphics;
    private BufferedImage bufferImage;
    private double fps;
    private boolean debug = true;
    private int msPerFrame;

    private void setup() {
        canvas.createBufferStrategy( 2 );
        buffer = canvas.getBufferStrategy();

        // Get graphics configuration...
        GraphicsEnvironment ge =
                GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice gd = ge.getDefaultScreenDevice();
        GraphicsConfiguration gc = gd.getDefaultConfiguration();

        // Create off-screen drawing surface
        bufferImage = gc.createCompatibleImage( size.width, size.height);

        msPerFrame = 1000/frameRate;
    }

    public void start() {
        setup();

        // Variables for counting frames per seconds
        int frames = 0;
        long elapsed = 0;
        long lastTime = System.currentTimeMillis();

        while( true ) {
            try {
                // count Frames per second...
                elapsed = System.currentTimeMillis() - lastTime;
                if (elapsed >= msPerFrame) {
                    lastTime = System.currentTimeMillis();

                    fps = 1000 / elapsed;

                    // clear back buffer...
                    g2d = bufferImage.createGraphics();
                    onUpdate.emit(elapsed);
                    onPaint.emit(g2d);

                    // Blit image and flip...
                    graphics = buffer.getDrawGraphics();
                    graphics.drawImage(bufferImage, 0, 0, null );
                    if( !buffer.contentsLost() )
                        buffer.show();
                }

                // Let the OS have a little time...
                Thread.yield();
            } finally {
                // release resources
                if( graphics != null )
                    graphics.dispose();
                if( g2d != null )
                    g2d.dispose();
            }
        }
    }

    protected void update(long elapsedMs){
        List<GameObject> objectsToRemove = objectList.stream().filter(x->x.isDestroyed()).collect(Collectors.toList());

        objectList.removeAll(objectsToRemove);
        objectList.addAll(objectsToAdd);
        objectsToAdd.clear();

        for(GameObject o : objectList){
            o.update(((double)elapsedMs) / 1000);
        }
        collisionChecking();
    }

    protected void collisionChecking(){
        List<GameObject> objectsToCheck = objectList.stream().filter(x->x.checkForCollisions()).collect(Collectors.toList());
        List<GameObject> solidObjects = objectList.stream().filter(x->x.isSolid()).collect(Collectors.toList());

        for (GameObject obj : objectsToCheck){
            for (GameObject solidObj : solidObjects){
                Shape objShape = obj.getCollisionArea();
                Shape solidShape = solidObj.getCollisionArea();

                if (objShape.intersects(solidShape.getBounds())){
                    obj.onCollision.emit(solidObj);
                }
            }
        }
    }

    protected void draw(Graphics2D graphics){
        graphics.setColor( background );
        graphics.fillRect( 0, 0, size.width, size.height);

        for(GameObject o : objectList){
            o.draw(graphics);
        }

        if (debug) {
            graphics.setFont( new Font( "Courier New", Font.PLAIN, 12 ) );
            graphics.setColor( Color.GREEN );
            graphics.drawString( String.format( "FPS: %s", fps ), 20, 20 );
        }
    }


    protected List<GameObject> objectsToAdd = new ArrayList<>();
    public void addObject(GameObject obj) {
        objectsToAdd.add(obj);
    }
}
