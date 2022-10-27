package persistence;

import model.DataCollectionAndProcess;
import model.OneDaySleep;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**********
 * Citation URL: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
 * cite the JsonReaderTest class written by CPSC 210 instructor
 **********/
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
            assertEquals("My sleep list", sl.getName());
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
            assertEquals("My sleep list", sl.getName());
            List<OneDaySleep> sleepLists = sl.getSleepDays();
            assertEquals(2, sleepLists.size());
            checkOneDaySleep(9, 24, 8, 2, sleepLists.get(0));
            checkOneDaySleep(9, 25,5,1, sleepLists.get(1));
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }
}
