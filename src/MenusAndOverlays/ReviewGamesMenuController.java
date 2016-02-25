package MenusAndOverlays;

import DataManagement.DatabaseManager;
import DataManagement.ScreenOverlayStack;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;

/**
 * Created by timbauer on 2/24/16.
 */
public class ReviewGamesMenuController extends TransparentOverlayBaseClassController {

    ReviewGamesMenuView reviewGamesMenuView;
    ReviewGamesMenuModel reviewGamesMenuModel;
    
    Component regionComboBox, teamListComboBox, gameSeriesComboBox, dateComboBox, getTeamGameDatesButton, 
            getTeamGameSeriesButton, goToReviewGameOverlayButton, backButton, startOverButton;

    ComboBoxListener comboBoxListener;
    ButtonListener buttonListener;

    ScreenOverlayStack screenOverlayStack = ScreenOverlayStack.getScreenOverlayStack();

    public ReviewGamesMenuController(ReviewGamesMenuView reviewGamesMenuView, ReviewGamesMenuModel reviewGamesMenuModel){

        this.reviewGamesMenuView = reviewGamesMenuView;
        this.reviewGamesMenuModel = reviewGamesMenuModel;

        regionComboBox = reviewGamesMenuView.getRegionComboBox();
        teamListComboBox = reviewGamesMenuView.getTeamListComboBox();
        gameSeriesComboBox = reviewGamesMenuView.getGameSeriesComboBox();
        dateComboBox = reviewGamesMenuView.getDateComboBox();
        getTeamGameDatesButton = reviewGamesMenuView.getGetTeamGameDatesButton();
        getTeamGameSeriesButton = reviewGamesMenuView.getGetTeamGameSeriesButton();
        goToReviewGameOverlayButton = reviewGamesMenuView.getGoToReviewGameOverlayButton();
        backButton = reviewGamesMenuView.getBackButton();
        startOverButton = reviewGamesMenuView.getStartOverButton();

        comboBoxListener = new ComboBoxListener();
        reviewGamesMenuView.addRegionComboBoxActionListener(comboBoxListener);

        buttonListener = new ButtonListener();
        reviewGamesMenuView.addGetTeamDatesButtonActionListener(buttonListener);
        reviewGamesMenuView.addGetTeamGameSeriesButtonActionListener(buttonListener);
        reviewGamesMenuView.addBackButtonActionListener(buttonListener);
        reviewGamesMenuView.addStartOverButtonActionListener(buttonListener);
        reviewGamesMenuView.addGoToReviewGameOverlayButtonActionListener(buttonListener);

        parentFrame.addOverlay(reviewGamesMenuView);
        screenOverlayStack.push(reviewGamesMenuView);

        onInit();
    }

    public void onInit(){
        componentsToRemove.add(regionComboBox);
        componentsToRemove.add(teamListComboBox);
        componentsToRemove.add(gameSeriesComboBox);
        componentsToRemove.add(dateComboBox);
        componentsToRemove.add(getTeamGameDatesButton);
        componentsToRemove.add(getTeamGameSeriesButton);
        componentsToRemove.add(goToReviewGameOverlayButton);
        componentsToRemove.add(backButton);
        componentsToRemove.add(startOverButton);

        removeComponentsFromView(reviewGamesMenuView, componentsToRemove);

        componentsToAdd.add(regionComboBox);
        componentsToAdd.add(backButton);

        reviewGamesMenuView.setRegionComboBox(reviewGamesMenuModel.getMajorRegions());

        addComponentsToView(reviewGamesMenuView, componentsToAdd);

        componentsToRemove.clear();
        componentsToAdd.clear();
    }
    
    public void setSelectTeamState(){
        componentsToRemove.add(regionComboBox);
        componentsToRemove.add(backButton);

        removeComponentsFromView(reviewGamesMenuView, componentsToRemove);

        String region = reviewGamesMenuView.getRegionComboBoxSelectedItem();
        try{
            reviewGamesMenuView.setTeamListComboBox(reviewGamesMenuModel.getListOfTeamsFromRegion(region));
        }catch (Exception exception){
            JOptionPane.showMessageDialog(parentFrame, "There was a problem retrieving the teams from the specified region");
        }

        componentsToAdd.add(teamListComboBox);
        componentsToAdd.add(getTeamGameDatesButton);
        componentsToAdd.add(startOverButton);

        addComponentsToView(reviewGamesMenuView, componentsToAdd);

        componentsToRemove.clear();
        componentsToAdd.clear();
    }
    
    public void setSelectGameDateState(String teamName){
        componentsToRemove.add(teamListComboBox);
        componentsToRemove.add(getTeamGameDatesButton);
        componentsToRemove.add(startOverButton);

        removeComponentsFromView(reviewGamesMenuView, componentsToRemove);

        componentsToAdd.add(dateComboBox);
        componentsToAdd.add(getTeamGameSeriesButton);
        componentsToAdd.add(startOverButton);

        try{
            reviewGamesMenuView.setDateComboBox(reviewGamesMenuModel.getTeamGameDates(teamName));
            ;
        }catch (Exception exeption){
            JOptionPane.showMessageDialog(parentFrame, "There was a problem getting dates for the team selected");
        }

        addComponentsToView(reviewGamesMenuView, componentsToAdd);

        componentsToRemove.clear();
        componentsToAdd.clear();
    }

    public void setSelectSeriesNumberState(Date gameDate){
        componentsToRemove.add(dateComboBox);
        componentsToRemove.add(getTeamGameSeriesButton);
        componentsToRemove.add(startOverButton);

        removeComponentsFromView(reviewGamesMenuView, componentsToRemove);

        componentsToAdd.add(gameSeriesComboBox);
        componentsToAdd.add(goToReviewGameOverlayButton);
        componentsToAdd.add(startOverButton);

        try {
            reviewGamesMenuView.setGameSeriesComboBox(reviewGamesMenuModel.getSeriesList(gameDate));
        }catch (Exception exception){
            JOptionPane.showMessageDialog(parentFrame, "There was a problem getting the game series for the selected date");
        }

        addComponentsToView(reviewGamesMenuView, componentsToAdd);

        componentsToRemove.clear();
        componentsToAdd.clear();
    }

    private class ComboBoxListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (reviewGamesMenuView.getRegionComboBoxSelectedItem() != reviewGamesMenuView.getRegionComboBox().getItemAt(0)) {

                setSelectTeamState();
                parentFrame.validate();
                parentFrame.repaint();
            } else {
                //ToDo
            }

        }
    }

    private class ButtonListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            if(e.getSource() == backButton){
                parentFrame.removeCurrentScreenOverlay();
                parentFrame.addOverlay(screenOverlayStack.pop());
            }else if(e.getSource() == startOverButton){
                onInit();
            }else if(e.getSource() == getTeamGameDatesButton){
                String teamName = reviewGamesMenuView.getTeamListComboBoxSelectedItem();
                reviewGamesMenuModel.setSelectedTeam(teamName);
                setSelectGameDateState(teamName);
            }else if(e.getSource() == getTeamGameSeriesButton){
                Date gameDate = reviewGamesMenuView.getDateComboBoxSelectedItem();
                reviewGamesMenuModel.setSelectedDate(gameDate);
                setSelectSeriesNumberState(gameDate);
            }else if(e.getSource() == goToReviewGameOverlayButton){

                String gameId = DatabaseManager.convertDataToGameId(reviewGamesMenuModel.getSelectedTeam(), reviewGamesMenuModel.getSelectedDate(),
                        reviewGamesMenuView.getGameSeriesComboBoxSelectedIndex() + 1);

                onInit();

                try{
                    ReviewGameOverlay reviewGameOverlay = new ReviewGameOverlay(gameId);
                    parentFrame.removeCurrentScreenOverlay();
                    parentFrame.removeOverlay(reviewGamesMenuView);
                    parentFrame.addOverlay(reviewGameOverlay);
                }catch (Exception exception){
                    JOptionPane.showMessageDialog(parentFrame, "There was a problem retrieving the specified game");
                    System.out.println(exception + " : There was a problem retrieving the specified game");
                    onInit();
                }
            }
        }
    }

    /*
    private class ButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e){
            if (e.getSource() == backButton){
                parentFrame.removeCurrentScreenOverlay();
                parentFrame.addOverlay(screenOverlayStack.pop());
            }else if (e.getSource() == getTeamGameDatesButton){
                selectedTeam = teamListComboBox.getSelectedItem().toString();
                try{
                    listOfGameDates = DatabaseManager.getTeamGameDatesButton(selectedTeam);
                }catch (Exception exception){
                    System.out.println(exception + " : MenusAndOverlays.ReviewGamesMenuView ButtonListener getTeamGameDatesButton");
                }
                teamGameDates = new Date[listOfGameDates.size()];
                teamGameDates = listOfGameDates.toArray(teamGameDates);
                dateComboBox = new JComboBox<>(teamGameDates);

                remove(teamListComboBox);
                remove(getTeamGameDatesButton);
                remove(backButton);

                add(dateComboBox);
                add(getTeamGameSeriesButton);
                add(backButton);

                parentFrame.validate();
                parentFrame.repaint();
            }else if(e.getSource() == getTeamGameSeriesButton){

                selectedDate = (Date)dateComboBox.getSelectedItem();

                try{
                    listOfGameSeries = DatabaseManager.getTeamGameSeriesButton(selectedTeam, selectedDate);
                }catch(Exception exception){
                    System.out.println(exception + " : MenusAndOverlays.ReviewGamesMenuView ButtonListener getTeamGameSeriesButton");
                }

                seriesList = new String[listOfGameSeries.size()];
                seriesList = listOfGameSeries.toArray(seriesList);
                gameSeriesComboBox = new JComboBox<>(seriesList);

                remove(dateComboBox);
                remove(getTeamGameSeriesButton);
                remove(backButton);

                add(gameSeriesComboBox);
                add(goToReviewGameOverlayButton);
                add(backButton);

                parentFrame.validate();
                parentFrame.repaint();

            }else if(e.getSource() == goToReviewGameOverlayButton){

                remove(gameSeriesComboBox);
                remove(goToReviewGameOverlayButton);
                remove(backButton);
                selectedGameInSeries = gameSeriesComboBox.getSelectedIndex() + 1;
                String gameId = DatabaseManager.convertDataToGameId(selectedTeam, selectedDate, selectedGameInSeries);
                try{
                    reviewGameOverlay = new ReviewGameOverlay(gameId);
                }catch (Exception exception){
                    System.out.println(exception + " : MenusAndOverlays.ReviewGamesMenuView ButtonListener goToReviewGameOverlayButton");
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
                    System.out.println(exception + " : MenusAndOverlays.ReviewGamesMenuView RegionComboBoxListener actionPerformed");
                }

                try{
                    remove(regionComboBox);
                }catch (Exception exception){}

                remove(backButton);

                teamList = new String[listOfTeams.size()];
                teamList = listOfTeams.toArray(teamList);
                teamListComboBox = new JComboBox<>(teamList);
                add(teamListComboBox);

                add(getTeamGameDatesButton);
                add(backButton);

                parentFrame.validate();
                parentFrame.repaint();
            } else {
                //ToDo
            }

        }
    }
    */
}
