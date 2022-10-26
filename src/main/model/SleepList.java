//package model;
//
//import org.json.JSONArray;
//import org.json.JSONObject;
//import persistence.Writable;
//
//import java.util.ArrayList;
//import java.util.Collections;
//import java.util.List;
//
//// Represents SleepList having a collection of sleepList
//public class SleepList implements Writable {
//    private String name;
//    private List<OneDaySleep> sleepList;
//
//    // EFFECTS: constructs sleep list with a name and empty list of sleepList
//    public SleepList(String name) {
//        this.name = name;
//        sleepList = new ArrayList<>();
//    }
//
//    public String getName() {
//        return name;
//    }
//
//    // MODIFIES: this
//    // EFFECTS: adds oneDaySleep to this sleepList
//    public void addOneDaySleep(OneDaySleep oneDaySleep) {
//        sleepList.add(oneDaySleep);
//    }
//
//    // EFFECTS: returns an unmodifiable list of sleepList in this workroom
//    public List<OneDaySleep> getSleepList() {
//        return Collections.unmodifiableList(sleepList);
//    }
//
//    // EFFECTS: returns number of sleepList in this workroom
//    public int numSleepList() {
//        return sleepList.size();
//    }
//
//    @Override
//    public JSONObject toJson() {
//        JSONObject json = new JSONObject();
//        json.put("name", name);
//        json.put("sleep data", sleepListToJson());
//        return json;
//    }
//
//    // EFFECTS: returns oneDaySleep in the sleepList as a JSON array
//    private JSONArray sleepListToJson() {
//        JSONArray jsonArray = new JSONArray();
//
//        for (OneDaySleep t : sleepList) {
//            jsonArray.put(t.toJson());
//        }
//
//        return jsonArray;
//    }
//}
//
