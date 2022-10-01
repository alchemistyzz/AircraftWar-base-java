package edu.hitsz.prop;

public class FrozeFactory implements AbstractPropFactory{
    @Override
    public AbstractProp createProp(int locationX, int locationY, int speedX, int speedY) {
        return new Froze(locationX,locationY,speedX,speedY);
    }
}
