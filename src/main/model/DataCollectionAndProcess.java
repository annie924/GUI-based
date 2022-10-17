package model;

import java.util.ArrayList;
import java.util.List;

// Represents a list of oneDaySleep data, calculates the average hours and gives a report
public class DataCollectionAndProcess {
    private List<OneDaySleep> sleepDays;

    // Effects: constructs an empty collection of sleepDays
    public DataCollectionAndProcess() {
        this.sleepDays = new ArrayList<>();
    }

    // Requires: 1 <= recordMonth <= 12, 1 <= recordDate <= 31
    // 1 <= userGrade <= 3, hour > 0, 0 <= systemGrade <=3
    // Modifies: this
    // Effects: adds a new oneDaySleep to the collection of sleepDays to be completed
    public void addOneDaySleep(OneDaySleep oneDaySleep) {
        this.sleepDays.add(oneDaySleep);
    }

    // Requires: 1 <= startDate <= 31, 1 <= endDate <= 31, 1 <= startMonth <= 12,1 <= endMonth <= 12
    // endMonth >= startMonth
    // Modifies: this
    // Effects: calculates average hour for given date interval
    public double averageHourGivenDate(int startMonth, int startDate, int endMonth, int endDate) {
        double totalHour = 0.0;
        List<OneDaySleep> sleepGiven = getReportForGiven(startMonth,startDate,endMonth,endDate);
        double givenDays = sleepGiven.size();
        for (OneDaySleep oneDaySleep : sleepGiven) {
            totalHour += oneDaySleep.getHour();
        }
        double averageHour;
        return averageHour = totalHour / givenDays;
    }


    // Requires: 1<= reportMonth <= 12
    // Effects: gets report of all sleeping data in given month
    public List<OneDaySleep> getReportForMonth(int reportMonth) {
        List<OneDaySleep> givenMonthSleep = new ArrayList<>();
        for (OneDaySleep oneDaySleep : this.sleepDays) {
            if (oneDaySleep.getMonth() == reportMonth) {
                givenMonthSleep.add(oneDaySleep);
            }
        }
        return givenMonthSleep;
    }

    // Requires: 1 <= startDate <= 31, 1 <= endDate <= 31, 1 <= startMonth <= 12,1 <= endMonth <= 12
    // endMonth >= startMonth
    // Effects: gets a list of oneDaySleep of given time interval
    public List<OneDaySleep> getReportForGiven(int startMonth, int startDate, int endMonth, int endDate) {
        List<OneDaySleep> givenSleepDays = new ArrayList<>();
        if (startMonth == endMonth) {
            for (OneDaySleep oneDaySleep : this.sleepDays) {
                if (startDate <= oneDaySleep.getDate() && oneDaySleep.getDate() <= endDate) {
                    givenSleepDays.add(oneDaySleep);
                }
            }
        } else if (startMonth < endMonth) {
            for (OneDaySleep oneDaySleep : this.sleepDays) {
                if (oneDaySleep.getMonth() == startMonth && startDate <= oneDaySleep.getDate()) {
                    givenSleepDays.add(oneDaySleep);
                }
            }
            for (int i = startMonth + 1; i < endMonth; i++) {
                givenSleepDays.addAll(getReportForMonth(i));
            }
            for (OneDaySleep oneDaySleep : this.sleepDays) {
                if (oneDaySleep.getMonth() == endMonth && oneDaySleep.getDate() <= endDate) {
                    givenSleepDays.add(oneDaySleep);
                }
            }
        }
        return givenSleepDays;
    }

    // getters
    public List<OneDaySleep> getSleepDays() {
        return this.sleepDays;
    }
}
