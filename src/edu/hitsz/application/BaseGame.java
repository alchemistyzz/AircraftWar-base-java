package edu.hitsz.application;

import edu.hitsz.aircraft.*;
import edu.hitsz.basic.AbstractFlyingObject;
import edu.hitsz.bullet.BaseBullet;
import edu.hitsz.play.MusicThread;
import edu.hitsz.prop.*;
import edu.hitsz.strategy.*;
import org.apache.commons.lang3.concurrent.BasicThreadFactory;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public abstract class BaseGame extends JPanel {
    protected int backGroundTop = 0;

    /**
     * Scheduled 线程池，用于任务调度
     */
    protected final ScheduledExecutorService executorService;

    /**
     * 时间间隔(ms)，控制刷新频率
     */
    protected int timeInterval = 40;

    protected final HeroAircraft heroAircraft;
    protected final List<AbstractAircraft> enemyAircrafts;
    protected final List<BaseBullet> heroBullets;
    protected final List<BaseBullet> enemyBullets;
    protected final List<AbstractProp> props;
    protected EnemyFactory enemyFactory;
    protected AbstractPropFactory propFactory;
    protected boolean surrvial = false;
    protected BufferedImage BACKGROUND_IMAGE;
    private MusicThread bgmThread=new MusicThread("src/videos/bgm_1.wav");
    private MusicThread gameOverThread=new MusicThread("src/videos/game_over.wav");
    protected MusicThread bgmBossThread;
    private MusicThread bulletHitThread;
    private MusicThread bombExplosionThread;
    private MusicThread getSupplyThread;
    protected boolean existflag = false;
    protected boolean hasFroze = false;
    protected boolean hasDamage= false;

    protected boolean soundEnabled;
    private boolean gameOverFlag=false;
    protected int enemyMaxNumber = 3;
    protected int time=0;
    protected int limit=50;
    protected int limit1=50;
    protected int limitReg=0;
    protected boolean has=true;
    protected int score=0;
    protected double probability=0.5;
    protected double increaseEnemyProbabliliy=1;
    protected double increaseEnemyHp=1;
    protected double increaseEnemySpeed=1;
    protected double increaseCycleDuration=1;
    protected int shootnum=1;
    protected boolean first=true;
    /**
     * 周期（ms)
     * 指示子弹的发射、敌机的产生频率
     */
    protected int cycleDuration = 600;
    protected int cycleTime = 0;


    public boolean isSoundEnabled() {
        return soundEnabled;
    }

    public void setSoundEnabled(boolean soundEnabled) {
        this.soundEnabled = soundEnabled;
    }
    public static BaseGame getGameOf(String difficulty){
        if(difficulty=="easy"){
            return new easyGame();
        }
        else if(difficulty=="medium"){
            return new mediumGame();
        }
        else if(difficulty=="hard"){
            return new hardGame();
        }
        return null;
    }
    public boolean isGameOver(){
        return gameOverFlag;
    }
    public  int getScore(){
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public BaseGame() {
        heroAircraft = HeroAircraft.getInstance (
                Main.WINDOW_WIDTH / 2,
                Main.WINDOW_HEIGHT - ImageManager.HERO_IMAGE.getHeight() ,
                0, 0, 300);
        heroAircraft.setStrategy(new StraightShoot());

        enemyAircrafts = new LinkedList<>();
        heroBullets = new LinkedList<>();
        enemyBullets = new LinkedList<>();
        props =new LinkedList<>();




        /**
         * Scheduled 线程池，用于定时任务调度
         * 关于alibaba code guide：可命名的 ThreadFactory 一般需要第三方包
         * apache 第三方库： org.apache.commons.lang3.concurrent.BasicThreadFactory
         */
        this.executorService = new ScheduledThreadPoolExecutor(1,
                new BasicThreadFactory.Builder().namingPattern("game-action-%d").daemon(true).build());

        //启动英雄机鼠标监听
        new HeroController(this, heroAircraft);

    }

    public void produceEnemy(){
        if (enemyAircrafts.size() < enemyMaxNumber) {

            double m=Math.random();
            if(m<probability) {
                enemyFactory=new MobEnemyFactory();
                MobEnemy mobEnemy=(MobEnemy) enemyFactory.createEnemy(
                        (int) (Math.random() * (Main.WINDOW_WIDTH - ImageManager.MOB_ENEMY_IMAGE.getWidth())) * 1,
                        (int) (Math.random() * Main.WINDOW_HEIGHT * 0.2) * 1,
                        2,
                        4,
                        30
                );
                enemyAircrafts.add(mobEnemy);


            }
            else if(m>=probability){
                enemyFactory=new EliteEnemyFactory();
                EliteEnemy eliteEnemy=(EliteEnemy) enemyFactory.createEnemy(
                        (int) (Math.random() * (Main.WINDOW_WIDTH - ImageManager.ELITE_ENEMY_IMAGE.getWidth())) * 1,
                        (int) (Math.random() * Main.WINDOW_HEIGHT * 0.2) * 1,
                        2,
                        4,
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

    public void increase(){}



    /**
     * 游戏启动入口，执行游戏逻辑
     */
    public void action() {

        bgmThread.setSoundenabled(soundEnabled);
        bgmThread.setRecycle(true);
        bgmThread.setStop(false);
        bgmThread.start();

        // 定时任务：绘制、对象产生、碰撞判定、击毁及结束判定
            Runnable task = () -> {

            time += timeInterval;
            // 周期性执行（控制频率）
            if (timeCountAndNewCycleJudge()) {
//                System.out.println(time);

                // 新敌机产生
                produceEnemy();
                //增加倍率
                increase();
                // 飞机射出子弹
                shootAction();
            }

            // 子弹移动
            bulletsMoveAction();


            // 飞机移动
            aircraftsMoveAction();


            // 撞击检测
            crashCheckAction();


            //道具移动
            propsMoveAction();


            // 后处理
            postProcessAction();


            //每个时刻重绘界面
            repaint();

            // 游戏结束检查
            if (heroAircraft.getHp() <= 0) {
                // 游戏结束
                bgmThread.setStop(true);
                bgmThread.setRecycle(false);
                if(existflag){
                    bgmBossThread.setStop(true);
                    bgmBossThread.setRecycle(false);
                }
                gameOverThread.setSoundenabled(soundEnabled);
                gameOverThread.setRecycle(false);
                gameOverThread.setStop(false);
                gameOverThread.start();
                System.out.println("Game Over!");
                bgmThread.setStop(true);
                gameOverFlag = true;
                executorService.shutdown();
                synchronized (Main.MAIN_LOCK){
                        Main.MAIN_LOCK.notify();

                }


            }



        };

        /**
         * 以固定延迟时间进行执行
         * 本次任务执行完成后，需要延迟设定的延迟时间，才会执行新的任务
         */
        executorService.scheduleWithFixedDelay(task, timeInterval, timeInterval, TimeUnit.MILLISECONDS);

    }

    //***********************
    //      Action 各部分
    //***********************

    private boolean timeCountAndNewCycleJudge() {
        cycleTime += timeInterval;
        if (cycleTime >= cycleDuration && cycleTime - timeInterval < cycleTime) {
            // 跨越到新的周期
            cycleTime %= cycleDuration;
            return true;
        } else {
            return false;
        }
    }

    private void shootAction() {
        // TODO 敌机射击
        // 英雄射击
        //这里也需要加东西
        for(AbstractAircraft enemy:enemyAircrafts){


            if(enemy.getClass().getName()=="edu.hitsz.aircraft.BossEnemy"){
               enemy.setStrategy(new ScatterShoot());
            }
            else {
                enemy.setStrategy(new StraightShoot());

            }

            enemyBullets.addAll(enemy.executeShoot(enemy.getLocationX(),enemy.getLocationY(),3,
                    enemy.getSpeedY(),enemy.getShootNum(), enemy.getPower(), enemy.getDirection(),true));
        }

        heroBullets.addAll(heroAircraft.executeShoot(heroAircraft.getLocationX(),heroAircraft.getLocationY(),2,
                heroAircraft.getSpeedY(),heroAircraft.getShootNum(),heroAircraft.getPower(), heroAircraft.getDirection(),false));

    }

    private void bulletsMoveAction() {
        for (BaseBullet bullet : heroBullets) {
            bullet.forward();
        }
        for (BaseBullet bullet : enemyBullets) {
            bullet.forward();
        }

    }

    private void aircraftsMoveAction() {
        for (AbstractAircraft enemyAircraft : enemyAircrafts) {
            enemyAircraft.forward();
        }
    }


    /**
     * 碰撞检测：
     * 1. 敌机攻击英雄
     * 2. 英雄攻击/撞击敌机
     * 3. 英雄获得补给
     */
    private void crashCheckAction() {
        // TODO 敌机子弹攻击英雄
        for(BaseBullet bullet:enemyBullets){
            if(bullet.notValid()){
                continue;
            }
            if(heroAircraft.notValid()){
                continue;
            }
            if(heroAircraft.crash(bullet)){
                heroAircraft.decreaseHp(bullet.getPower());
                bullet.vanish();
            }
        }
        // 英雄子弹攻击敌机
        for (BaseBullet bullet : heroBullets) {
            if (bullet.notValid()) {

                continue;
            }
            for (AbstractAircraft enemyAircraft : enemyAircrafts) {
                if (enemyAircraft.notValid()) {
                    // 已被其他子弹击毁的敌机，不再检测
                    // 避免多个子弹重复击毁同一敌机的判定
                    if(enemyAircraft.getClass().getName()=="edu.hitsz.aircraft.BossEnemy"){
                        surrvial=false;
                        bgmBossThread.setStop(!surrvial);
                        existflag = false;
                    }
                    continue;
                }
                if (enemyAircraft.crash(bullet)) {
                    // 敌机撞击到英雄机子弹
                    // 敌机损失一定生命值

                    enemyAircraft.decreaseHp(bullet.getPower());
                    bullet.vanish();
                    bulletHitThread = new MusicThread("src/videos/bullet_hit.wav");
                    bulletHitThread.setRecycle(false);
                    bulletHitThread.setSoundenabled(soundEnabled);
                    bulletHitThread.setStop(false);
                    bulletHitThread.start();
                    if (enemyAircraft.notValid()) {
                        // TODO 获得分数，产生道具补给
                        if(enemyAircraft.getClass().getName()=="edu.hitsz.aircraft.BossEnemy"){
                            score+=30;
                        }
                        else{ score += 10;}

                        if(enemyAircraft.getClass().getName()=="edu.hitsz.aircraft.EliteEnemy"){
                            double t=Math.random();
                            if(t<0.2){
                                propFactory=new BloodFactory();
                                props.add(propFactory.createProp(
                                        (int) (enemyAircraft.getLocationX()) * 1,
                                        (int) (enemyAircraft.getLocationY()) * 1,
                                        0,
                                        6
                                ));
                            }
                            else if(t>=0.2&&t<0.3){
                                propFactory=new BombFactory();
                                props.add(propFactory.createProp(
                                        (int) (enemyAircraft.getLocationX()) * 1,
                                        (int) (enemyAircraft.getLocationY()) * 1,
                                        0,
                                        6

                                ));
                            }
                            else if(t>=0.3&&t<0.55){
                                propFactory=new BulletFactory();
                                props.add(propFactory.createProp(
                                        (int) (enemyAircraft.getLocationX()) * 1,
                                        (int) (enemyAircraft.getLocationY()) * 1,
                                        0,
                                        6

                                ));
                            }
                            else if(t>=0.55&&t<0.65&&hasFroze){
                                propFactory=new FrozeFactory();
                                props.add(propFactory.createProp(
                                        (int) (enemyAircraft.getLocationX()) * 1,
                                        (int) (enemyAircraft.getLocationY()) * 1,
                                        0,
                                        6
                                ));
                            }
                            else if(t>=0.65&&t<0.8&&hasDamage){
                                propFactory=new DamageFactory();
                                props.add(propFactory.createProp(
                                        (int) (enemyAircraft.getLocationX()) * 1,
                                        (int) (enemyAircraft.getLocationY()) * 1,
                                        0,
                                        6
                                ));
                            }
                            else if(t>=0.8&&t<0.9){
                                propFactory=new LiftFactory();
                                props.add(propFactory.createProp(
                                        (int) (enemyAircraft.getLocationX()) * 1,
                                        (int) (enemyAircraft.getLocationY()) * 1,
                                        0,
                                        6
                                ));
                            }


                        }

                    }
                }

                // 英雄机 与 敌机 相撞，均损毁
                if (enemyAircraft.crash(heroAircraft) || heroAircraft.crash(enemyAircraft)) {
                    enemyAircraft.vanish();
                    heroAircraft.decreaseHp(Integer.MAX_VALUE);
                    heroAircraft.vanish();
                }
            }
        }

        // Todo: 我方获得道具，道具生效
        for(AbstractProp prop:props){
            if(prop.notValid()){
                continue;
            }
            if(heroAircraft.notValid()){
                continue;
            }
            if(heroAircraft.crash(prop)){
                getSupplyThread=new MusicThread("src/videos/get_supply.wav");
                getSupplyThread.setRecycle(false);
                getSupplyThread.setStop(false);
                getSupplyThread.setSoundenabled(soundEnabled);
                getSupplyThread.start();


                if(prop.getClass().getName()=="edu.hitsz.prop.Bomb"){
                    Bomb bomb=(Bomb)prop;
                    for(BaseBullet bullet:enemyBullets){
                        if(bullet.notValid()){
                            continue;
                        }
                        else{
                            bomb.addSubscriber(bullet);
                        }
                    }
                    for(AbstractAircraft enemy:enemyAircrafts){
                        if(enemy.notValid()){

                            if (enemy.getLocationY()>= Main.WINDOW_HEIGHT ) {
                                score+=10;
                            }
                            continue;
                        }
                        else if(enemy.getClass().getName()=="edu.hitsz.aircraft.BossEnemy"){
                            continue;
                        }
                        else{
                            bomb.addSubscriber(enemy);
                        }
                    }

                    bombExplosionThread=new MusicThread("src/videos/bomb_explosion.wav");
                    bombExplosionThread.setRecycle(false);
                    bombExplosionThread.setStop(false);
                    bombExplosionThread.setSoundenabled(soundEnabled);
                    bombExplosionThread.start();
                }


                else if(prop.getClass().getName()=="edu.hitsz.prop.Froze"){
                    Froze froze = (Froze) prop;
                    for(AbstractAircraft enemy:enemyAircrafts){
                        if(enemy.notValid()){
                            continue;
                        }
                        else if(enemy.getClass().getName()=="edu.hitsz.aircraft.BossEnemy"){
                            continue;
                        }
                        else{
                            froze.addSubscriber1(enemy);
                        }
                    }
                }

                else if(prop.getClass().getName()=="edu.hitsz.prop.Damage"){
                    Damage damage = (Damage)  prop;
                    System.out.println("当前英雄机的火力伤害为"+heroAircraft.getPower());
                }

                prop.active(heroAircraft);
                for(AbstractAircraft enemy:enemyAircrafts) {
                    if (enemy.notValid()) {
                        if (enemy.getLocationY() <= Main.WINDOW_HEIGHT) {
                            score += 10;
                        }
                    }
                }
                prop.vanish();
            }

        }

    }
    private void propsMoveAction(){
        for (AbstractProp prop : props) {
            prop.forward();
        }
    }

    /**
     * 后处理：
     * 1. 删除无效的子弹
     * 2. 删除无效的敌机
     * 3. 检查英雄机生存
     * <p>
     * 无效的原因可能是撞击或者飞出边界
     */
    private void postProcessAction() {
        enemyBullets.removeIf(AbstractFlyingObject::notValid);
        heroBullets.removeIf(AbstractFlyingObject::notValid);
        enemyAircrafts.removeIf(AbstractFlyingObject::notValid);
        props.removeIf(AbstractFlyingObject::notValid);
    }


    //***********************
    //      Paint 各部分
    //***********************

    /**
     * 重写paint方法
     * 通过重复调用paint方法，实现游戏动画
     *
     * @param  g
     */
    @Override
    public void paint(Graphics g) {
        super.paint(g);

        // 绘制背景,图片滚动
        g.drawImage(BACKGROUND_IMAGE, 0, this.backGroundTop - Main.WINDOW_HEIGHT, null);
        g.drawImage(BACKGROUND_IMAGE, 0, this.backGroundTop, null);
        this.backGroundTop += 1;
        if (this.backGroundTop == Main.WINDOW_HEIGHT) {
            this.backGroundTop = 0;
        }

        // 先绘制子弹，后绘制飞机
        // 这样子弹显示在飞机的下层
        paintImageWithPositionRevised(g, enemyBullets);
        paintImageWithPositionRevised(g, heroBullets);

        paintImageWithPositionRevised(g, enemyAircrafts);
        paintImageWithPositionRevised(g, props);

        g.drawImage(ImageManager.HERO_IMAGE, heroAircraft.getLocationX() - ImageManager.HERO_IMAGE.getWidth() / 2,
                heroAircraft.getLocationY() - ImageManager.HERO_IMAGE.getHeight() / 2, null);

        //绘制得分和生命值
        paintScoreAndLife(g);

    }

    private void paintImageWithPositionRevised(Graphics g, List<? extends AbstractFlyingObject> objects) {
        if (objects.size() == 0) {
            return;
        }

        for (AbstractFlyingObject object : objects) {
            BufferedImage image = object.getImage();
            assert image != null : objects.getClass().getName() + " has no image! ";
            g.drawImage(image, object.getLocationX() - image.getWidth() / 2,
                    object.getLocationY() - image.getHeight() / 2, null);
        }
    }

    private void paintScoreAndLife(Graphics g) {
        int x = 10;
        int y = 25;
        g.setColor(new Color(16711680));
        g.setFont(new Font("SansSerif", Font.BOLD, 22));
        g.drawString("SCORE:" + this.score, x, y);
        y = y + 20;
        g.drawString("LIFE:" + this.heroAircraft.getHp()+"/"+this.heroAircraft.getMaxHp(), x, y);
    }
}
