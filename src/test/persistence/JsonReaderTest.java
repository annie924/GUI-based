package persistence;

import model.DataCollectionAndProcess;
import model.OneDaySleep;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class JsonReaderTest extends JsonTest{
    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            DataCollectionAndProcess sl = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderEmptySleepList() {
        JsonReader reader = new JsonReader("./data/testReaderEmptySleepList.json");
        try {
            DataCollectionAndProcess sl = reader.read();
            assertEquals("My Sleep List", sl.getName());
            assertEquals(0, sl.numSleepDays());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralWorkRoom() {
        JsonReader reader = new JsonReader("./data/testReaderGeneralSleepList.json");
        try {
            DataCollectionAndProcess sl = reader.read();
            assertEquals("My Sleep List", sl.getName());
            List<OneDaySleep> sleepLists = sl.getSleepDays();
            assertEquals(2, sleepLists.size());
            checkOneDaySleep(9, 24, 8, 2, sleepLists.get(0));
            checkOneDaySleep(9, 25,5,1, sleepLists.get(1));
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }
}
