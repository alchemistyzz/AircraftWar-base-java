package edu.hitsz.aircraft;

import edu.hitsz.application.Main;
import edu.hitsz.bullet.BaseBullet;
import edu.hitsz.bullet.EnemyBullet;
import edu.hitsz.strategy.ScatterShoot;
import edu.hitsz.strategy.Strategy;

import java.util.LinkedList;
import java.util.List;

public class BossEnemy extends AbstractAircraft{

    public BossEnemy(int locationX, int locationY, int speedX, int speedY, int hp) {
        super(locationX, locationY, speedX, speedY, hp);
        this.shootNum=2;
        this.power=30;
        this.direction=1;
        setStrategy(new ScatterShoot());
    }




    @Override
    public void forward() {
        super.forward();
        // 判定 y 轴向下飞行出界
        if (locationY >= Main.WINDOW_HEIGHT ) {
            vanish();
        }
    }




}
