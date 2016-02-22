package MenusAndOverlays;

import DataManagement.ScreenOverlayStack;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;


/**
 * Created by timbauer on 12/27/15.
 */
public class RecordGameMenuView extends TransparentOverlayBaseClassView {

    private JComboBox<String> regionComboBox, teamNameComboBox, monthComboBox, gameNumber;
    private JComboBox<Integer> dayComboBox, yearComboBox;
    private JButton goToRecordingMapWindowButton, backButton;


    ScreenOverlayStack screenOverlayStack = ScreenOverlayStack.getScreenOverlayStack();

    public RecordGameMenuView(){
        super();

        this.setLayout(new FlowLayout());


        regionComboBox = new JComboBox<>();
        teamNameComboBox = new JComboBox<>();

        dayComboBox = new JComboBox<>();
        monthComboBox = new JComboBox<>();
        yearComboBox = new JComboBox<>();

        gameNumber = new JComboBox<>();

        goToRecordingMapWindowButton = new JButton("Ok");
        backButton = new JButton("Back");

        screenOverlayStack.push(this);
    }

    public void addRegionComboBoxActionListener(ActionListener regionComboBoxActionListener){
        regionComboBox.addActionListener(regionComboBoxActionListener);
    }

    public void addGoToRecordingMapWindowButtonActionListener(ActionListener goToRecordingMapWindowButtonActionlistener){
        goToRecordingMapWindowButton.addActionListener(goToRecordingMapWindowButtonActionlistener);
    }

    public void addBackButtonActionListener(ActionListener backButtonActionListener){
        backButton.addActionListener(backButtonActionListener);
    }

    //..............Methods used to get data from various components..............//
    public String getRegionComboBoxSelectedItem() {
        return regionComboBox.getSelectedItem().toString();
    }

    public String getTeamNameComboBoxSelectedItem() {
        return teamNameComboBox.getSelectedItem().toString();
    }

    public String getMonthComboBoxSelected() {
        return monthComboBox.getSelectedItem().toString();
    }

    public int getGameNumberSelected() {
        return gameNumber.getSelectedIndex() + 1;
    }

    public String getDayComboBoxSelectedItem() {
        return dayComboBox.getSelectedItem().toString();
    }

    public String getYearComboBoxSelectedItem() {
        return yearComboBox.getSelectedItem().toString();
    }


    //..............Methods used to set data for various components..............//
    public void setRegionComboBoxValues(String[] regions) {
        regionComboBox.setModel(new DefaultComboBoxModel<String>(regions));
    }

    public void setTeamNameComboBox(String[] teamsInSelectedRegion) {
        teamNameComboBox.setModel(new DefaultComboBoxModel<String>(teamsInSelectedRegion));
    }

    public void setMonthComboBox(String[] monthsOfTheYear) {
        monthComboBox.setModel(new DefaultComboBoxModel<String>(monthsOfTheYear));
    }

    public void setMonthComboBoxSelectedItem(String month){
        monthComboBox.setSelectedItem(month);
    }

    public void setDayComboBox(Integer[] daysOfTheMonth) {
        dayComboBox.setModel(new DefaultComboBoxModel<Integer>(daysOfTheMonth));
    }

    public void setDayComboBoxSelectedItem(Integer day){
        dayComboBox.setSelectedItem(day);
    }

    public void setYearComboBox(Integer[] listOfYears) {
        yearComboBox.setModel(new DefaultComboBoxModel<Integer>(listOfYears));
    }

    public void setYearComboBoxSelectedItem(Integer year){
        yearComboBox.setSelectedItem(year);
    }

    public void setGameNumber(String[] gameSeriesNumbers) {
        gameNumber.setModel(new DefaultComboBoxModel<String>(gameSeriesNumbers));
    }

    //..............Methods used to add/remove components to/from menu..............//
    public void addComponent(Component component){
        this.add(component);
        parentFrame.validate();
        parentFrame.repaint();
    }

    public void removeComponent(Component component){
        try{
            this.remove(component);
            parentFrame.validate();
            parentFrame.repaint();
        }catch (Exception exception){}
    }

    public void addComponents(Component[] components){
        for(int i = 0; i < components.length; i++) this.add(components[i]);
        parentFrame.validate();
        parentFrame.repaint();
    }

    public void removeComponents(Component[] components){

            for(int i = 0; i < components.length; i++) {
                try{
                    this.remove(components[i]);
                }catch (Exception exception){}
            }
        parentFrame.validate();
        parentFrame.repaint();
    }

    //..............Methods used to get specific components from menu..............//
    public JComboBox<String> getRegionComboBox() {
        return regionComboBox;
    }

    public JComboBox<String> getTeamNameComboBox() {
        return teamNameComboBox;
    }

    public JComboBox<String> getMonthComboBox() {
        return monthComboBox;
    }

    public JComboBox<String> getGameNumber() {
        return gameNumber;
    }

    public JComboBox<Integer> getDayComboBox() {
        return dayComboBox;
    }

    public JComboBox<Integer> getYearComboBox() {
        return yearComboBox;
    }

    public JButton getGoToRecordingMapWindowButton() {
        return goToRecordingMapWindowButton;
    }

    public JButton getBackButton() {
        return backButton;
    }
/*
    //..............Methods used to add specific components to menu..............//
    public void addRegionComboBox(){
        this.add(regionComboBox);
    }

    public void addTeamNameComboBox(){
        this.add(teamNameComboBox);
    }

    public void addDayComboBox(){
        this.add(dayComboBox);
    }

    public void addMonthComboBox(){
        this.add(monthComboBox);
    }

    public void addYearComboBox(){
        this.add(yearComboBox);
    }

    public void addGameNumberComboBox(){
        this.add(gameNumber);
    }

    public void addGoToRecordingMapMenuButton(){
        this.add(goToRecordingMapWindowButton);
    }

    public void addBackButton(){
        this.add(backButton);
    }


    //..............Methods used to remove specific components from menu..............//
    public void removeRegionComboBox(){
        this.remove(regionComboBox);
    }

    public void removeTeamNameComboBox(){
        this.remove(teamNameComboBox);
    }

    public void removeDayComboBox(){
        this.remove(dayComboBox);
    }

    public void removeMonthComboBox(){
        this.remove(monthComboBox);
    }

    public void removeYearComboBox(){
        this.remove(yearComboBox);
    }

    public void removeGameNumberComboBox(){
        this.remove(gameNumber);
    }

    public void removeGoToRecordingMapMenuButton(){
        this.remove(goToRecordingMapWindowButton);
    }

    public void removeBackButton(){
        this.remove(backButton);
    }
    */
}
