package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

public class ProfileTest {

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();

    Profile profile;
    Profile profile2;
    HealthEssentials he;



    @BeforeEach
    public void Setup() {
        profile = new Profile("John", "Colins", true,33,189,150);
        profile2 = new Profile("John", "Colins", true,33,189,175);
        profile.addEssentials(new HealthEssentials(3,3,3,3,3,3));
        he = new HealthEssentials(3,3,3,3,3,3);
        System.setOut(new PrintStream(outContent));
        System.setErr(new PrintStream(errContent));

    }

    @Test
    public void constructorTest () {
        assertEquals("John",profile.getFirstName());
        assertEquals("Colins",profile.getLastName());
        assertTrue(profile.getGender());
        assertEquals(33,profile.getAge());
        assertEquals(189,profile.getHeight());
        assertEquals(150,profile.getWeight());

    }

    @Test
    public void SettersTest() {
        profile.setAge(20);
        assertEquals(20,profile.getAge());
        profile.setWeight(40);
        assertEquals(40,profile.getWeight());
        profile.setFirstName("aaa");
        assertEquals("aaa",profile.getFirstName());
        profile.setLastName("sss");
        assertEquals("sss",profile.getLastName());
        profile.setHeight(200);
        assertEquals(200,profile.getHeight());
        profile.setGender(false);
        assertFalse(profile.getGender());
    }


    @Test
    public void findBiggestTest() {
        assertEquals(1,profile.findFirstBiggerThanTwo(3,2,3,4,5,6));
        assertEquals(2,profile.findFirstBiggerThanTwo(1,3,3,4,5,6));
        assertEquals(3,profile.findFirstBiggerThanTwo(1,2,3,4,5,6));
        assertEquals(4,profile.findFirstBiggerThanTwo(1,2,2,4,5,6));
        assertEquals(5,profile.findFirstBiggerThanTwo(1,2,2,2,5,6));
        assertEquals(6,profile.findFirstBiggerThanTwo(1,2,2,2,2,6));
    }

    @Test
    public void makeNewEssentialsTest() {

        profile.makeNewEssentials(1);
        he.setCalcium(2);
        assertTrue(he.equals(profile.getEssentials().get(1)));
        assertEquals(2,profile.getEssentials().size());

        profile.makeNewEssentials(2);
        he.setIron(2);
        assertTrue(he.equals(profile.getEssentials().get(2)));
        assertEquals(3,profile.getEssentials().size());

        profile.makeNewEssentials(3);
        he.setZinc(2);
        assertTrue(he.equals(profile.getEssentials().get(3)));
        assertEquals(4,profile.getEssentials().size());

        profile.makeNewEssentials(4);
        he.setVitaminB(2);
        assertTrue(he.equals(profile.getEssentials().get(4)));
        assertEquals(5,profile.getEssentials().size());

        profile.makeNewEssentials(5);
        he.setVitaminC(2);
        assertTrue(he.equals(profile.getEssentials().get(5)));
        assertEquals(6,profile.getEssentials().size());

        profile.makeNewEssentials(6);
        he.setSugar(2);
        assertTrue(he.equals(profile.getEssentials().get(6)));
        assertEquals(7,profile.getEssentials().size());
    }

    @Test
    public void addSameEssentialTest() {
        profile.addEssentials(he);
        profile.addSameEssentials();
        assertTrue(he.equals(profile.getEssentials().get(1)));
    }

    @Test
    public void calculateProfileAverageTest() {

        List<Double> ls = new ArrayList<>();
        ls.add(2.0);
        assertEquals(ls,profile.calculateProfileAverage());

    }
}
