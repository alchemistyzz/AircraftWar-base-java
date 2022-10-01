package edu.hitsz.dao;
import java.io.*;
import java.util.List;

public interface DataDao {
    void findRecord(String playerName);

    List<Record> getAllRecords();

    void doAdd(Record record);

    void doDelete(int order);

    void update();

    void display();
}
