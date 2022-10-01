package edu.hitsz.swing;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import edu.hitsz.application.Main;
import edu.hitsz.dao.*;

public class ScoreBoard {
    private JPanel mainPanel;
    private JButton deleteButton;
    private JTable scoreTable;
    private JScrollPane tableScrollPanel;
    private JTextField core;
    private JTextField head;
    private List<Record> records=new ArrayList<Record>();
    private String difficluty;
    private DefaultTableModel model;
    private DataDao dataDao;

    public JTable getScoreTable() {
        return scoreTable;
    }

    public void setDifficluty(String difficluty) {
        this.difficluty = difficluty;
    }

    public void update(String difficulty){
        String[] columnNames = {"排名", "玩家姓名", "分数", "时间"};
        // 表格所有行数据
        Object[][] rowData = new Object[1000][4];
        dataDao = new RecordDaoImpl("src/edu/hitsz/dao/"+difficulty+"_board.txt");
        dataDao.update();
        records = dataDao.getAllRecords();
        System.out.println(records.size());
        for(int i=0;i<records.size();i++) {
            Record record= records.get(i);
            int j=0;
            rowData[i][j++] = record.getOrder();
            rowData[i][j++] = record.getPlayerName();
            rowData[i][j++] = record.getScore();
            rowData[i][j++] = record.getTime();
        }

       model = new DefaultTableModel(rowData, columnNames) {
            @Override
            public boolean isCellEditable(int row, int col) {
                return false;
            }

        };
        scoreTable.setModel(model);
        tableScrollPanel.setViewportView(scoreTable);



    }

    public void doAdd(String name,int score1){
        Record record=new Record(name,score1);
        dataDao.doAdd(record);
    }

    public void removeROW(int row){
        model.removeRow(row);
        dataDao.doDelete(row+1);
    }

    public ScoreBoard(String difficulty) {
         update(difficulty);
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Dialog2 dialog2=new Dialog2(difficulty);
                dialog2.setSize(250,150);
                Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
                dialog2.setBounds(((int) screenSize.getWidth() - Main.WINDOW_WIDTH) / 2+100, ((int) screenSize.getHeight() -Main.WINDOW_HEIGHT) / 2,
                        Main.WINDOW_WIDTH/2, Main.WINDOW_HEIGHT/3);
                dialog2.setContentPane(dialog2.getMainPanel());
                dialog2.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
                dialog2.setVisible(true);

            }
        });

        core.setText("难度： "+difficulty);
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("panel2");
        frame.setContentPane(new ScoreBoard("easy").mainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }




}

