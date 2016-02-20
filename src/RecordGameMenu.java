import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 * Created by timbauer on 12/27/15.
 */
public class RecordGameMenu extends TransparentOverlayBaseClass {

    JTextField dateEntryBox;
    JComboBox<String> regionComboBox, teamNameFromDb, monthComboBox, gameNumber;
    JComboBox<Integer> dayComboBox, yearComboBox;
    JButton goToRecordingMapWindow, backButton;
    String teamName, teamRegion;
    int numOfGameInCurrentSeries;
    ArrayList<String> listOfTeams;
    String[] teamList;
    StartRecordingOverlay startRecordingOverlay;

    MainWindow parentFrame;
    Dimension windowDimension;

    Date sqlDate = new Date(System.currentTimeMillis());
    String currentDate = sqlDate.toString();

    final String[] majorRegions = {"Please Select A Region", "NA LCS", "EU LCS", "LCK (Korea)", "LPL (China)",
            "LMS (Taiwan)", "IWC (International Wild Card)"};

    final String[] months = {"January", "February", "March", "April", "May", "June", "July", "August", "September",
            "October", "November", "December"};

    final Integer[] days = {1, 2, 3, 4, 5, 6, 7, 8 , 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26,
            27, 28, 29, 30, 31};

    final Integer[] years = {2011, 2012, 2013, 2014, 2015, 2016, 2017, 2018, 2019, 2020};

    final String[] numberOfGameInCurrentSeries = {"Game 1", "Game 2", "Game 3", "Game 4", "Game 5"};

    ScreenOverlayStack screenOverlayStack = ScreenOverlayStack.getScreenOverlayStack();

    public RecordGameMenu(Dimension windowDimension, MainWindow parentFrame){
        super(windowDimension, parentFrame);
        this.parentFrame = parentFrame;
        this.windowDimension = windowDimension;



        this.setLayout(new FlowLayout());

        ButtonListener buttonListener = new ButtonListener();
        ComboBoxListener comboBoxListener = new ComboBoxListener(majorRegions);


        regionComboBox = new JComboBox<String>(majorRegions);
        regionComboBox.addActionListener(comboBoxListener);

        dayComboBox = new JComboBox<>(days);
        monthComboBox = new JComboBox<>(months);
        yearComboBox = new JComboBox<>(years);

        gameNumber = new JComboBox<>(numberOfGameInCurrentSeries);


        convertDateToComboBoxes(currentDate);



        dateEntryBox = new JTextField(sqlDate.toString());

        goToRecordingMapWindow = new JButton("Ok");
        goToRecordingMapWindow.addActionListener(buttonListener);


        this.add(regionComboBox);

        backButton = new JButton("Back");
        backButton.addActionListener(buttonListener);
        this.add(backButton);

        screenOverlayStack.push(this);


    }

    private void convertDateToComboBoxes(String date){
        int year = Integer.parseInt(date.substring(0, 4));
        int day = Integer.parseInt(date.substring(9));
        int month = Integer.parseInt(date.substring(5, 7));

        yearComboBox.setSelectedItem((Integer)year);
        dayComboBox.setSelectedItem((Integer)day);
        monthComboBox.setSelectedIndex(month - 1);
    }

    private Date convertStringToSqlDate(String month, String day, String year){
        String formattedDate;
        String formattedDay, formattedMonth = null;

        //converting the String month into a numeric format to be later put in db
        for(int i = 0; i < months.length; i++){
            if(months[i].equals(month)){
                int numericMonth = i + 1;
                formattedMonth = Integer.toString(numericMonth);
                break;
            }
        }

        if(Integer.parseInt(day) < 10){
            formattedDay = "0" + day;
        }else {
            formattedDay = day;
        }


        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM-dd-yyyy");
        formattedDate = formattedMonth + "-" + formattedDay + "-" + year;

        java.util.Date toBeConverted = null;
        try{
            toBeConverted = simpleDateFormat.parse(formattedDate);
        }catch(Exception exception){
            System.out.println(exception + " : RecordGameMenu convertStringToSqlDate");
        }

        Date dateOfGame = new Date(toBeConverted.getTime());
        return dateOfGame;
    }

    private class ButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e){
            if(e.getSource() == goToRecordingMapWindow){

                String year = yearComboBox.getSelectedItem().toString();
                String month = monthComboBox.getSelectedItem().toString();
                String day = dayComboBox.getSelectedItem().toString();


                teamName = teamNameFromDb.getSelectedItem().toString();
                teamRegion = regionComboBox.getSelectedItem().toString();

                numOfGameInCurrentSeries = gameNumber.getSelectedIndex() + 1;

                Date sqlDate = convertStringToSqlDate(month, day, year);
                parentFrame.removeCurrentScreenOverlay();
                try{
                    startRecordingOverlay = new StartRecordingOverlay(teamName, teamRegion, sqlDate, numOfGameInCurrentSeries,
                            windowDimension, parentFrame);
                }catch (Exception exception){
                    System.out.println("Recording Map Menu\n" + exception);
                }
                parentFrame.addOverlay(startRecordingOverlay);

            }else if (e.getSource() == backButton){
                try{
                    remove(teamNameFromDb);
                }catch (Exception exception){}
                try{
                    remove(monthComboBox);
                }catch (Exception exception){}
                try{
                    remove(dayComboBox);
                }catch (Exception exception){}
                try{
                    remove(yearComboBox);
                }catch (Exception exception){}
                try{
                    remove(gameNumber);
                }catch (Exception exception){}
                parentFrame.removeCurrentScreenOverlay();
                parentFrame.addOverlay(screenOverlayStack.pop());
            }
        }
    }

    private class ComboBoxListener implements ActionListener {

        String[] majorRegions;

        public ComboBoxListener(String[] majorRegions){
            this.majorRegions = majorRegions;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (regionComboBox.getSelectedItem() != regionComboBox.getItemAt(0)){
                try{
                    remove(teamNameFromDb);
                }catch (Exception exception){}

                try{
                    listOfTeams = DatabaseManager.getListOfTeamsByRegion(regionComboBox.getSelectedItem().toString());
                }catch (Exception exception){
                    System.out.println(exception + " : RecordGameMenu ComboBoxListener actionPerformed");
                }

                teamList = new String[listOfTeams.size()];
                teamList = listOfTeams.toArray(teamList);
                teamNameFromDb = new JComboBox<>(teamList);
                remove(backButton);

                add(teamNameFromDb);
                add(monthComboBox);
                add(dayComboBox);
                add(yearComboBox);
                add(gameNumber);
                add(goToRecordingMapWindow);
                add(backButton);

                parentFrame.validate();
                parentFrame.repaint();
            } else {
                //ToDo
            }

        }
    }
}
