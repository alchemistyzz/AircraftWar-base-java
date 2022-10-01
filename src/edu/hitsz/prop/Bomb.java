package edu.hitsz.prop;

import edu.hitsz.aircraft.AbstractAircraft;
import edu.hitsz.aircraft.HeroAircraft;
import edu.hitsz.subscriber.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Flow;

/**
 * 炸弹道具
 * 继承抽象道具的抽象类
 */
public class Bomb extends AbstractProp{
    private List<Subscriber> subscribers = new ArrayList<>();

    public void addSubscriber(Subscriber subscriber){
        subscribers.add(subscriber);
    }

    public Bomb(int locationX, int locationY, int speedX, int speedY) {
        super(locationX, locationY, speedX, speedY);
    }
    @Override
    public void active(HeroAircraft heroaircraft){
        for (Subscriber subscriber:subscribers){
            subscriber.update();
        }
    }
}
