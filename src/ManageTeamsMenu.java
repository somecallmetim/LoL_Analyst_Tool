import javafx.scene.control.ComboBox;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by timbauer on 2/15/16.
 */
public class ManageTeamsMenu extends TransparentOverlayBaseClass {

    JButton addTeam, dropTeam, backToMainMenu, clearWithoutSaving, saveTeamData;
    JTextField teamNameTextBox;
    JComboBox<String> teamRegionComboBox;

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


        teamRegionComboBox = new JComboBox<String>(majorRegions);
        teamRegionComboBox.addActionListener(comboBoxListener);
        this.add(teamRegionComboBox);

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
        remove(teamNameTextBox);
        remove(saveTeamData);
        remove(clearWithoutSaving);

        add(teamRegionComboBox);
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

                remove(addTeam);
                remove(dropTeam);
                remove(backToMainMenu);

                add(teamNameTextBox);
                add(saveTeamData);
                add(clearWithoutSaving);

                parentFrame.validate();
                parentFrame.repaint();

            }else if (e.getSource() == dropTeam){

                remove(addTeam);
                remove(dropTeam);
                remove(backToMainMenu);

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
                if(!teamNameTextBox.getText().trim().equals("")){
                    teamName = teamNameTextBox.getText();

                    region = teamRegionComboBox.getSelectedItem().toString();

                    try{
                        addTeamToDatabase(teamName, region);
                    }catch (Exception exception){
                        System.out.println(exception + " ManageTeamsMenu error in ButtonListener at saveTeamData");
                    }

                    setScreenToAddOrDropTeam();
                }else{
                    JOptionPane.showMessageDialog(parentFrame, "Please Enter a Team Name");
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
            if (teamRegionComboBox.getSelectedItem() != teamRegionComboBox.getItemAt(0)){
                dropTeam.setEnabled(true);
                addTeam.setEnabled(true);
            } else {
                dropTeam.setEnabled(false);
                addTeam.setEnabled(false);
            }

        }
    }
}
