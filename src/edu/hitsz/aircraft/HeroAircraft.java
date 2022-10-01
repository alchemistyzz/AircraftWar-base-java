package edu.hitsz.aircraft;

import edu.hitsz.bullet.BaseBullet;
import edu.hitsz.bullet.HeroBullet;

import java.util.LinkedList;
import java.util.List;

/**
 * 英雄飞机，游戏玩家操控
 * @author hitsz
 */
public class HeroAircraft extends AbstractAircraft {

    private static  HeroAircraft instance = null;
    private HeroAircraft(int locationX, int locationY, int speedX, int speedY, int hp) {
        super(locationX, locationY, speedX, speedY, hp);
        this.shootNum=1;
        this.power=30;
        this.direction=-1;
        this.maxHp=300;
    }
    public static synchronized HeroAircraft getInstance(int locationX, int locationY, int speedX, int speedY, int hp){
        if(instance==null){
            instance = new HeroAircraft(locationX, locationY, speedX, speedY, hp);
        }
        return  instance;
    }

    @Override
    public void forward() {
        // 英雄机由鼠标控制，不通过forward函数移动
    }
    @Override
    public void setShootNum(int shootNum){
        this.shootNum=shootNum;
    }

    @Override
    public void setHp(int hp) {
        super.setHp(hp);
    }




}
