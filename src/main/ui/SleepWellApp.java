package ui;

import model.DataCollectionAndProcess;
import model.OneDaySleep;

import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class SleepWellApp {
    private final DataCollectionAndProcess dataCollectionAndProcess = new DataCollectionAndProcess();
    private final Scanner scanner;

    // sleep well application
    public SleepWellApp() {
        scanner = new Scanner(System.in);
        selectToDo();
    }

    public void selectToDo() {
        while (true) {
            System.out.println("Please select an option : \n"
                    + "[1] input sleeping data \n"
                    + "[2] get average hour for given time interval \n"
                    + "[3] get report for sleeping \n"
                    + "[4] quit");
            String selectedOption = scanner.nextLine();
            if (selectedOption.equals("4")) {
                System.out.println("Thanks for using the SleepWell App!");
                break;
            }
            executeSelection(selectedOption);
        }
    }

    public void executeSelection(String option) {
        switch (option) {
            case "1":
                inputData();
                System.out.println("Do you want to continue or quit?");
                break;
            case "2":
                getAverage();
                break;
            case "3":
                getReport();
            default:
                break;
        }
    }

    public void getReport() {
        System.out.println("Input All, Period or Month to get report:q");
        String choose = scanner.nextLine();
        switch (choose) {
            case "All" -> printList(this.dataCollectionAndProcess.getSleepDays());
            case "Given" -> {
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
            case "Month" -> {
                System.out.println("Please input the month to get report:");
                int month = scanner.nextInt();
                printList(this.dataCollectionAndProcess.getReportForMonth(month));
            }
        }
    }

    public void printList(List<OneDaySleep> sleepDayList) {
        for (OneDaySleep oneDaySleep : sleepDayList) {
            int month = oneDaySleep.getMonth();
            int date = oneDaySleep.getDate();
            double hour = oneDaySleep.getHour();
            int userGrade = oneDaySleep.getGrade();
            int systemGrade = oneDaySleep.getSystemGrade();
            System.out.println("Date:" + month + "'/'" + date
                    + "\n Sleeping Hour:" + hour
                    + "\n Your sleeping quality:" + userGrade
                    + "\n System Grade:" + systemGrade);
        }
    }

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
    }

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
    }


}
