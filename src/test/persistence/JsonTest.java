package persistence;

import model.OneDaySleep;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**********
 * Citation URL: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
 * cite the JsonTest class written by CPSC 210 instructor
 **********/
public class JsonTest {
    protected void checkOneDaySleep(int month, int date, double hour, int userGrade, OneDaySleep oneDaySleep) {
        assertEquals(month, oneDaySleep.getMonth());
        assertEquals(date, oneDaySleep.getDate());
        assertEquals(hour, oneDaySleep.getHour());
        assertEquals(userGrade, oneDaySleep.getGrade());
    }
}
