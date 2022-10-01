package edu.hitsz.strategy;

import edu.hitsz.aircraft.AbstractAircraft;
import edu.hitsz.bullet.BaseBullet;

import java.util.List;

public interface Strategy {
    List<BaseBullet> shoot(int x,int y,int speedX,int speedY,int shootNum,int power,int direction,boolean flag);

}
