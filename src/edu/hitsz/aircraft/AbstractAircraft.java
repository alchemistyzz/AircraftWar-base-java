package edu.hitsz.aircraft;

import edu.hitsz.bullet.BaseBullet;
import edu.hitsz.basic.AbstractFlyingObject;
import edu.hitsz.strategy.Strategy;
import edu.hitsz.subscriber.*;

import java.util.List;

/**
 * 所有种类飞机的抽象父类：
 * 敌机（BOSS, ELITE, MOB），英雄飞机
 *
 * @author hitsz
 */
public abstract class AbstractAircraft extends AbstractFlyingObject  implements Subscriber , SubscriberFroze {
    /**
     * 生命值
     */
    /**攻击方式 */

    /**
     * 子弹一次发射数量
     */
    protected int shootNum ;

    /**
     * 子弹伤害
     */
    protected int power ;

    /**
     * 子弹射击方向 (向上发射：1，向下发射：-1)
     */
    protected int direction ;


    public int getShootNum() {
        return shootNum;
    }


    public int getDirection() {
        return direction;
    }


    public int getPower() {
        return power;
    }


    protected int maxHp;
    protected int hp;

    private Strategy strategy;

    public void AbstractAircraft(Strategy strategy){
        this.strategy=strategy;
    }

    public void setStrategy(Strategy strategy){
        this.strategy=strategy;
    }

    public AbstractAircraft(int locationX, int locationY, int speedX, int speedY, int hp) {
        super(locationX, locationY, speedX, speedY);
        this.hp = hp;
        this.maxHp = hp;
    }


    public void decreaseHp(int decrease){
        hp -= decrease;
        if(hp <= 0){
            hp=0;
            vanish();
        }
    }


    public int getHp() {
        return hp;
    }

    public void setHp(int hp){this.hp =maxHp; }

    public void setShootNum(int num){
        this.shootNum=num;

    }

    public Strategy getStrategy() {
        return strategy;
    }

    /**
     * 飞机射击方法，可射击对象必须实现
     * @return
     *  可射击对象需实现，返回子弹
     *  非可射击对象空实现，返回null
     */
    public List<BaseBullet> executeShoot(int x,int y,int speedX,int speedY,int shootNum,int power,int direction,boolean flag) {
        return strategy.shoot(x, y, speedX, speedY, shootNum, power, direction,flag);

    }
    @Override
    public void update(){
        this.isValid=false;
    }
    @Override
    public void update1(){
        speedX=(int)Math.round(speedX*0.5);
        speedY=(int)Math.round(speedY*0.5);
    }

    @Override
    public  void reset(){
        speedX=speedX*2;
        speedY=speedY*2;
    }

    public void setPower(int power) {
        this.power = power;
    }

    public int getMaxHp() {
        return maxHp;
    }

    public void setMaxHp(int maxHp) {
        this.maxHp = maxHp;
    }
}


