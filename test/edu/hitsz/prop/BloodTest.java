//package edu.hitsz.prop;
//
//import org.junit.jupiter.api.AfterEach;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//class BloodTest {
//    private AbstractProp blood;
//
//    @BeforeEach
//    void setUp() {
//       AbstractProp blood = new AbstractProp(100,80,0,10);
//    }
//
//    @AfterEach
//    void tearDown() {
//        blood = null;
//    }
//
//    @Test
//    void getLocationX() {
//        AbstractProp blood = new AbstractProp(100,80,0,10);
//        assertEquals(100,blood.getLocationX());
//    }
//
//    @Test
//    void getLocationY() {
//        AbstractProp blood = new AbstractProp(100,80,0,10);
//        assertEquals(80,blood.getLocationY());
//    }
//
//    @Test
//    void notValid() {
//        AbstractProp blood = new AbstractProp(100,80,0,10);
//        assertFalse(blood.notValid());
//        blood.vanish();
//        assertTrue(blood.notValid());
//
//    }
//}