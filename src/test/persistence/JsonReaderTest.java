package persistence;

import model.HealthEssentials;
import model.Profile;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class JsonReaderTest extends JsonTest {

    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            Profile pr = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderEmptyProfile() {
        JsonReader reader = new JsonReader("./data/testReaderEmptyProfile.json");
        try {
            Profile pr = reader.read();
            assertEquals("arad", pr.getFirstName());
            assertEquals("parshad", pr.getLastName());
            assertEquals(181, pr.getHeight());
            assertEquals(180, pr.getWeight());
            assertEquals(21, pr.getAge());
            assertTrue(pr.getGender());
            assertEquals(0, pr.getEssentials().size());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralWorkRoom() {
        JsonReader reader = new JsonReader("./data/testReaderGeneralProfile.json");
        try {
            Profile pr = reader.read();
            assertEquals("arad", pr.getFirstName());
            assertEquals("parshad", pr.getLastName());
            assertEquals(21, pr.getAge());
            assertTrue(pr.getGender());
            List<HealthEssentials> he = pr.getEssentials();
            assertEquals(2, he.size());
            checkHealthEssential(2,2,2,2,2,2,he.get(0));
            checkHealthEssential(3,3,3,3,3,3,he.get(1));
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }
}
