package edu.hitsz.aircraft;

import edu.hitsz.application.Main;
import edu.hitsz.bullet.BaseBullet;
import edu.hitsz.strategy.Strategy;
import edu.hitsz.subscriber.Subscriber;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Flow;

/**
 * 普通敌机
 * 不可射击
 *
 * @author hitsz
 */
public class MobEnemy extends AbstractAircraft{


    public MobEnemy(int locationX, int locationY, int speedX, int speedY, int hp) {
        super(locationX, locationY, speedX, speedY, hp);
        this.shootNum=1;
        this.power=30;
        this.direction=1;
    }

    @Override
    public void forward() {
        super.forward();
        // 判定 y 轴向下飞行出界
        if (locationY >= Main.WINDOW_HEIGHT ) {
            vanish();
        }
    }



    @Override
    public List<BaseBullet> executeShoot(int x, int y, int speedX, int speedY, int shootNum, int power, int direction,boolean flag) {
        return new LinkedList<>();
    }


}
