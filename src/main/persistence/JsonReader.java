package persistence;

import model.DataCollectionAndProcess;
import model.OneDaySleep;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

// Represents a reader that reads SleepList from JSON data stored in file
public class JsonReader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads SleepList from file and returns it;
    // throws IOException if an error occurs reading data from file
    public DataCollectionAndProcess read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseSleepList(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses sleepList from JSON object and returns it
    private DataCollectionAndProcess parseSleepList(JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        DataCollectionAndProcess sl = new DataCollectionAndProcess(name);
        addSleepLists(sl, jsonObject);
        return sl;
    }

    // MODIFIES: sl
    // EFFECTS: parses thingies from JSON object and adds them to workroom
    private void addSleepLists(DataCollectionAndProcess sl, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("sleep data");
        for (Object json : jsonArray) {
            JSONObject nextOneDaySleep = (JSONObject) json;
            addSleepList(sl, nextOneDaySleep);
        }
    }

    // MODIFIES: sl
    // EFFECTS: parses thingy from JSON object and adds it to workroom
    private void addSleepList(DataCollectionAndProcess sl, JSONObject jsonObject) {
        int month = jsonObject.getInt("month");
        int date = jsonObject.getInt("date");
        double hour = jsonObject.getDouble("sleep hour");
        int userGrade = jsonObject.getInt("user grade");
        OneDaySleep oneDaySleep = new OneDaySleep(month, date, hour, userGrade);
        sl.addOneDaySleep(oneDaySleep);
    }
}


