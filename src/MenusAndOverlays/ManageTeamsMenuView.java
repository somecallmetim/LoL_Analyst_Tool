package MenusAndOverlays;


import DataManagement.ScreenOverlayStack;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 * Created by timbauer on 2/15/16.
 */
public class ManageTeamsMenuView extends TransparentOverlayBaseClassView {

    JButton addTeamButton, dropTeamButton, backToMainMenuButton, clearWithoutSavingButton, saveTeamDataButton;
    JTextField teamNameTextBox;
    JComboBox<String> regionComboBox, listOfTeamsComboBox;


    Dimension buttonSize = new Dimension(180, 30);


    ScreenOverlayStack screenOverlayStack = ScreenOverlayStack.getScreenOverlayStack();



    public ManageTeamsMenuView(){
        super();

        regionComboBox = new JComboBox<String>();

        teamNameTextBox = new JTextField("Team Name");

        listOfTeamsComboBox = new JComboBox<>();


        addTeamButton = new JButton("Add Team");
        addTeamButton.setEnabled(false);


        dropTeamButton = new JButton("Drop Team");
        dropTeamButton.setEnabled(false);

        backToMainMenuButton = new JButton("Main Menu");
        clearWithoutSavingButton = new JButton("Exit Without Saving");
        saveTeamDataButton = new JButton("Save Changes");


        screenOverlayStack.push(this);

    }

    //..............Methods used to set ActionListeners for various components..............//
    public void addRegionComboBoxActionListener(ActionListener regionComboBoxActionListener){
        regionComboBox.addActionListener(regionComboBoxActionListener);
    }

    public void addAddTeamButtonActionListener(ActionListener addTeamButtonActionListener){
        addTeamButton.addActionListener(addTeamButtonActionListener);
    }

    public void addDropTeamButtonActionListener(ActionListener dropTeamButtonActionListener){
        dropTeamButton.addActionListener(dropTeamButtonActionListener);
    }

    public void addBackToMainMenuButtonActionListener(ActionListener backToMainMenuButtonActionListener){
        backToMainMenuButton.addActionListener(backToMainMenuButtonActionListener);
    }

    public void addClearWithoutSavingButtonActionListener(ActionListener clearWithoutSavingButtonActionListener){
        clearWithoutSavingButton.addActionListener(clearWithoutSavingButtonActionListener);
    }

    public void addSaveTeamDataButtonActionListener(ActionListener saveTeamDataActionListener){
        saveTeamDataButton.addActionListener(saveTeamDataActionListener);
    }

    //..............Methods used to get data from various components..............//
    public String getTeamNameTextBoxValue(){ return teamNameTextBox.getText(); }

    public String getRegionComboBoxSelectedItem(){ return regionComboBox.getSelectedItem().toString(); }

    public String getListOfTeamsComboBoxSelectedItem(){ return  listOfTeamsComboBox.getSelectedItem().toString(); }


    //..............Methods used to set data for various components..............//
    public void setRegionComboBox(String[] listOfRegions) {
        this.regionComboBox.setModel(new DefaultComboBoxModel<String>(listOfRegions));
    }

    public void setListOfTeamsComboBox(String[] listOfTeams) {
        this.listOfTeamsComboBox.setModel(new DefaultComboBoxModel<String>(listOfTeams));
    }

    public void setSaveTeamDataButtonText(String buttonText){
        saveTeamDataButton.setText(buttonText);
    }

    //..............Methods used to get specific components from menu..............//
    public JButton getAddTeamButton() { return addTeamButton; }

    public JButton getDropTeamButton() { return dropTeamButton; }

    public JButton getBackToMainMenuButton() { return backToMainMenuButton; }

    public JButton getClearWithoutSavingButton() { return clearWithoutSavingButton; }

    public JButton getSaveTeamDataButton() { return saveTeamDataButton; }

    public JTextField getTeamNameTextBox() { return teamNameTextBox; }

    public JComboBox<String> getRegionComboBox() { return regionComboBox; }

    public JComboBox<String> getListOfTeamsComboBox() { return listOfTeamsComboBox; }
}
