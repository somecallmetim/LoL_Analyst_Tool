package MenusAndOverlays;

import DataManagement.DatabaseManager;
import DataManagement.ScreenOverlayStack;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * Created by timbauer on 2/15/16.
 */
public class ManageTeamsMenu extends TransparentOverlayBaseClass {

    JButton addTeam, dropTeam, backToMainMenu, clearWithoutSaving, saveTeamData;
    JTextField teamNameTextBox;
    JComboBox<String> regionComboBox, listOfTeamsComboBox;

    ArrayList<String> listOfTeams;

    JPanel recordingMapMenu, reviewGamesMenu;
    Dimension buttonSize = new Dimension(180, 30);
    Dimension windowDimension;
    MainWindow parentFrame;


    boolean entryModeIsSetForAddingATeam = true;


    ScreenOverlayStack screenOverlayStack = ScreenOverlayStack.getScreenOverlayStack();



    public ManageTeamsMenu(Dimension windowDimension, MainWindow parentFrame){
        super(windowDimension, parentFrame);
        this.windowDimension = windowDimension;
        this.parentFrame = parentFrame;


        String[] majorRegions = {"Please Select A Region", "NA LCS", "EU LCS", "LCK (Korea)", "LPL (China)", "LMS (Taiwan)", "IWC (International Wild Card)"};


        ButtonListener buttonListener = new ButtonListener();
        ComboBoxListener comboBoxListener = new ComboBoxListener(majorRegions);



        regionComboBox = new JComboBox<String>(majorRegions);
        regionComboBox.addActionListener(comboBoxListener);
        this.add(regionComboBox);

        teamNameTextBox = new JTextField("Team Name");


        addTeam = new JButton("Add Team");
        addTeam.addActionListener(buttonListener);
        addTeam.setPreferredSize(buttonSize);
        addTeam.setEnabled(false);
        this.add(addTeam);



        dropTeam = new JButton("Drop Team");
        dropTeam.addActionListener(buttonListener);
        dropTeam.setPreferredSize(buttonSize);
        dropTeam.setEnabled(false);
        this.add(dropTeam);

        backToMainMenu = new JButton("Main Menu");
        backToMainMenu.addActionListener(buttonListener);
        this.add(backToMainMenu);

        clearWithoutSaving = new JButton("Exit Without Saving");
        clearWithoutSaving.addActionListener(buttonListener);

        saveTeamData = new JButton("Save Changes");
        saveTeamData.addActionListener(buttonListener);

        screenOverlayStack.push(this);

    }

    private void addTeamToDatabase(String team, String teamRegion) throws Exception{

        boolean isTeamAlreadyEntered = DatabaseManager.checkIfTeamIsEntered(team);

        if(!isTeamAlreadyEntered){
            DatabaseManager.addNewTeam(team, teamRegion);
        } else {
            JOptionPane.showMessageDialog(parentFrame, "Team Already Exists in Database");
        }
    }

    private void setScreenToAddOrDropTeam(){

        //Didn't want program crashing if one of these components wasn't currently on
        // the screen. Not the best solution, but it does keep things rolling
        try{
            remove(teamNameTextBox);
        }catch (Exception exception){}
        try{
            remove(saveTeamData);
        }catch (Exception exception){}
        try{
            remove(clearWithoutSaving);
        }catch (Exception exception){}
        try{
            remove(listOfTeamsComboBox);
        }catch (Exception exception){}


        add(regionComboBox);
        add(addTeam);
        add(dropTeam);
        add(backToMainMenu);

        parentFrame.validate();
        parentFrame.repaint();
    }

    private class ButtonListener implements ActionListener {

        String teamName, region;

        @Override
        public void actionPerformed(ActionEvent e){
            if(e.getSource() == addTeam){

                entryModeIsSetForAddingATeam = true;

                remove(addTeam);
                remove(dropTeam);
                remove(backToMainMenu);

                teamNameTextBox.setText("Team Name");
                saveTeamData.setText("Add Team");

                add(teamNameTextBox);
                add(saveTeamData);
                add(clearWithoutSaving);

                parentFrame.validate();
                parentFrame.repaint();

            }else if (e.getSource() == dropTeam){

                entryModeIsSetForAddingATeam = false;
                region = regionComboBox.getSelectedItem().toString();

                try{
                    listOfTeams = DatabaseManager.getListOfTeamsByRegion(region);
                }catch (Exception exception){
                    System.out.println(exception + " problem in MenusAndOverlays.ManageTeamsMenu in ButtonListener at dropTeam");
                }

                String[] teamList = new String[listOfTeams.size()];
                
                teamList = listOfTeams.toArray(teamList);

                listOfTeamsComboBox = new JComboBox<>(teamList);

                remove(addTeam);
                remove(dropTeam);
                remove(backToMainMenu);

                saveTeamData.setText("Delete Team");

                add(listOfTeamsComboBox);
                add(saveTeamData);
                add(clearWithoutSaving);

                parentFrame.validate();
                parentFrame.repaint();

            }else if (e.getSource() == backToMainMenu){

                parentFrame.removeCurrentScreenOverlay();
                parentFrame.addOverlay(screenOverlayStack.pop());

            }else if (e.getSource() == clearWithoutSaving){

                setScreenToAddOrDropTeam();
            }else if (e.getSource() == saveTeamData){

                region = regionComboBox.getSelectedItem().toString();

                if(region.equals("Please Select A Region")){
                    JOptionPane.showMessageDialog(parentFrame, "Please Select A Region");
                }else {
                    if(entryModeIsSetForAddingATeam){

                        if(!teamNameTextBox.getText().trim().equals("")){
                            teamName = teamNameTextBox.getText();
                            try{
                                addTeamToDatabase(teamName, region);
                            }catch (Exception exception){
                                System.out.println(exception + " MenusAndOverlays.ManageTeamsMenu error in ButtonListener at saveTeamData");
                            }

                            setScreenToAddOrDropTeam();
                        }else{
                            JOptionPane.showMessageDialog(parentFrame, "Please Enter a Team Name");
                        }

                    }else {
                        try{
                            DatabaseManager.removeTeamFromRegion(listOfTeamsComboBox.getSelectedItem().toString());
                        }catch (Exception exception){
                            System.out.println(exception + " : MenusAndOverlays.ManageTeamsMenu saveTeamData removeTeamFromRegion");
                        }
                        setScreenToAddOrDropTeam();
                    }
                }

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
                dropTeam.setEnabled(true);
                addTeam.setEnabled(true);
            } else {
                dropTeam.setEnabled(false);
                addTeam.setEnabled(false);
            }

        }
    }
}
