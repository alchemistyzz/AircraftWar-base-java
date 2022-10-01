//package edu.hitsz.aircraft;
//
//import edu.hitsz.bullet.EnemyBullet;
//import edu.hitsz.prop.AbstractProp;
//import edu.hitsz.prop.Blood;
//
//import org.junit.jupiter.api.AfterEach;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//
//
//
//import static org.junit.jupiter.api.Assertions.*;
//
//class HeroAircraftTest {
//    private HeroAircraft heroAircraft;
//
//
//    @BeforeEach
//    void setUp() {
//        heroAircraft = HeroAircraft.getInstance(100,100,0,0,100);
//    }
//
//    @AfterEach
//    void tearDown() {
//        heroAircraft = null;
//    }
//
//    @Test
//    void decreaseHp() {
//        int preHp = heroAircraft.getHp();
//        int decrease1 = 100;
//        heroAircraft.decreaseHp(decrease1);
//        assertEquals(preHp-decrease1,heroAircraft.getHp());
//        int decrease2 = 110;
//        heroAircraft.setHp();
//        heroAircraft.decreaseHp(decrease2);
//        assertEquals(0,heroAircraft.getHp());
//
//    }
//
//    @Test
//    void crash() {
//        MobEnemy mobEnemy = new MobEnemy(90,90,0,10,30);
//        EnemyBullet enemyBullet = new EnemyBullet(20,20,0,10,30);
//        AbstractProp blood =new Blood(100,100,20,10);
//        assertTrue(heroAircraft.crash(mobEnemy));
//        assertFalse(heroAircraft.crash(enemyBullet));
//        assertTrue(heroAircraft.crash(blood));
//
//    }
//
//    @Test
//    void vanish() {
//        heroAircraft.vanish();
//        assertTrue(heroAircraft.notValid());
//    }
//}