package edu.hitsz.application;


import edu.hitsz.aircraft.*;
import edu.hitsz.play.MusicThread;

public class easyGame extends BaseGame{
    public easyGame() {
        super();
        BACKGROUND_IMAGE = ImageManager.BACKGROUND_IMAGE_EASY;
        probability = 0.2;
        has = false;/*无boss机*/
        cycleDuration = 1000;
        enemyMaxNumber=2;
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
                        2,
                        30
                );
                enemyAircrafts.add(mobEnemy);


            }
            else if(m>=probability){
                enemyFactory=new EliteEnemyFactory();
                EliteEnemy eliteEnemy=(EliteEnemy) enemyFactory.createEnemy(
                        (int) (Math.random() * (Main.WINDOW_WIDTH - ImageManager.ELITE_ENEMY_IMAGE.getWidth())) * 1,
                        (int) (Math.random() * Main.WINDOW_HEIGHT * 0.2) * 1,
                        0,
                        2,
                        30
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
                    200));
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



