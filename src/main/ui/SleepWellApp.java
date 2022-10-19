package ui;

import model.DataCollectionAndProcess;
import model.OneDaySleep;


import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class SleepWellApp {
    private DataCollectionAndProcess dataCollectionAndProcess = new DataCollectionAndProcess();
    private final Scanner scanner;

    // Effects: runs the sleep well application
    public SleepWellApp() {
        this.scanner = new Scanner(System.in);
        selectToDo();
    }

    // Effects: displays options to user
    public void selectToDo() {
        while (true) {
            System.out.println("Please select an option : \n"
                    + "[1] input sleeping data \n"
                    + "[2] get system grade of for sleeping\n"
                    + "[3] get average hour for given time interval \n"
                    + "[4] get report for sleeping \n"
                    + "[5] quit");
            String selectedOption = scanner.nextLine();
            if (selectedOption.equals("5")) {
                System.out.println("Thanks for using the SleepWell App!");
                break;
            }
            executeSelection(selectedOption);
        }
    }

    // Requires: 1 <= option <= 5
    // Effects: processes user input
    public void executeSelection(String option) {
        switch (option) {
            case "1":
                inputData();
                break;
            case "2":
                getSystemGrade();
                break;
            case "3":
                getAverage();
                break;
            case "4":
                getReport();
                break;
            default:
                break;
        }
    }

    // Effects: processes user input
    private void getSystemGrade() {
        System.out.println("You have already added data for these dates:"
                + getDate()
                + "\n Please input the date to see the system grade: \n month:");
        int month = scanner.nextInt();
        System.out.println("date:");
        int date = scanner.nextInt();
        if (this.dataCollectionAndProcess.getSystemGradeGivenDay(month,date) != 0) {
            System.out.println("The system grade for" + " " + month + "/" + date + " " + "is:"
                    + this.dataCollectionAndProcess.getSystemGradeGivenDay(month, date));
            scanner.nextLine();
        } else {
            System.out.println("Given date is not in the list!");
            scanner.nextLine();
        }
    }

    // Effects: creates a date list for existing sleep days and returns the list
    private List<String> getDate() {
        List<String> dateList = new ArrayList<>();
        for (OneDaySleep oneDaySleep : this.dataCollectionAndProcess.getSleepDays()) {
            String date = oneDaySleep.getMonth() + "/" + oneDaySleep.getDate();
            dateList.add(date);
        }
        return dateList;
    }

    // Requires: the input can only be "All", "Period" and "Month"
    // Effects: processes user input
    public void getReport() {
        System.out.println("Input 'All', 'Period' or 'Month' to get report:");
        String choose = scanner.nextLine();
        switch (choose) {
            case "All":
                printList(this.dataCollectionAndProcess.getSleepDays());
                break;
            case "Period":
                reportForPeriod();
                break;
            case "Month":
                System.out.println("Please input the month to get report:");
                int month = scanner.nextInt();
                printList(this.dataCollectionAndProcess.getReportForMonth(month));
                break;
            default:
                break;
        }
    }

    // Effects: process for user input
    public void reportForPeriod() {
        System.out.println("Please input the time period to get report: \n Start month:");
        int startMonth = scanner.nextInt();
        System.out.println("Start date:");
        int startDate = scanner.nextInt();
        System.out.println("End month:");
        int endMonth = scanner.nextInt();
        System.out.println("End date");
        int endDate = scanner.nextInt();
        printList(this.dataCollectionAndProcess.getReportForGiven(startMonth, startDate, endMonth, endDate));
    }

    // Effects: prints the list of existing sleeping data with month, date, hour, user grade and system grade
    public void printList(List<OneDaySleep> sleepDayList) {
        for (OneDaySleep oneDaySleep : sleepDayList) {
            int month = oneDaySleep.getMonth();
            int date = oneDaySleep.getDate();
            double hour = oneDaySleep.getHour();
            int userGrade = oneDaySleep.getGrade();
            int systemGrade = oneDaySleep.systemGrading(hour);
            System.out.println("Date:" + month + "/" + date
                    + "\n Sleeping Hour:" + hour
                    + "\n Your sleeping quality:" + userGrade
                    + "\n System Grade:" + systemGrade);
        }
        scanner.nextLine();
    }

    // Effects: processes user input
    public void getAverage() {
        System.out.println("Please input the time interval to get average hour: \n Start month:");
        int startMonth = scanner.nextInt();
        System.out.println("Start date:");
        int startDate = scanner.nextInt();
        System.out.println("End month:");
        int endMonth = scanner.nextInt();
        System.out.println("End date");
        int endDate = scanner.nextInt();
        double averageHour = this.dataCollectionAndProcess.averageHourGivenDate(
                startMonth, startDate, endMonth, endDate);
        System.out.println("The average hour is:" + averageHour);
        scanner.nextLine();
    }

    // Effects: processes user input
    public void inputData() {
        System.out.println("Please input the month:");
        int month = scanner.nextInt();
        System.out.println("Please input the date:");
        int date = scanner.nextInt();
        System.out.println("Please input the sleeping hour:");
        double hour = scanner.nextDouble();
        System.out.println("Please grade your sleeping quality from 1 to 3:");
        int grade = scanner.nextInt();
        OneDaySleep oneDaySleep = new OneDaySleep(month, date, hour, grade);
        this.dataCollectionAndProcess.addOneDaySleep(oneDaySleep);
        System.out.println(
                "You successfully add the following data:"
                        + "\n Month:" + month
                        + "\n Date:" + date
                        + "\n Sleeping hour:" + hour
                        + "\n Your grade for sleeping quality:" + grade);
        scanner.nextLine();
    }
}


