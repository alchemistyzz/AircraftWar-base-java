package edu.hitsz.swing;

import edu.hitsz.application.Main;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuBoard {
    private JPanel mainPanel;
    private JButton easyButton;
    private JButton mediumButton;
    private JButton hardButton;
    private JComboBox soundComboBox;
    private JTextField TextField;
    private String difficulty="easy";
    private boolean soundEnabled=true;


    public MenuBoard() {
        easyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                difficulty="easy";
                mainPanel.setVisible(false);
                synchronized (Main.MAIN_LOCK){
                    Main.MAIN_LOCK.notify();
                }
            }
        });

        mediumButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                difficulty="medium";
                mainPanel.setVisible(false);
                synchronized (Main.MAIN_LOCK) {
                    Main.MAIN_LOCK.notify();
                }
            }
        });

        hardButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                difficulty="hard";
                mainPanel.setVisible(false);
                synchronized (Main.MAIN_LOCK) {
                    Main.MAIN_LOCK.notify();
                }
            }
        });
        soundComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selecteditem = soundComboBox.getSelectedItem().toString();
                soundEnabled = selecteditem.equals("关")?false:true;
            }
        });
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public boolean isSoundEnabled() {
        return soundEnabled;
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("mainpanel");
        frame.setContentPane(new MenuBoard().mainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
