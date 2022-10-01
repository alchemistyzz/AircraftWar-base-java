package edu.hitsz.application;

import edu.hitsz.swing.MenuBoard;
import edu.hitsz.swing.*;
import javax.swing.*;
import java.awt.*;

/**
 * 程序入口
 * @author hitsz
 */
public class Main {

    public static final int WINDOW_WIDTH = 512;
    public static final int WINDOW_HEIGHT = 768;
    public static final Object MAIN_LOCK=new Object();
    public  static ScoreBoard scoreBoard;

    public static void main(String[] args) {

        System.out.println("Hello Aircraft War");

        // 获得屏幕的分辨率，初始化 Frame
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        JFrame frame = new JFrame("Aircraft War");
        frame.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        frame.setResizable(false);
        //设置窗口的大小和位置,居中放置
        frame.setBounds(((int) screenSize.getWidth() - WINDOW_WIDTH) / 2, 0,
                WINDOW_WIDTH, WINDOW_HEIGHT);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //初始界面
        //选择难度
        MenuBoard menuBoard =new MenuBoard();
        JPanel menuPanel = menuBoard.getMainPanel();
        frame.setContentPane(menuPanel);
        frame.setVisible(true);

        synchronized (MAIN_LOCK){
            while(menuPanel.isVisible()){
                //主线程等待菜单面板关闭
                try {
                    MAIN_LOCK.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        //移除难度选择菜单panel，后面加入正常的gamepanel,实现界面切换
        frame.remove(menuPanel);

        //获取游戏难度和音效设置
        String difficulty = menuBoard.getDifficulty();
        boolean soundEnabled = menuBoard.isSoundEnabled();

        BaseGame gamePanel = BaseGame.getGameOf(difficulty);
        gamePanel.setSoundEnabled(soundEnabled);

        frame.setContentPane(gamePanel);
        frame.setVisible(true);
        gamePanel.action();

        //等待游戏结束，更换界面为分数榜
        synchronized (MAIN_LOCK){
            while (!gamePanel.isGameOver()){
                try{
                    MAIN_LOCK.wait();
                }
                catch (InterruptedException e){
                    e.printStackTrace();
                }
            }
        }
        boolean x= gamePanel.isGameOver();
        System.out.println(x);
        int score = gamePanel.getScore();
        frame.remove(gamePanel);

//        排行榜界面
//        记录分数，游戏结束


        scoreBoard =new ScoreBoard(difficulty);
        frame.setContentPane(scoreBoard.getMainPanel());
        frame.setVisible(true);
        Dialog1 dialog1 = new Dialog1(score,difficulty);
        dialog1.setSize(250,150);
        dialog1.setBounds(((int) screenSize.getWidth() - WINDOW_WIDTH) / 2+100, ((int) screenSize.getHeight() - WINDOW_HEIGHT) / 2,
                WINDOW_WIDTH/2, WINDOW_HEIGHT/3);
        dialog1.setContentPane(dialog1.getMainPanel());
//        dialog1.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
        dialog1.setVisible(true);


    }
}
