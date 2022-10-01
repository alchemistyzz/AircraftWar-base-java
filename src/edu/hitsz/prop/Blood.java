package edu.hitsz.prop;

import edu.hitsz.aircraft.AbstractAircraft;
import edu.hitsz.aircraft.HeroAircraft;

/**
 * 加血道具
 * 继承抽象道具的抽象类
 */

public class Blood extends AbstractProp{
    public Blood(int locationX, int locationY, int speedX, int speedY) {
        super(locationX, locationY, speedX, speedY);
    }

    @Override
    public void active(HeroAircraft heroaircraft){
        if(heroaircraft.getHp()<heroaircraft.getMaxHp()){

            heroaircraft.decreaseHp(Math.max(-60,heroaircraft.getHp()-heroaircraft.getMaxHp()));
        }

    }
}
