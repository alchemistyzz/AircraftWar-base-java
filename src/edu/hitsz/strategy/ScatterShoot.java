package edu.hitsz.strategy;

import edu.hitsz.bullet.BaseBullet;
import edu.hitsz.bullet.EnemyBullet;
import edu.hitsz.bullet.HeroBullet;

import java.util.LinkedList;
import java.util.List;

public class ScatterShoot implements Strategy{

    @Override
    public List<BaseBullet> shoot(int x, int y, int speedX, int speedY, int shootNum, int power, int direction,boolean flag) {

        List<BaseBullet> res = new LinkedList<>();
        int X = x;
        int Y = y + direction * 2;
        int[] Speedx = new int[shootNum];
        if(shootNum%2!=0){
            for (int i = 0; i < shootNum; i++) {
            Speedx[i] = -speedX + 2 * speedX / shootNum * i;
             }
        }
        else if (shootNum%2==0){
            for(int i=0;i<shootNum;i++)
            {
                Speedx[i]=-speedX+2*speedX/(shootNum-1)*i;
            }
        }
        int SpeedY = speedY + direction * 5;
        BaseBullet baseBullet;
        if (flag) {
            for (int i = 0; i < shootNum; i++) {
                // 子弹发射位置相对飞机位置向前偏移
                // 多个子弹横向分散
                baseBullet = new EnemyBullet(X + (i * 2 - shootNum + 1) * 10, Y, Speedx[i], SpeedY, power);
                res.add(baseBullet);
            }

        }

    else {
        for (int i = 0; i < shootNum; i++) {
            // 子弹发射位置相对飞机位置向前偏移
            // 多个子弹横向分散
            baseBullet = new HeroBullet(X + (i * 2 - shootNum + 1) * 10, Y, Speedx[i], SpeedY, power);
            res.add(baseBullet);
        }

    }
    return res;
  }
}
