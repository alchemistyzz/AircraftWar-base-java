package edu.hitsz.prop;

public class DamageFactory implements AbstractPropFactory{
    @Override
    public AbstractProp createProp(int locationX, int locationY, int speedX, int speedY) {
        return new Damage(locationX,locationY,speedX,speedY);
    }
}
