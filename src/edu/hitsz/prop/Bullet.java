package edu.hitsz.prop;

import edu.hitsz.aircraft.AbstractAircraft;
import edu.hitsz.aircraft.HeroAircraft;
import edu.hitsz.application.BaseGame;
import edu.hitsz.application.Main;
import edu.hitsz.play.MusicThread;
import edu.hitsz.strategy.ScatterShoot;
import edu.hitsz.strategy.StraightShoot;
import edu.hitsz.strategy.Strategy;

/**
 * 子弹补充道具
 * 继承抽象道具的抽象类
 */
public class Bullet extends AbstractProp{
    public Bullet(int locationX, int locationY, int speedX, int speedY) {
        super(locationX, locationY, speedX, speedY);
    }
    public static final Object Bullet_LOCK=new Object();
    @Override
    public void active(HeroAircraft heroaircraft){
        Runnable r= ()->{
            if(heroaircraft.getShootNum()<=3){
                heroaircraft.setShootNum(heroaircraft.getShootNum()+1);
            }
            synchronized (Bullet_LOCK){

                    try{
                        System.out.println("进入火力多线程");
                        heroaircraft.setStrategy(new ScatterShoot());
                       Bullet_LOCK.wait(3000);
                       System.out.println("结束等待3s，开始直射模式");
                       heroaircraft.setStrategy(new StraightShoot());
                       Bullet_LOCK.notify();

                    }catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("当前线程已经结束");

            }

        };
        Thread thread1 =new Thread(r);
        thread1.start();
    }
}
