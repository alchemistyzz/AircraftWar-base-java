package edu.hitsz.prop;

import edu.hitsz.aircraft.HeroAircraft;

public class Lift extends AbstractProp{
    public Lift(int locationX, int locationY, int speedX, int speedY) {
        super(locationX, locationY, speedX, speedY);
    }

    @Override
    public void active(HeroAircraft heroaircraft){
        if(heroaircraft.getHp()<heroaircraft.getMaxHp()){
            heroaircraft.decreaseHp(-60);
        }
        heroaircraft.setMaxHp(heroaircraft.getMaxHp()+50);
    }
}
