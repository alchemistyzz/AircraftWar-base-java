package edu.hitsz.prop;
/**
 * 炸弹道具工厂
 * 采用工厂模式引用抽象工厂的接口，具体实现创建方法
 * 负责具体的炸弹道具的工厂制造
 */
public class BombFactory implements AbstractPropFactory{
    @Override
    public AbstractProp createProp(int locationX, int locationY, int speedX, int speedY) {
        return new Bomb(locationX,locationY,speedX,speedY);
    }
}
