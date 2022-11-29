package ui;

import model.Event;
import model.EventLog;

public class ConsolePrinter implements LogPrinter {
    private String log;

    @Override
    public void printLog(EventLog el) {
        for (Event event : el) {
            log = event.toString();
            System.out.println(log);
        }
    }
}
