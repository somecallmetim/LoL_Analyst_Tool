package MenusAndOverlays;

import DataManagement.ScreenOverlayStack;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.sql.Date;

/**
 * Created by timbauer on 12/27/15.
 */
public class ReviewGamesMenuView extends TransparentOverlayBaseClassView {



    JComboBox<String> regionComboBox, teamListComboBox, gameSeriesComboBox;
    JComboBox<Date> dateComboBox;
    JButton backButton, getTeamGameDatesButton, getTeamGameSeriesButton, goToReviewGameOverlayButton, startOverButton;



    public ReviewGamesMenuView(){

        regionComboBox = new JComboBox();
        teamListComboBox = new JComboBox<>();
        gameSeriesComboBox = new JComboBox<>();

        dateComboBox = new JComboBox<Date>();


        getTeamGameDatesButton = new JButton("Ok");
        getTeamGameSeriesButton = new JButton("Ok");
        goToReviewGameOverlayButton = new JButton("Ok");
        backButton = new JButton("Back");
        startOverButton = new JButton("Start Over");

        this.setLayout(new FlowLayout());
    }

    //..............Methods used to set ActionListeners for various components..............//
    public void addRegionComboBoxActionListener(ActionListener actionListener){
        regionComboBox.addActionListener(actionListener);
    }

    public void addGetTeamDatesButtonActionListener(ActionListener actionListener){
        getTeamGameDatesButton.addActionListener(actionListener);
    }

    public void addGetTeamGameSeriesButtonActionListener(ActionListener actionListener){
        getTeamGameSeriesButton.addActionListener(actionListener);
    }

    public void addGoToReviewGameOverlayButtonActionListener(ActionListener actionListener){
        goToReviewGameOverlayButton.addActionListener(actionListener);
    }

    public void addBackButtonActionListener(ActionListener actionListener){
        backButton.addActionListener(actionListener);
    }

    public void addStartOverButtonActionListener(ActionListener actionListener){
        startOverButton.addActionListener(actionListener);
    }


    //..............Methods used to get data from various components..............//
    public String getRegionComboBoxSelectedItem(){
        return regionComboBox.getSelectedItem().toString();
    }

    public String getTeamListComboBoxSelectedItem(){
        return teamListComboBox.getSelectedItem().toString();
    }

    public int getGameSeriesComboBoxSelectedIndex(){
        return gameSeriesComboBox.getSelectedIndex();
    }

    public Date getDateComboBoxSelectedItem(){
        return (Date)dateComboBox.getSelectedItem();
    }



    //..............Methods used to set data for various components..............//
    public void setRegionComboBox(String[] listOfRegions) {
        regionComboBox.setModel(new DefaultComboBoxModel<String>(listOfRegions));
    }

    public void setTeamListComboBox(String[] teamList){
        teamListComboBox.setModel(new DefaultComboBoxModel<String>(teamList));
    }

    public void setGameSeriesComboBox(String[] gameSeries){
        gameSeriesComboBox.setModel(new DefaultComboBoxModel<String>(gameSeries));
    }

    public void setDateComboBox(Date[] gameDates){
        dateComboBox.setModel(new DefaultComboBoxModel<Date>(gameDates));
    }


    //..............Methods used to get specific components from menu..............//
    public JComboBox<String> getRegionComboBox() {
        return regionComboBox;
    }

    public JComboBox<String> getTeamListComboBox() {
        return teamListComboBox;
    }

    public JComboBox<String> getGameSeriesComboBox() {
        return gameSeriesComboBox;
    }

    public JComboBox<Date> getDateComboBox() {
        return dateComboBox;
    }

    public JButton getBackButton() {
        return backButton;
    }

    public JButton getGetTeamGameDatesButton() {
        return getTeamGameDatesButton;
    }

    public JButton getGetTeamGameSeriesButton() {
        return getTeamGameSeriesButton;
    }

    public JButton getGoToReviewGameOverlayButton() {
        return goToReviewGameOverlayButton;
    }

    public JButton getStartOverButton() {
        return startOverButton;
    }
}

