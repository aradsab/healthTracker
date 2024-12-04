package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class HealthEssentialTest {

    HealthEssentials he;


    @BeforeEach
    public void Setup() {
        he = new HealthEssentials(3,3,3,3,3,3);
    }


}
