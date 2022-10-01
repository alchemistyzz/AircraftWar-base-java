//package edu.hitsz.aircraft;
//
//import edu.hitsz.bullet.EnemyBullet;
//import org.junit.jupiter.api.AfterEach;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import edu.hitsz.bullet.BaseBullet;
//
//import java.util.LinkedList;
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//class EliteEnemyTest {
//
//    private  EliteEnemy eliteEnemy;
//    private  List<BaseBullet> enemyBullets;
//    @BeforeEach
//    void setUp() {
//        EliteEnemy eliteEnemy = new EliteEnemy(100,100,10,10,30);
//    }
//
//    @AfterEach
//    void tearDown() {
//        eliteEnemy = null;
//
//    }
//
//    @Test
//    void forward() {
//        EliteEnemy eliteEnemy = new EliteEnemy(100,100,10,10,30);
//        eliteEnemy.forward();
//        assertEquals(110,eliteEnemy.getLocationX());
//        assertEquals(10,eliteEnemy.getSpeedX());
//
//
//        eliteEnemy.setLocation(0,10);
//        eliteEnemy.setSpeed(-10,10);
//        eliteEnemy.forward();
//        assertEquals(-10,eliteEnemy.getLocationX());
//        assertEquals(10,eliteEnemy.getSpeedX());
//
//        eliteEnemy.setLocation(512,10);
//        eliteEnemy.setSpeed(10,10);
//        eliteEnemy.forward();
//        assertEquals(522,eliteEnemy.getLocationX());
//        assertEquals(-10,eliteEnemy.getSpeedX());
//
//        eliteEnemy.setLocation(10,0);
//        eliteEnemy.setSpeed(10,-10);
//        eliteEnemy.forward();
//        assertEquals(-10,eliteEnemy.getLocationY());
//        assertEquals(-10,eliteEnemy.getSpeedY());
//
//        eliteEnemy.setLocation(10,768);
//        eliteEnemy.setSpeed(10,10);
//        eliteEnemy.forward();
//        assertTrue(eliteEnemy.notValid());
//
//    }
//
//    @Test
//    void getLocationX() {
//        eliteEnemy = new EliteEnemy(10,20,10,10,100);
//        assertEquals(10,eliteEnemy.getLocationX());
//    }
//
//    @Test
//    void getLocationY() {
//        eliteEnemy = new EliteEnemy(10,20,10,10,100);
//        assertEquals(20,eliteEnemy.getLocationY());
//    }
//
//    @Test
//    void shoot() {
//        eliteEnemy =new EliteEnemy(10,10,10,10,100);
//        enemyBullets = new LinkedList<>();
//        enemyBullets.addAll(eliteEnemy.shoot());
//        assertNotNull(enemyBullets);
//        assertEquals(1,enemyBullets.size());
//
//        for(int i=0;i<enemyBullets.size();i++) {
//            assertEquals(eliteEnemy.getLocationX() + (i * 2) * 10, enemyBullets.get(i).getLocationX());
//            assertEquals(eliteEnemy.getLocationY() + 1 * 2, enemyBullets.get(i).getLocationY());
//            assertEquals(0, enemyBullets.get(i).getSpeedX());
//            assertEquals(eliteEnemy.getSpeedY() + 1 * 5, enemyBullets.get(i).getSpeedY());
//            assertEquals(30, enemyBullets.get(i).getPower());
//        }
//
//    }
//}