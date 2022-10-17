package model;

import java.util.List;

// Represents a collection of all the data for one-date sleep that has
// a month, date, hour, userGrade and systemGrade
public class OneDaySleep {
    private double sleepHour;
    private final int grade;
    private int systemGrade;
    private final int month;
    private final int date;

    // Requires: 1 <= recordMonth <= 12, 1 <= recordDate <= 31
    // 1 <= userGrade <= 3, hour > 0, 0 <= systemGrade <=3
    // Effects: constructs a OneDaySleep with sleep date, sleep hours and user grade
    // about the sleeping quality. Initially, the system grade is 0
    public OneDaySleep(int recordMonth, int recordDate, double hour, int userGrade) {
        this.month = recordMonth;
        this.date = recordDate;
        this.sleepHour = hour;
        this.grade = userGrade;
        this.systemGrade = 0;
    }


    // Requires: sleepHour >= 0, 1 <= point <= 3
    // Modifies: this
    // Effects: judges the given sleepHour.
    // If 0 < sleepHour <= 4 then grade it to 1;
    // If 4 < sleepHour <= 7, then grade it to 2;
    // If sleepHour > 7, then grade it to 3;
    public int systemGrading(double gradeHour) {
        this.sleepHour = gradeHour;
        if (gradeHour > 0 && gradeHour <= 4) {
            this.systemGrade = 1;
        } else if (gradeHour > 4 && gradeHour <= 7) {
            this.systemGrade = 2;
        } else {
            this.systemGrade = 3;
        }
        return this.systemGrade;
    }

    // getters
    public int getMonth() {
        return this.month;
    }

    public int getDate() {
        return this.date;
    }

    public Double getHour() {
        return this.sleepHour;
    }

    public int getGrade() {
        return this.grade;
    }

    public int getSystemGrade() {
        return this.systemGrade;
    }
}
