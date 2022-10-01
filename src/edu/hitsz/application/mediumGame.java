package edu.hitsz.application;

import edu.hitsz.aircraft.*;
import edu.hitsz.play.MusicThread;

public class mediumGame extends BaseGame{
    public mediumGame(){
        super();
        BACKGROUND_IMAGE = ImageManager.BACKGROUND_IMAGE_MEDIUM;
        probability = 0.35;
        //无boss机
        has = true;
        cycleDuration = 800;
        enemyMaxNumber=3;
        limit=100;
        hasFroze=true;
        hasDamage=false;

    }

    @Override
    public void increase(){
        increaseEnemyProbabliliy=1.02;
        if (score%100==0&&score!=0){
            increaseEnemyProbabliliy *= 1.02;
            System.out.println("精英机产生概率提高至"+probability*increaseEnemyProbabliliy);
        }
    }

    @Override
    public void produceEnemy(){
        if (enemyAircrafts.size() < enemyMaxNumber) {

            double m=Math.random();
            if(m<probability*increaseEnemyProbabliliy) {
                enemyFactory=new MobEnemyFactory();
                MobEnemy mobEnemy=(MobEnemy) enemyFactory.createEnemy(
                        (int) (Math.random() * (Main.WINDOW_WIDTH - ImageManager.MOB_ENEMY_IMAGE.getWidth())) * 1,
                        (int) (Math.random() * Main.WINDOW_HEIGHT * 0.2) * 1,
                        0,
                        3,
                        60
                );
                enemyAircrafts.add(mobEnemy);


            }
            else if(m>=probability*increaseEnemyProbabliliy){
                enemyFactory=new EliteEnemyFactory();
                EliteEnemy eliteEnemy=(EliteEnemy) enemyFactory.createEnemy(
                        (int) (Math.random() * (Main.WINDOW_WIDTH - ImageManager.ELITE_ENEMY_IMAGE.getWidth())) * 1,
                        (int) (Math.random() * Main.WINDOW_HEIGHT * 0.2) * 1,
                        0,
                        3,
                        90
                );
                enemyAircrafts.add(eliteEnemy);

            }


        }
        if(score>=limit&&!surrvial&&has){
            enemyFactory=new BossEnemyFactory();
            enemyAircrafts.add(enemyFactory.createEnemy(
                    (int) (Math.random() * (Main.WINDOW_WIDTH - ImageManager.BOSS_ENEMY_IMAGE.getWidth())) * 1,
                    (int) (Math.random() * Main.WINDOW_HEIGHT * 0.2) * 1,
                    1,
                    0,
                    150));
            surrvial=true;
            limit+=100;


            bgmBossThread=new MusicThread("src/videos/bgm_boss.wav");
            bgmBossThread.setStop(!surrvial);
            bgmBossThread.setRecycle(surrvial);
            bgmBossThread.setSoundenabled(soundEnabled);
            bgmBossThread.start();
            existflag=true;
        }
    }

}
