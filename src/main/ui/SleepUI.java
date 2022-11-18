package ui;

import model.DataCollectionAndProcess;
import model.OneDaySleep;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.FileNotFoundException;
import java.io.IOException;


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
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;
    private DataCollectionAndProcess dataCollectionAndProcess;
    private JList list;
    private DefaultListModel listModel;

    public SleepUI() {
        frame = new JFrame("SleepWell");
        frame.setSize(600, 600);
        frame.setLayout(new FlowLayout());
        frame.setLocationRelativeTo(null);
        frame.getContentPane().setBackground(Color.pink);

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
        addButton.addActionListener(new AddDataAction());
        frame.setLayout(null);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

    }

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

    // Effects: create entry field to input sleep data
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

    private void createButton() {
        addButton = new JButton("Add data");
        addButton.setBounds(40, 460, 120, 60);
        frame.add(addButton);

    }

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

    public void createWindow() {
        window = new JWindow();
        Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
        Icon img = new ImageIcon(this.getClass().getResource("WechatIMG9772.jpeg"));
        JLabel label = new JLabel(img);
        window.getContentPane().add(label);
        window.setBounds(((int) d.getWidth() - 900) / 2, ((int) d.getHeight() - 700) / 2, 995, 632);
        window.setVisible(true);
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        window.setVisible(false);
    }


    private class AddDataAction implements ActionListener {
        private String month;
        private String date;
        private String hour;
        private int grade;

        public AddDataAction() {

        }

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

    private class ReportForAll extends AbstractAction {
        private JFrame popFrame;

        public ReportForAll() {
            super("Report All");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            popFrame = new JFrame("Report");
            popFrame.setBounds(150, 150, 500, 300);
            if (e.getSource() == item) {
                print();
                popFrame.setDefaultCloseOperation(HIDE_ON_CLOSE);
                popFrame.setVisible(true);
            }
        }

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

    private class MonthReport extends AbstractAction {
        private JFrame monthFrame;
        private JDialog dialog;
        private JButton report;
        private JTextField monthField;
        private JList monthList;
        private DefaultListModel monthModel;
        private JLabel label;

        public MonthReport() {
            super("Month Report");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == item1) {
                setFrame();
            }
        }

        public void setFrame() {
            monthFrame = new JFrame("Report");
            monthFrame.setSize(600, 600);
            monthFrame.getContentPane().setBackground(Color.cyan);

            monthField = new JTextField();
            monthField.setBounds(40, 160, 40, 40);

            report = new JButton("Get Report");
            report.setBounds(60, 220, 90, 40);
            report.addActionListener(new ReportListener());

            label = new JLabel("Enter month");
            label.setBounds(40, 140, 80, 10);

            monthModel = new DefaultListModel();
            monthList = new JList<>(monthModel);
            monthList.setBounds(160, 140, 400, 300);

            monthFrame.add(monthList);
            monthFrame.add(report);
            monthFrame.add(label);
            monthFrame.add(monthField);
            monthFrame.setDefaultCloseOperation(HIDE_ON_CLOSE);
            monthFrame.setVisible(true);
        }

        private class ReportListener implements ActionListener {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == report) {
                    print();
                }
            }
        }

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

            textFieldMonth.requestFocusInWindow();
            textFieldMonth.setText("");
        }
    }


    private class SaveAction extends AbstractAction {

        public SaveAction() {
            super("Save");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            saveSleepList();
        }

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

    private class LoadAction extends AbstractAction {

        LoadAction() {
            super("Load");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            loadSleepList();
        }

        private void loadSleepList() {
            try {
                dataCollectionAndProcess = jsonReader.read();
                System.out.println("Loaded " + dataCollectionAndProcess.getName() + " from " + JSON_STORE);
                for (OneDaySleep ods : dataCollectionAndProcess.getSleepDays()) {
                    int month = ods.getMonth();
                    int date = ods.getDate();
                    Double hour = ods.getHour();
                    int grade = ods.getGrade();
                    String s = "Month: " + month + ", Date: " + date + ", Hour: " + hour + ", Grade: " + grade;
                    listModel.addElement(s);
                }
            } catch (IOException e) {
                System.out.println("Unable to read from file: " + JSON_STORE);
            }
        }
    }
}




