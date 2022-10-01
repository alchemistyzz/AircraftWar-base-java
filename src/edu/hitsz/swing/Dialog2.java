package edu.hitsz.swing;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

import edu.hitsz.application.Main;
import edu.hitsz.dao.DataDao;
import edu.hitsz.dao.Record;
import edu.hitsz.dao.RecordDaoImpl;
import edu.hitsz.swing.ScoreBoard;

public class Dialog2 extends JDialog {
    private JPanel JPanel;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextField TextField;
    private JButton buttonReset;
    private String difficulty;

    public Dialog2(String difficulty) {
        this.difficulty=difficulty;
        setContentPane(JPanel);
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
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // 遇到 ESCAPE 时调用 onCancel()
        JPanel.registerKeyboardAction(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);



        buttonReset.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {onCancel();}
        });
    }

    public JPanel getMainPanel(){
        return JPanel;
    }

    private void onOK() {
        // 在此处添加您的代码
        int row =Main.scoreBoard.getScoreTable().getSelectedRow();
        System.out.println(row);
        if(row != -1){
            Main.scoreBoard.removeROW(row);
        }
        Main.scoreBoard.update(difficulty);
        dispose();
    }

    private void onCancel() {
        // 必要时在此处添加您的代码
        dispose();
    }

    public static void main(String[] args) {
        Dialog2 dialog = new Dialog2("easy");
        dialog.pack();
        dialog.setVisible(true);
        System.exit(0);
    }
}
