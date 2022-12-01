package ui;

import model.DataCollectionAndProcess;
import model.EventLog;
import model.OneDaySleep;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.FileNotFoundException;
import java.io.IOException;

// Represents the GUI
public class SleepUI extends JFrame {
    private JWindow window;
    private JButton addButton;
    private JFrame frame;
    private JTextField textFieldMonth;
    private JTextField textFieldDate;
    private JTextField textFieldHour;
    private JSpinner spinner;
    private Integer[] grades;
    private JMenuBar bar;
    private JMenu menu;
    private JMenu menu1;
    private JMenuItem item;
    private JMenuItem item1;
    private JMenuItem save;
    private JMenuItem load;
    private static final String JSON_STORE = "./data/SleepList.json";
    private JList list;
    private DefaultListModel listModel;

    private DataCollectionAndProcess dataCollectionAndProcess;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    // Constructs a GUI
    public SleepUI() {
        frame = new JFrame("SleepWell");
        frame.setSize(700, 700);
        frame.setLayout(new FlowLayout());
        frame.setLocationRelativeTo(null);
        frame.getContentPane().setBackground(Color.lightGray);

        dataCollectionAndProcess = new DataCollectionAndProcess("Sleep List");
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);

        listModel = new DefaultListModel();
        list = new JList<>(listModel);
        list.setBounds(160, 140, 300, 300);
        frame.add(list);


        createWindow();
        createMenu();
        createEntryFields();
        createLabel();
        createButton();
        frame.addWindowListener(new WindowCloseEvent());
        addButton.addActionListener(new AddDataAction());
        frame.setLayout(null);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

    }

    // Effects: create label on main frame
    public void createLabel() {
        JLabel l1 = new JLabel("Month:");
        JLabel l2 = new JLabel("Date:");
        JLabel l3 = new JLabel("Hour:");
        JLabel l4 = new JLabel("Grade:");
        l1.setBounds(40, 140, 80, 10);
        l2.setBounds(40, 220, 80, 10);
        l3.setBounds(40, 300, 80, 10);
        l4.setBounds(40, 380, 80, 10);
        frame.add(l1);
        frame.add(l2);
        frame.add(l3);
        frame.add(l4);
    }

    // Effects: create entry field to input sleep data on main frame
    protected void createEntryFields() {
        textFieldMonth = new JTextField();
        textFieldDate = new JTextField();
        textFieldHour = new JTextField();
        grades = new Integer[]{0, 1, 2, 3};
        spinner = new JSpinner(new SpinnerListModel(grades));
        spinner.setBounds(40, 400, 80, 40);
        textFieldMonth.setBounds(40, 160, 80, 40);
        textFieldDate.setBounds(40, 240, 80, 40);
        textFieldHour.setBounds(40, 320, 80, 40);
        frame.add(textFieldDate);
        frame.add(textFieldMonth);
        frame.add(textFieldHour);
        frame.add(spinner);

    }

    // Effects: create button to add data on main frame
    private void createButton() {
        addButton = new JButton("Add data");
        addButton.setBounds(40, 460, 120, 60);
        frame.add(addButton);

    }

    // Effects: create menu bar and menu on main frame
    private void createMenu() {
        bar = new JMenuBar();
        menu = new JMenu("Report");
        menu1 = new JMenu("File");
        item = new JMenuItem("Report All");
        item.addActionListener(new ReportForAll());
        item1 = new JMenuItem(new MonthReport());
        save = new JMenuItem(new SaveAction());
        load = new JMenuItem(new LoadAction());
        menu.add(item);
        menu.add(item1);
        menu1.add(save);
        menu1.add(load);
        bar.add(menu);
        bar.add(menu1);
        frame.setJMenuBar(bar);
    }

    // Effects: create open window for GUI
    public void createWindow() {
        window = new JWindow();
        Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
        Icon img = new ImageIcon("./data/WechatIMG9772.jpeg");
        JLabel label = new JLabel(img);
        window.getContentPane().add(label);
        window.setBounds(((int) d.getWidth() - 900) / 2, ((int) d.getHeight() - 700) / 2, 995, 632);
        window.setVisible(true);
        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        window.setVisible(false);
    }

    // Represents add data button action
    private class AddDataAction implements ActionListener {
        private String month;
        private String date;
        private String hour;
        private int grade;

        public AddDataAction() {
        }

        // Effects: constructs action event for adding-data button
        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == addButton) {
                month = textFieldMonth.getText();
                date = textFieldDate.getText();
                hour = textFieldHour.getText();
                grade = (int) spinner.getValue();
                String gradeStr = Integer.toString(grade);
                String s = "Month: " + month + ", Date: " + date + ", Hour: " + hour + ", Grade: " + gradeStr;
                listModel.addElement(s);

                int monthInt = Integer.parseInt(month);
                int dateInt = Integer.parseInt(date);
                Double hourDou = Double.parseDouble(hour);
                dataCollectionAndProcess.addOneDaySleep(new OneDaySleep(monthInt, dateInt, hourDou, grade));
            }

            textFieldMonth.requestFocusInWindow();
            textFieldMonth.setText("");
            textFieldDate.requestFocusInWindow();
            textFieldDate.setText("");
            textFieldHour.requestFocusInWindow();
            textFieldHour.setText("");
            spinner.setValue(0);

        }
    }

    // Represents action events for report all item
    private class ReportForAll extends AbstractAction {
        private JFrame popFrame;

        public ReportForAll() {
            super("Report All");
        }

        // Effects: constructs action for report all item
        @Override
        public void actionPerformed(ActionEvent e) {
            popFrame = new JFrame("Report");
            popFrame.setBounds(150, 150, 500, 300);
            if (e.getSource() == item) {
                print();
                popFrame.setLocationRelativeTo(null);
                popFrame.setDefaultCloseOperation(HIDE_ON_CLOSE);
                popFrame.setVisible(true);
            }
        }

        // Effects: print the report
        public void print() {
            String month;
            String date;
            String hour;
            String grade;
            String systemGrade;
            DefaultListModel model;
            model = new DefaultListModel();
            JList reportList = new JList(model);

            for (OneDaySleep ods : dataCollectionAndProcess.getSleepDays()) {
                month = Integer.toString(ods.getMonth());
                date = Integer.toString(ods.getDate());
                hour = Double.toString(ods.getHour());
                grade = Integer.toString(ods.getGrade());
                systemGrade = Integer.toString(ods.systemGrading(ods.getHour()));
                model.addElement(
                        "Month: " + month + ", Date: " + date + ", Hour: " + hour + ", Grade: "
                                + grade + ", System Grade: " + systemGrade);
            }
            popFrame.add(reportList);
        }
    }

    // Represents action events for report for month item
    private class MonthReport extends AbstractAction {
        private JFrame monthFrame;
        private JButton report;
        private JTextField monthField;
        private JList monthList;
        private DefaultListModel monthModel;
        private JLabel label;

        public MonthReport() {
            super("Month Report");
        }

        // Effects: constructs action for month report item
        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == item1) {
                setFrame();

                monthFrame.setDefaultCloseOperation(HIDE_ON_CLOSE);
                monthFrame.setVisible(true);
            }
        }

        // Effects: set the frame to show the report
        public void setFrame() {
            monthFrame = new JFrame("Report");
            monthFrame.setSize(600, 600);
            monthFrame.setLayout(null);
            monthFrame.getContentPane().setBackground(Color.lightGray);
            monthFrame.setLocationRelativeTo(null);

            label = new JLabel("Enter month");
            label.setBounds(40, 100, 80, 10);
            monthFrame.add(label);

            monthField = new JTextField();
            monthField.setBounds(60, 120, 80, 40);
            monthFrame.add(monthField);

            report = new JButton("Get Report");
            report.setBounds(40, 180, 90, 40);
            report.addActionListener(new ReportListener());
            monthFrame.add(report);

            monthModel = new DefaultListModel();
            monthList = new JList<>(monthModel);
            monthList.setBounds(170, 100, 400, 400);
            monthFrame.add(monthList);



        }

        // Represents action events for get reports in month report frame
        private class ReportListener implements ActionListener {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == report) {
                    print();
                }
            }
        }

        // Effects: prints the data in list
        public void print() {
            String month;
            String date;
            String hour;
            String grade;
            String systemGrade;
            int monthInt = Integer.parseInt(monthField.getText());

            for (OneDaySleep ods : dataCollectionAndProcess.getReportForMonth(monthInt)) {
                month = Integer.toString(ods.getMonth());
                date = Integer.toString(ods.getDate());
                hour = Double.toString(ods.getHour());
                grade = Integer.toString(ods.getGrade());
                systemGrade = Integer.toString(ods.systemGrading(ods.getHour()));
                monthModel.addElement(
                        "Month: " + month + ", Date: " + date + ", Hour: " + hour + ", Grade: "
                                + grade + ", System Grade: " + systemGrade);
            }

            monthField.requestFocusInWindow();
            monthField.setText("");
        }
    }

    // Constructs action events for save menu to save input data
    private class SaveAction extends AbstractAction {

        public SaveAction() {
            super("Save");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            saveSleepList();
        }

        // Effects: save button event
        private void saveSleepList() {
            try {
                jsonWriter.open();
                jsonWriter.write(dataCollectionAndProcess);
                jsonWriter.close();
                System.out.println("Saved " + dataCollectionAndProcess.getName() + " to " + JSON_STORE);
            } catch (FileNotFoundException e) {
                System.out.println("Unable to write to file: " + JSON_STORE);
            }
        }
    }

    // Constructs action events for load menu to load data from file
    private class LoadAction extends AbstractAction {

        LoadAction() {
            super("Load");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            loadSleepList();
        }

        // Effects: load button event
        private void loadSleepList() {
            try {
                dataCollectionAndProcess = jsonReader.read();
                System.out.println("Loaded " + dataCollectionAndProcess.getName() + " from " + JSON_STORE);
//                for (OneDaySleep ods : dataCollectionAndProcess.getSleepDays()) {
//                    int month = ods.getMonth();
//                    int date = ods.getDate();
//                    Double hour = ods.getHour();
//                    int grade = ods.getGrade();
//                    String s = "Month: " + month + ", Date: " + date + ", Hour: " + hour + ", Grade: " + grade;
//                    listModel.addElement(s);
//                }
            } catch (IOException e) {
                System.out.println("Unable to read from file: " + JSON_STORE);
            }
        }
    }

    // Represents window action
    private class WindowCloseEvent implements WindowListener {
        private LogPrinter lp;

        @Override
        public void windowOpened(WindowEvent e) {

        }

        // Effects: prints the events in EventLog when closing the app
        @Override
        public void windowClosing(WindowEvent e) {
            lp = new ConsolePrinter();
            lp.printLog(EventLog.getInstance());
        }

        @Override
        public void windowClosed(WindowEvent e) {
        }

        @Override
        public void windowIconified(WindowEvent e) {

        }

        @Override
        public void windowDeiconified(WindowEvent e) {

        }

        @Override
        public void windowActivated(WindowEvent e) {

        }

        @Override
        public void windowDeactivated(WindowEvent e) {

        }
    }
}




