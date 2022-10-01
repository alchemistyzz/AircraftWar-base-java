package edu.hitsz.swing;

import edu.hitsz.application.BaseGame;
import edu.hitsz.application.Main;
import edu.hitsz.dao.DataDao;
import edu.hitsz.dao.Record;
import edu.hitsz.dao.RecordDaoImpl;


import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Dialog1 extends JDialog {
    private JPanel Input;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextField textField2;
    private JTextArea textArea1;
    private int score1;
    private String difficulty;


    public JPanel getMainPanel(){
        return Input;
    }


    public Dialog1(int score,String difficulty) {
        this.difficulty=difficulty;
        score1=score;
        textArea1.setText("游戏结束，你的得分为  "+score1+"\n"+" 请输入名字记录得分：");
        setContentPane(Input);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        buttonOK.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                onOK();
            }
        });

        buttonCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });

        // 点击 X 时调用 onCancel()
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // 遇到 ESCAPE 时调用 onCancel()
        Input.registerKeyboardAction(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }

    private void onOK() {
        // 在此处添加您的代码
        Main.scoreBoard.doAdd(textField2.getText(), score1);
        Main.scoreBoard.update(difficulty);
        System.out.println(textField2.getText()+ " "+score1);
        dispose();
    }

    private void onCancel() {
        // 必要时在此处添加您的代码
        dispose();
    }

    public static void main(String[] args) {
        Dialog1 dialog = new Dialog1(60,"easy");
        dialog.pack();
        dialog.setVisible(true);
        System.exit(0);
    }
}
