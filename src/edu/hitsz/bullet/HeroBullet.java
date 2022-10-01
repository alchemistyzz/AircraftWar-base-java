package edu.hitsz.bullet;

import edu.hitsz.subscriber.Subscriber;

/**
 * @Author hitsz
 */
public class HeroBullet extends BaseBullet implements Subscriber {

    public HeroBullet(int locationX, int locationY, int speedX, int speedY, int power) {
        super(locationX, locationY, speedX, speedY, power);
    }
    @Override
    public void update(){
        this.isValid=false;
    }

}
