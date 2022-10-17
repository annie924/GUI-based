package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class OneDaySleepTest {
    private OneDaySleep oneDaySleep;
    private OneDaySleep oneDaySleep1;
    private OneDaySleep oneDaySleep2;
    private OneDaySleep oneDaySleep3;
    private OneDaySleep oneDaySleep4;


    @BeforeEach
    public void set() {
        oneDaySleep = new OneDaySleep(9,24,3.8,1);
        oneDaySleep1 = new OneDaySleep(9,23,4,1);
        oneDaySleep2 = new OneDaySleep(9,25,4.5,2);
        oneDaySleep3 = new OneDaySleep(9,26,7,2);
        oneDaySleep4 = new OneDaySleep(9,27,8,3);
    }

    @Test
    public void testConstructor() {
        assertEquals(9,oneDaySleep.getMonth());
        assertEquals(24, oneDaySleep.getDate());
        assertEquals(3.8,oneDaySleep.getHour());
        assertEquals(1, oneDaySleep.getGrade());
        assertEquals(0,oneDaySleep.getSystemGrade());
    }

    @Test
    public void testSystemGrading() {
        double gradeHour = oneDaySleep.getHour();
        oneDaySleep.systemGrading(gradeHour);
        assertEquals(1,oneDaySleep.getSystemGrade());

        double gradeHour1 = oneDaySleep1.getHour();
        oneDaySleep1.systemGrading(gradeHour1);
        assertEquals(1, oneDaySleep1.getSystemGrade());

        double gradeHour2 = oneDaySleep2.getHour();
        oneDaySleep2.systemGrading(gradeHour2);
        assertEquals(2,oneDaySleep2.getSystemGrade());

        double gradeHour3 = oneDaySleep3.getHour();
        oneDaySleep3.systemGrading(gradeHour3);
        assertEquals(2,oneDaySleep3.getSystemGrade());

        double gradeHour4 = oneDaySleep4.getHour();
        oneDaySleep4.systemGrading(gradeHour4);
        assertEquals(3,oneDaySleep4.getSystemGrade());

    }

}
