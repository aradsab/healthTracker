package persistence;

import model.HealthEssentials;
import model.Profile;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class JsonTest {
    protected void checkHealthEssential(int calcium,int iron,int zinc, int vitaminC, int vitaminB,int sugar
            , HealthEssentials he) {
        assertEquals(iron, he.getIron());
        assertEquals(calcium, he.getCalcium());
        assertEquals(sugar,he.getSugar());
        assertEquals(vitaminB, he.getVitaminB());
        assertEquals(zinc, he.getZinc());
        assertEquals(vitaminC, he.getVitaminC());
    }
}