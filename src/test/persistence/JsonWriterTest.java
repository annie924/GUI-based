package persistence;

import model.DataCollectionAndProcess;
import model.OneDaySleep;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class JsonWriterTest extends JsonTest {
    @Test
    void testWriterInvalidFile() {
        try {
            DataCollectionAndProcess sl = new DataCollectionAndProcess("My sleep list");
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptySleepList() {
        try {
            DataCollectionAndProcess sl = new DataCollectionAndProcess("My sleep list");
            JsonWriter writer = new JsonWriter("./data/testWriterEmptySleepList.json");
            writer.open();
            writer.write(sl);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptySleepList.json");
            sl = reader.read();
            assertEquals("My sleep list", sl.getName());
            assertEquals(0, sl.numSleepDays());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneraSleepList() {
        try {
            DataCollectionAndProcess sl = new DataCollectionAndProcess("My sleep list");
            sl.addOneDaySleep(new OneDaySleep(9,24,8,2));
            sl.addOneDaySleep(new OneDaySleep(9,25,5,1));
            JsonWriter writer = new JsonWriter("./data/testWriterGeneralSleepList.json");
            writer.open();
            writer.write(sl);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralSleepList.json");
            sl = reader.read();
            assertEquals("My sleep list", sl.getName());
            List<OneDaySleep> sleepLists = sl.getSleepDays();
            assertEquals(2, sleepLists.size());
            checkOneDaySleep(9,24,8,2, sleepLists.get(0));
            checkOneDaySleep(9,25,5,1, sleepLists.get(1));

        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}
