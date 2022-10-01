package edu.hitsz.prop;

import edu.hitsz.aircraft.HeroAircraft;
import edu.hitsz.subscriber.*;

import java.util.ArrayList;
import java.util.List;

public class Froze extends AbstractProp{
    private List<SubscriberFroze> subscribers1 = new ArrayList<>();
    public static final Object Froze_LOCK=new Object();

    public void addSubscriber1(SubscriberFroze subscriber){
        subscribers1.add(subscriber);
    }


    public Froze(int locationX, int locationY, int speedX, int speedY) {
        super(locationX, locationY, speedX, speedY);
    }

    @Override
    public void active(HeroAircraft heroaircraft){
        Runnable r=()->{
            synchronized (Froze_LOCK){
                for(SubscriberFroze subscriber1:subscribers1){
                    subscriber1.update1();
                }
                try {
                    Froze_LOCK.wait(3000);
                }
                catch (InterruptedException e){
                    e.printStackTrace();
                }

                System.out.println("所有敌机减速3s结束");
                for (SubscriberFroze subscriber1:subscribers1){
                    subscriber1.reset();
                }
            }

        };
        Thread thread2 =new Thread(r);
        thread2.start();


    }
}
