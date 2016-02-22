package MenusAndOverlays;

import DataManagement.DatabaseManager;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by timbauer on 2/20/16.
 */
public class RecordGameMenuModel {

    Date sqlDate = new Date(System.currentTimeMillis());
    String currentDate = sqlDate.toString();

    final static String[] majorRegions = {"Please Select A Region", "NA LCS", "EU LCS", "LCK (Korea)", "LPL (China)",
            "LMS (Taiwan)", "IWC (International Wild Card)"};

    final static String[] months = {"January", "February", "March", "April", "May", "June", "July", "August", "September",
            "October", "November", "December"};

    final static Integer[] days = {1, 2, 3, 4, 5, 6, 7, 8 , 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26,
            27, 28, 29, 30, 31};

    final static Integer[] years = {2011, 2012, 2013, 2014, 2015, 2016, 2017, 2018, 2019, 2020};

    final static String[] numberOfGameInCurrentSeries = {"Game 1", "Game 2", "Game 3", "Game 4", "Game 5"};
    int gameNumber;
    
    static String[] teamsFromCurrentRegion;

    static HashMap<String, String> monthConversion = new HashMap<>();

    
    public RecordGameMenuModel(){
        
        monthConversion.put(months[0],  "01");
        monthConversion.put(months[1],  "02");
        monthConversion.put(months[2],  "03");
        monthConversion.put(months[3],  "04");
        monthConversion.put(months[4],  "05");
        monthConversion.put(months[5],  "06");
        monthConversion.put(months[6],  "07");
        monthConversion.put(months[7],  "08");
        monthConversion.put(months[8],  "09");
        monthConversion.put(months[9],  "10");
        monthConversion.put(months[10], "11");
        monthConversion.put(months[11], "12");

    }

    public String[] getMajorRegions() {
        return majorRegions;
    }

    public String[] getMonths() {
        return months;
    }

    public Integer[] getDays() {
        return days;
    }

    public Integer[] getYears() {
        return years;
    }

    public String[] getNumberOfGameInCurrentSeries() {
        return numberOfGameInCurrentSeries;
    }
    
    public String[] getTeamsFromCurrentRegion(String region) {
        ArrayList<String> listOfTeams;
        try {
            listOfTeams = DatabaseManager.getListOfTeamsByRegion(region);
            teamsFromCurrentRegion = new String[listOfTeams.size()];
            teamsFromCurrentRegion = listOfTeams.toArray(teamsFromCurrentRegion);
            return teamsFromCurrentRegion;
        } catch (Exception exception) {
            System.out.println(exception + " : MenusAndOverlays.RecordGameMenuView ComboBoxListener actionPerformed");
        }

        String[] problem = {"There was a problem"};
        return problem;
    }

    public Date convertStringToSqlDate(String month, String day, String year){
        String formattedDate;
        String formattedDay, formattedMonth;

        formattedMonth = monthConversion.get(month);

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
            System.out.println(exception + " : MenusAndOverlays.RecordGameMenuView convertStringToSqlDate");
        }

        Date dateOfGame = new Date(toBeConverted.getTime());
        return dateOfGame;
    }

    public String getCurrentMonth(){
        int month = Integer.parseInt(currentDate.substring(5, 7));
        return months[month - 1];
    }

    public Integer getCurrentDay(){
        Integer day = Integer.parseInt(currentDate.substring(8));
        return day;
    }

    public Integer getCurrentYear(){
        Integer year = Integer.parseInt(currentDate.substring(0, 4));
        return year;
    }

}
