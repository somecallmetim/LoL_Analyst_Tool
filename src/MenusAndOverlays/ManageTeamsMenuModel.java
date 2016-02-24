package MenusAndOverlays;

import DataManagement.DatabaseManager;

import javax.swing.*;
import java.util.ArrayList;

/**
 * Created by timbauer on 2/23/16.
 */
public class ManageTeamsMenuModel {

    ArrayList<String> listOfTeams;
    String[] teams;
    String[] majorRegions = {"Please Select A Region", "NA LCS", "EU LCS", "LCK (Korea)", "LPL (China)", "LMS (Taiwan)",
            "IWC (International Wild Card)"};
    MainWindow parentFrame = MainWindow.getMainWindow();

    String region;


    public void addTeamToDatabase(String team) throws Exception{

        boolean isTeamAlreadyEntered = DatabaseManager.checkIfTeamIsEntered(team);

        if(!isTeamAlreadyEntered){
            DatabaseManager.addNewTeam(team, region);
        } else {
            JOptionPane.showMessageDialog(parentFrame, "Team Already Exists in Database");
        }
    }

    public void dropTeamFromDatabase(String teamName) throws Exception{
        DatabaseManager.removeTeamFromDatabase(teamName);
    }

    public String[] getListOfTeamsFromRegion() throws Exception{
        listOfTeams = DatabaseManager.getListOfTeamsByRegion(region);
        teams = new String[listOfTeams.size()];
        teams = listOfTeams.toArray(teams);
        return teams;
    }

    public String[] getMajorRegions(){
        return majorRegions;
    }

    public void setRegion(String region) {
        this.region = region;
    }
}
