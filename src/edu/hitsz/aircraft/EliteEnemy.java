package edu.hitsz.aircraft;
import edu.hitsz.application.BaseGame;
import edu.hitsz.application.Main;
import edu.hitsz.bullet.BaseBullet;
import edu.hitsz.bullet.EnemyBullet;
import edu.hitsz.strategy.Strategy;
import edu.hitsz.subscriber.Subscriber;


import java.util.LinkedList;
import java.util.List;

public class EliteEnemy extends AbstractAircraft{


    public EliteEnemy(int locationX, int locationY, int speedX, int speedY, int hp) {
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
    public int getLocationX() {
        return super.getLocationX();
    }

    @Override
    public int getLocationY() {
        return super.getLocationY();
    }


}