package edu.hitsz.prop;

import edu.hitsz.aircraft.HeroAircraft;

public class Damage extends AbstractProp{
    public Damage(int locationX, int locationY, int speedX, int speedY) {
        super(locationX, locationY, speedX, speedY);
    }

    @Override
    public void active(HeroAircraft heroaircraft){

            heroaircraft.setPower((int)Math.round(heroaircraft.getPower()*1.05));
            System.out.println("当前英雄机的火力伤害为"+heroaircraft.getPower());

    }

}
