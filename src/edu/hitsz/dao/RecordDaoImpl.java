package edu.hitsz.dao;

import java.io.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Comparator;

public class RecordDaoImpl implements DataDao{
    private List<Record> records=new ArrayList<Record>();
    private String pathname;
    private File file;

    public RecordDaoImpl(String pathname){
        this.pathname=pathname;
         file =new File(pathname);
        try {
            readFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public List<Record> getAllRecords() {
        return records;
    }

    @Override
    public void findRecord(String playerName) {
        for(Record record:records){
            if(record.getPlayerName()==playerName){
                System.out.println("第"+record.getOrder()+"名"+" "+record.getPlayerName()+" "
                        + record.getScore()+" "+record.getTime());
                return;
            }
        }
        System.out.println("can not find this record!");
    }

    @Override
    public void doDelete(int order) {
        for(Record record:records){
            if(record.getOrder()==order){
                records.remove(record);
                break;
            }
        }
        update();
        try {
            writeFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("can not find this record!");
    }

    @Override
    public void doAdd(Record record) {
        records.add(record);
        update();
        try {
            writeFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void display() {
        for(Record record:records){
            System.out.println("第"+record.getOrder()+"名"+" "+record.getPlayerName()+" "
                        + record.getScore()+" "+record.getTime());
            }
    }


    @Override
    public void update(){

        Collections.sort(records, new Comparator<Record>() {
            @Override
            public int compare(Record record1, Record record2) {
                return  record1.getScore()>record2.getScore()? -1:(record1.getScore()==record2.getScore()? 0:1);
            }
        });
        for(Record record1:records) {
            record1.setOrder(records.indexOf(record1)+1);
        }

        try {
            this.writeFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void writeFile() throws IOException{
        FileOutputStream  fw=new FileOutputStream(file);
        OutputStreamWriter ow=new  OutputStreamWriter(fw);
        for(Record record:records){
            ow.append(record.getOrder()+" "+"\n");
            ow.append(record.getPlayerName()+" "+"\n");
            ow.append(record.getScore()+" "+"\n");
            ow.append(record.getTime()+"\n");
        }
        ow.close();
        fw.close();

    }

    public void readFile() throws IOException {
        file.createNewFile();
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(file));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        String str = "";
        while ((str = br.readLine()) != null) {
            Record record = new Record("0", 0);
            record.setOrder(Integer.parseInt(str.trim()));
            record.setPlayerName(br.readLine().trim());
            record.setScore(Integer.parseInt(br.readLine().trim()));
            record.setTime(br.readLine().trim());
            records.add(record);
        }
    }


}
