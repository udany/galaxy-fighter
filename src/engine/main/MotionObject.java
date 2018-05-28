package engine.main;

import engine.base.Vector;

public abstract class MotionObject extends GameObject {
    protected Vector speed = new Vector(0,0);
    protected Vector acceleration = new Vector(0,0);

    @Override
    public void update() {
        speed.add(acceleration);
        position.add(speed);
    }


}
