package MenusAndOverlays;

import DataManagement.DatabaseManager;

import java.sql.Date;
import java.util.ArrayList;

/**
 * Created by timbauer on 2/24/16.
 */
public class ReviewGamesMenuModel extends TransparentOverlyBaseClassModel {
    ArrayList<String> listOfTeams, listOfGameSeries;
    ArrayList<Date> listOfGameDates;
    Date[] teamGameDates;
    Date selectedDate;
    String[] teams, seriesList;

    String selectedTeam;
    int selectedGameInSeries;
    final String[] majorRegions = {"Please Select A Region", "NA LCS", "EU LCS", "LCK (Korea)", "LPL (China)",
            "LMS (Taiwan)", "IWC (International Wild Card)"};

    public String[] getMajorRegions() {
        return majorRegions;
    }

    public String[] getListOfTeamsFromRegion(String region) throws Exception{
        listOfTeams = DatabaseManager.getListOfTeamsByRegion(region);
        teams = new String[listOfTeams.size()];
        teams = listOfTeams.toArray(teams);
        return teams;
    }

    public Date[] getTeamGameDates(String teamName) throws Exception{
        listOfGameDates = DatabaseManager.getTeamGameDates(teamName);
        teamGameDates = new Date[listOfGameDates.size()];
        teamGameDates = listOfGameDates.toArray(teamGameDates);
        return teamGameDates;
    }

    public String[] getSeriesList(Date gameDate)throws Exception{
        listOfGameSeries = DatabaseManager.getTeamGameSeries(selectedTeam, gameDate);
        seriesList = new String[listOfGameSeries.size()];
        seriesList = listOfGameSeries.toArray(seriesList);
        return seriesList;
    }

    public String getSelectedTeam() {
        return selectedTeam;
    }

    public void setSelectedTeam(String selectedTeam) {
        this.selectedTeam = selectedTeam;
    }

    public Date getSelectedDate() {
        return selectedDate;
    }

    public void setSelectedDate(Date selectedDate) {
        this.selectedDate = selectedDate;
    }
}
