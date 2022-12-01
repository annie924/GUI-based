package ui;

import model.Event;
import model.EventLog;

// Represents the printer for EventLog
public class ConsolePrinter implements LogPrinter {
    private String log;

    // Effects: print each event in EventLog
    @Override
    public void printLog(EventLog el) {
        for (Event event : el) {
            log = event.toString();
            System.out.println(log);
        }
    }
}
