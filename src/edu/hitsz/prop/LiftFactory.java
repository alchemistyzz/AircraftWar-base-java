package edu.hitsz.prop;

public class LiftFactory implements AbstractPropFactory{
    @Override
    public AbstractProp createProp(int locationX, int locationY, int speedX, int speedY) {
        return new Lift(locationX,locationY,speedX,speedY);
    }
}
