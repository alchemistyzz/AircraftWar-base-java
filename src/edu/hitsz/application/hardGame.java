package edu.hitsz.application;

import edu.hitsz.aircraft.*;
import edu.hitsz.dao.DataDao;
import edu.hitsz.play.MusicThread;
import edu.hitsz.strategy.ScatterShoot;
import edu.hitsz.strategy.StraightShoot;

public class hardGame extends BaseGame{
   public hardGame(){
       super();
       BACKGROUND_IMAGE=ImageManager.BACKGROUND_IMAGE_HARD;
       probability = 0.4;
       has = true;/*有boss机*/
       cycleDuration = 750;
       enemyMaxNumber=4;
       limit=80;//产生敌机的分数阈值
       hasFroze=true;
       hasDamage=true;

   }

   @Override
   public void increase(){
       if (score>=limit1&&score!=0&&score!=limitReg){
           System.out.println("score="+score);
           if(score<=2000) {
               increaseEnemyProbabliliy *= 1.05;
               System.out.println("精英机产生概率提高至" + probability * increaseEnemyProbabliliy);

               increaseEnemyHp *= 1.05;
               System.out.println("普通敌机血量提高至" + (int) Math.round(30 * increaseEnemyHp));
               System.out.println("精英敌机血量提高至" + (int) Math.round(60 * increaseEnemyHp));
               if (surrvial) {
                   System.out.println("Boss机血量提高至" + (int) Math.round(200 * increaseEnemyHp));
               }

               increaseEnemySpeed *= 1.05;
               System.out.println("普通敌机纵向速度提升至" + (int) Math.round(4 * increaseEnemySpeed));
               System.out.println("精英敌机纵向速度提升至" + (int) Math.round(4 * increaseEnemySpeed));
               if (surrvial) {
                   System.out.println("Boss机横向速度提升至" + (int) Math.round(1 * increaseEnemySpeed));
               }

               increaseCycleDuration *= 1.005;
               System.out.println("游戏速度加快倍率：" + increaseCycleDuration);
               cycleDuration = (int) Math.round(cycleDuration / increaseCycleDuration);


           }
           else {
               System.out.println("精英机产生概率提高至最高为" + probability * increaseEnemyProbabliliy);
               System.out.println("普通敌机血量提高至最高为" + (int) Math.round(30 * increaseEnemyHp));
               System.out.println("精英敌机血量提高至最高为" + (int) Math.round(60 * increaseEnemyHp));
               System.out.println("普通敌机纵向速度提升至最高为" + (int) Math.round(4 * increaseEnemySpeed));
               System.out.println("精英敌机纵向速度提升至最高为" + (int) Math.round(4 * increaseEnemySpeed));
               if (surrvial) {
                   System.out.println("Boss机血量提高至最高为" + (int) Math.round(200 * increaseEnemyHp));
                   System.out.println("Boss机横向速度提升至最高为" + (int) Math.round(1 * increaseEnemySpeed));
               }
               System.out.println("游戏速度加快倍率最高为" + increaseCycleDuration);

           }


           limitReg=limit1;
           limit1+=100;

      }
   }
    @Override
    public void produceEnemy(){
        if (enemyAircrafts.size() < enemyMaxNumber) {

            double m=Math.random();
            if(m<probability) {
                enemyFactory=new MobEnemyFactory();
                MobEnemy mobEnemy=(MobEnemy) enemyFactory.createEnemy(
                        (int) (Math.random() * (Main.WINDOW_WIDTH - ImageManager.MOB_ENEMY_IMAGE.getWidth())) * 1,
                        (int) (Math.random() * Main.WINDOW_HEIGHT * 0.2) * 1,
                        0,
                        (int)Math.round(4*increaseEnemySpeed),
                        (int)Math.round(30*increaseEnemyHp)
                );
                enemyAircrafts.add(mobEnemy);


            }
            else if(m>=probability){
                enemyFactory=new EliteEnemyFactory();
                EliteEnemy eliteEnemy=(EliteEnemy) enemyFactory.createEnemy(
                        (int) (Math.random() * (Main.WINDOW_WIDTH - ImageManager.ELITE_ENEMY_IMAGE.getWidth())) * 1,
                        (int) (Math.random() * Main.WINDOW_HEIGHT * 0.2) * 1,
                        0,
                        (int)Math.round(4*increaseEnemySpeed),
                        (int)Math.round(60*increaseEnemyHp)
                );
                enemyAircrafts.add(eliteEnemy);

            }


        }
        if(score>=limit&&!surrvial&&has){
            enemyFactory=new BossEnemyFactory();
            BossEnemy bossEnemy=(BossEnemy) enemyFactory.createEnemy(
                    (int) (Math.random() * (Main.WINDOW_WIDTH - ImageManager.BOSS_ENEMY_IMAGE.getWidth())) * 1,
                    (int) (Math.random() * Main.WINDOW_HEIGHT * 0.2) * 1,
                    (int)Math.round(1*increaseEnemySpeed),
                    0,
                    (int)Math.round(300*increaseEnemyHp));

            enemyAircrafts.add(bossEnemy);
            bossEnemy.setShootNum(6);
            bossEnemy.setStrategy(new ScatterShoot());
            surrvial=true;
            limit+=200;



            bgmBossThread=new MusicThread("src/videos/bgm_boss.wav");
            bgmBossThread.setStop(!surrvial);
            bgmBossThread.setRecycle(surrvial);
            bgmBossThread.setSoundenabled(soundEnabled);
            bgmBossThread.start();
            existflag=true;
        }
    }

}
