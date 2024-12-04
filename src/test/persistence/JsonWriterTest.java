package persistence;

import model.HealthEssentials;
import model.Profile;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class JsonWriterTest extends JsonTest {

    @Test
    void testWriterInvalidFile() {
        try {
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptyProfile() {
        try {
            Profile pr = new Profile("arad","parshad",true,21,181,180);
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyWorkroom.json");
            writer.open();
            writer.write(pr);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyWorkroom.json");
            pr = reader.read();
            assertEquals("arad", pr.getFirstName());
            assertEquals("parshad", pr.getLastName());
            assertEquals(181, pr.getHeight());
            assertEquals(180, pr.getWeight());
            assertEquals(21, pr.getAge());
            assertTrue(pr.getGender());
            assertEquals(0, pr.getEssentials().size());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralProfile() {
        try {
            Profile pr = new Profile("arad","parshad",true,21,181,180);
            pr.addEssentials(new HealthEssentials(2,2,2,2,2,2));
            pr.addEssentials(new HealthEssentials(2,2,2,2,2,2));
            JsonWriter writer = new JsonWriter("./data/testWriterGeneralProfile.json");
            writer.open();
            writer.write(pr);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralProfile.json");
            pr = reader.read();
            assertEquals("arad", pr.getFirstName());
            assertEquals("parshad", pr.getLastName());
            assertEquals(181, pr.getHeight());
            assertEquals(180, pr.getWeight());
            assertEquals(21, pr.getAge());
            assertTrue(pr.getGender());
            List<HealthEssentials> he = pr.getEssentials();
            assertEquals(2, he.size());
            checkHealthEssential(2,2,2,2,2,2, he.get(0));
            checkHealthEssential(2,2,2,2,2,2, he.get(1));

        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}
