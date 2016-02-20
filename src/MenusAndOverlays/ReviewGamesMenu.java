package MenusAndOverlays;

import DataManagement.DatabaseManager;
import DataManagement.ScreenOverlayStack;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.sql.Date;

/**
 * Created by timbauer on 12/27/15.
 */
public class ReviewGamesMenu extends TransparentOverlayBaseClassView {

    ArrayList<String>  listOfTeams, listOfGameSeries;
    ArrayList<Date> listOfGameDates;
    Date[] teamGameDates;
    Date selectedDate;
    String[] teamList, seriesList;
    String selectedTeam;
    int selectedGameInSeries;

    JComboBox<String> regionComboBox, teamListComboBox, gameSeriesComboBox;
    JComboBox<Date> dateComboBox;
    JButton backButton, getTeamGameDates, getTeamGameSeries, goToReviewGameOverlay;

    ButtonListener buttonListener;
    RegionComboBoxListener regionComboBoxListener;

    ReviewGameOverlay reviewGameOverlay;

    final String[] majorRegions = {"Please Select A Region", "NA LCS", "EU LCS", "LCK (Korea)", "LPL (China)",
            "LMS (Taiwan)", "IWC (International Wild Card)"};

    ScreenOverlayStack screenOverlayStack = ScreenOverlayStack.getScreenOverlayStack();

    public ReviewGamesMenu(){
        super();

        buttonListener = new ButtonListener();
        regionComboBoxListener = new RegionComboBoxListener(majorRegions);

        regionComboBox = new JComboBox(majorRegions);
        regionComboBox.addActionListener(regionComboBoxListener);

        getTeamGameDates = new JButton("Ok");
        getTeamGameDates.addActionListener(buttonListener);

        getTeamGameSeries = new JButton("Ok");
        getTeamGameSeries.addActionListener(buttonListener);

        goToReviewGameOverlay = new JButton("Ok");
        goToReviewGameOverlay.addActionListener(buttonListener);


        this.setLayout(new FlowLayout());
        this.add(regionComboBox);


        backButton = new JButton("Back");
        backButton.addActionListener(buttonListener);
        this.add(backButton);

        screenOverlayStack.push(this);

    }

    public void onRestart(){
        try{
            remove(regionComboBox);
        }catch (Exception exception){}
        try{
            remove(teamListComboBox);
        }catch (Exception exception){}
        try{
            remove(gameSeriesComboBox);
        }catch (Exception exception){}
        try{
            remove(dateComboBox);
        }catch (Exception exception){}
        try{
            remove(backButton);
        }catch (Exception exception){}
        try{
            remove(getTeamGameSeries);
        }catch (Exception exception){}
        try{
            remove(getTeamGameDates);
        }catch (Exception exception){}
        try{
            remove(goToReviewGameOverlay);
        }catch (Exception exception){}

        add(regionComboBox);
        add(backButton);

        parentFrame.validate();
        parentFrame.repaint();
    }

    private class ButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e){
            if (e.getSource() == backButton){
                parentFrame.removeCurrentScreenOverlay();
                parentFrame.addOverlay(screenOverlayStack.pop());
            }else if (e.getSource() == getTeamGameDates){
                selectedTeam = teamListComboBox.getSelectedItem().toString();
                try{
                    listOfGameDates = DatabaseManager.getTeamGameDates(selectedTeam);
                }catch (Exception exception){
                    System.out.println(exception + " : MenusAndOverlays.ReviewGamesMenu ButtonListener getTeamGameDates");
                }
                teamGameDates = new Date[listOfGameDates.size()];
                teamGameDates = listOfGameDates.toArray(teamGameDates);
                dateComboBox = new JComboBox<>(teamGameDates);

                remove(teamListComboBox);
                remove(getTeamGameDates);
                remove(backButton);

                add(dateComboBox);
                add(getTeamGameSeries);
                add(backButton);

                parentFrame.validate();
                parentFrame.repaint();
            }else if(e.getSource() == getTeamGameSeries){

                selectedDate = (Date)dateComboBox.getSelectedItem();

                try{
                    listOfGameSeries = DatabaseManager.getTeamGameSeries(selectedTeam, selectedDate);
                }catch(Exception exception){
                    System.out.println(exception + " : MenusAndOverlays.ReviewGamesMenu ButtonListener getTeamGameSeries");
                }

                seriesList = new String[listOfGameSeries.size()];
                seriesList = listOfGameSeries.toArray(seriesList);
                gameSeriesComboBox = new JComboBox<>(seriesList);

                remove(dateComboBox);
                remove(getTeamGameSeries);
                remove(backButton);

                add(gameSeriesComboBox);
                add(goToReviewGameOverlay);
                add(backButton);

                parentFrame.validate();
                parentFrame.repaint();

            }else if(e.getSource() == goToReviewGameOverlay){

                remove(gameSeriesComboBox);
                remove(goToReviewGameOverlay);
                remove(backButton);
                selectedGameInSeries = gameSeriesComboBox.getSelectedIndex() + 1;
                String gameId = DatabaseManager.convertDataToGameId(selectedTeam, selectedDate, selectedGameInSeries);
                try{
                    reviewGameOverlay = new ReviewGameOverlay(gameId);
                }catch (Exception exception){
                    System.out.println(exception + " : MenusAndOverlays.ReviewGamesMenu ButtonListener goToReviewGameOverlay");
                }


                parentFrame.removeCurrentScreenOverlay();
                parentFrame.addOverlay(reviewGameOverlay);
            }
        }
    }

    private class RegionComboBoxListener implements ActionListener {

        String[] majorRegions;

        public RegionComboBoxListener(String[] majorRegions){
            this.majorRegions = majorRegions;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (regionComboBox.getSelectedItem() != regionComboBox.getItemAt(0)){

                try{
                    listOfTeams = DatabaseManager.getListOfTeamsByRegion(regionComboBox.getSelectedItem().toString());
                }catch (Exception exception){
                    System.out.println(exception + " : MenusAndOverlays.ReviewGamesMenu RegionComboBoxListener actionPerformed");
                }

                try{
                    remove(regionComboBox);
                }catch (Exception exception){}

                remove(backButton);

                teamList = new String[listOfTeams.size()];
                teamList = listOfTeams.toArray(teamList);
                teamListComboBox = new JComboBox<>(teamList);
                add(teamListComboBox);

                add(getTeamGameDates);
                add(backButton);

                parentFrame.validate();
                parentFrame.repaint();
            } else {
                //ToDo
            }

        }
    }
}

