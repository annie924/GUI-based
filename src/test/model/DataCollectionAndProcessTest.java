package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class DataCollectionAndProcessTest {
    private DataCollectionAndProcess testDataCollectionAndProcess;
    private OneDaySleep oneDaySleep;
    private OneDaySleep oneDaySleep1;
    private OneDaySleep oneDaySleep2;
    private OneDaySleep oneDaySleep3;
    private OneDaySleep oneDaySleep4;
    private OneDaySleep oneDaySleep5;


    @BeforeEach
    public void set() {
        this.testDataCollectionAndProcess = new DataCollectionAndProcess("My sleep list");
        oneDaySleep1 = new OneDaySleep(9, 23, 4.0, 1);
        oneDaySleep = new OneDaySleep(9, 24, 3.8, 1);
        oneDaySleep2 = new OneDaySleep(9, 25, 4.5, 2);
        oneDaySleep3 = new OneDaySleep(9, 26, 7.0, 2);
        oneDaySleep4 = new OneDaySleep(11, 27, 8.0, 3);
        oneDaySleep5 = new OneDaySleep(10, 27, 7.0, 3);
    }

    @Test
    public void testEmptyDataCollectionAndProcess() {
        List<OneDaySleep> testSleepDays = testDataCollectionAndProcess.getSleepDays();
        assertEquals(0, testSleepDays.size());
    }

    @Test
    public void testAddOneDay() {
        testDataCollectionAndProcess.addOneDaySleep(oneDaySleep);
        List<OneDaySleep> testSleepDays = testDataCollectionAndProcess.getSleepDays();
        assertEquals(1, testSleepDays.size());
        assertEquals(oneDaySleep, testSleepDays.get(0));
    }

    @Test
    public void testAddMultipleDays() {
        testDataCollectionAndProcess.addOneDaySleep(oneDaySleep);
        testDataCollectionAndProcess.addOneDaySleep(oneDaySleep1);
        testDataCollectionAndProcess.addOneDaySleep(oneDaySleep2);
        List<OneDaySleep> testSleepDays = testDataCollectionAndProcess.getSleepDays();
        assertEquals(3, testSleepDays.size());
        assertEquals(oneDaySleep, testSleepDays.get(0));
        assertEquals(oneDaySleep1, testSleepDays.get(1));
        assertEquals(oneDaySleep2, testSleepDays.get(2));
    }


    @Test
    public void testGetReportForMonth() {
        testDataCollectionAndProcess.addOneDaySleep(oneDaySleep);
        testDataCollectionAndProcess.addOneDaySleep(oneDaySleep1);
        testDataCollectionAndProcess.addOneDaySleep(oneDaySleep5);
        List<OneDaySleep> reportForMonth = testDataCollectionAndProcess.getReportForMonth(9);
        assertEquals(2, reportForMonth.size());
        assertEquals(oneDaySleep, reportForMonth.get(0));
        assertEquals(oneDaySleep1, reportForMonth.get(1));
    }

    @Test
    public void testAverageHourGivenDateSameMonth() {
        testDataCollectionAndProcess.addOneDaySleep(oneDaySleep);
        testDataCollectionAndProcess.addOneDaySleep(oneDaySleep1);
        testDataCollectionAndProcess.addOneDaySleep(oneDaySleep2);
        double realHour = testDataCollectionAndProcess.averageHourGivenDate(
                9, 23, 9, 25);
        double averageHour = (4.0 + 3.8 + 4.5) / 3.0;
        assertEquals(averageHour, realHour);
    }

    @Test
    public void testAverageHourGivenDateDiffMonth() {
        testDataCollectionAndProcess.addOneDaySleep(oneDaySleep);
        testDataCollectionAndProcess.addOneDaySleep(oneDaySleep1);
        testDataCollectionAndProcess.addOneDaySleep(oneDaySleep2);
        testDataCollectionAndProcess.addOneDaySleep(oneDaySleep3);
        testDataCollectionAndProcess.addOneDaySleep(oneDaySleep4);
        testDataCollectionAndProcess.addOneDaySleep(oneDaySleep5);
        double realHour1 = testDataCollectionAndProcess.averageHourGivenDate(
                9, 24, 10, 27);
        double averageHour1 = (3.8 + 4.5 + 7.0 + 7.0) / 4.0;
        assertEquals(averageHour1, realHour1);
    }

    @Test
    public void testEmptyGetReportForGiven() {
        List<OneDaySleep> reportForAll = testDataCollectionAndProcess.getReportForGiven(
                9, 24, 9, 25);
        assertEquals(0, reportForAll.size());
    }

    @Test
    public void testGetReportForGiven() {
        testDataCollectionAndProcess.addOneDaySleep(oneDaySleep1);
        testDataCollectionAndProcess.addOneDaySleep(oneDaySleep);
        testDataCollectionAndProcess.addOneDaySleep(oneDaySleep2);
        testDataCollectionAndProcess.addOneDaySleep(oneDaySleep3);
        List<OneDaySleep> reportForGiven = testDataCollectionAndProcess.getReportForGiven(
                9, 23, 9, 25);
        assertEquals(3, reportForGiven.size());
        assertEquals(oneDaySleep1, reportForGiven.get(0));
        assertEquals(oneDaySleep, reportForGiven.get(1));
        assertEquals(oneDaySleep2, reportForGiven.get(2));

        testDataCollectionAndProcess.addOneDaySleep(oneDaySleep5);
        List<OneDaySleep> reportForGiven0 = testDataCollectionAndProcess.getReportForGiven(
                9, 1, 9, 30);
        assertEquals(4, reportForGiven0.size());
        assertEquals(oneDaySleep1, reportForGiven0.get(0));
        assertEquals(oneDaySleep, reportForGiven0.get(1));
        assertEquals(oneDaySleep2, reportForGiven0.get(2));
        assertEquals(oneDaySleep3, reportForGiven0.get(3));

        testDataCollectionAndProcess.addOneDaySleep(oneDaySleep4);
        List<OneDaySleep> reportForGiven1 = testDataCollectionAndProcess.getReportForGiven(
                9, 22, 10, 28);
        assertEquals(5, reportForGiven1.size());
        assertEquals(oneDaySleep1, reportForGiven1.get(0));
        assertEquals(oneDaySleep, reportForGiven1.get(1));
        assertEquals(oneDaySleep2, reportForGiven1.get(2));
        assertEquals(oneDaySleep3, reportForGiven1.get(3));
        assertEquals(oneDaySleep5, reportForGiven1.get(4));

        List<OneDaySleep> reportForGiven2 = testDataCollectionAndProcess.getReportForGiven(
                9, 27, 11, 27);
        assertEquals(2, reportForGiven2.size());
        assertEquals(oneDaySleep5, reportForGiven2.get(0));
        assertEquals(oneDaySleep4, reportForGiven2.get(1));

        List<OneDaySleep> reportForGGiven3 = testDataCollectionAndProcess.getReportForGiven(
                10, 20, 9, 24);
        assertEquals(0, reportForGGiven3.size());
    }

    @Test
    public void testGetSystemGradeGivenDays() {
        testDataCollectionAndProcess.addOneDaySleep(oneDaySleep1);
        testDataCollectionAndProcess.addOneDaySleep(oneDaySleep);
        testDataCollectionAndProcess.addOneDaySleep(oneDaySleep2);
        int testGrade = testDataCollectionAndProcess.getSystemGradeGivenDay(9, 23);
        assertEquals(oneDaySleep1.getSystemGrade(), testGrade);
        int testGrade1 = testDataCollectionAndProcess.getSystemGradeGivenDay(10, 23);
        assertEquals(0, testGrade1);
        int testGrade2 = testDataCollectionAndProcess.getSystemGradeGivenDay(9, 28);
        assertEquals(0, testGrade2);
    }
}
