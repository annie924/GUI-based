package persistence;

import org.json.JSONObject;

/**********
 * Citation URL: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
 * cite the Writable interface written by CPSC 210 instructor
 **********/
public interface Writable {
    // EFFECTS: returns this as JSON object
    JSONObject toJson();
}
