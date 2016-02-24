package MenusAndOverlays;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by timbauer on 2/23/16.
 */
public class ManageTeamsMenuController extends TransparentOverlayBaseClassController{

    ManageTeamsMenuView manageTeamsMenuView;
    ManageTeamsMenuModel manageTeamsMenuModel;

    Component addTeamButton, dropTeamButton, backToMainMenuButton, clearWithoutSavingButton,
            saveTeamDataButton, teamNameTextBox, regionComboBox, listOfTeamsComboBox;

    ComboBoxListener comboBoxListener;

    InitialStateButtonActionListener initialStateButtonActionListener;
    AddOrDropTeamStateActionListener addOrDropTeamStateActionListener;

    boolean addingTeam;


    public ManageTeamsMenuController(ManageTeamsMenuView manageTeamsMenuView, ManageTeamsMenuModel manageTeamsMenuModel){
        this.manageTeamsMenuView = manageTeamsMenuView;
        this.manageTeamsMenuModel = manageTeamsMenuModel;

        parentFrame.addOverlay(manageTeamsMenuView);

        comboBoxListener = new ComboBoxListener();

        this.addTeamButton = manageTeamsMenuView.getAddTeamButton();
        this.dropTeamButton = manageTeamsMenuView.getDropTeamButton();
        this.backToMainMenuButton = manageTeamsMenuView.getBackToMainMenuButton();
        this.clearWithoutSavingButton = manageTeamsMenuView.getClearWithoutSavingButton();
        this.saveTeamDataButton = manageTeamsMenuView.getSaveTeamDataButton();
        this.teamNameTextBox = manageTeamsMenuView.getTeamNameTextBox();
        this.regionComboBox = manageTeamsMenuView.getRegionComboBox();
        this.listOfTeamsComboBox = manageTeamsMenuView.getListOfTeamsComboBox();

        initialStateButtonActionListener = new InitialStateButtonActionListener();

        manageTeamsMenuView.addAddTeamButtonActionListener(initialStateButtonActionListener);
        manageTeamsMenuView.addDropTeamButtonActionListener(initialStateButtonActionListener);
        manageTeamsMenuView.addBackToMainMenuButtonActionListener(initialStateButtonActionListener);

        addOrDropTeamStateActionListener = new AddOrDropTeamStateActionListener();

        manageTeamsMenuView.addClearWithoutSavingButtonActionListener(addOrDropTeamStateActionListener);
        manageTeamsMenuView.addSaveTeamDataButtonActionListener(addOrDropTeamStateActionListener);

        onInit();
    }

    public void onInit(){

        componentsToRemove.clear();
        componentsToAdd.clear();

        componentsToRemove.add(addTeamButton);
        componentsToRemove.add(dropTeamButton);
        componentsToRemove.add(backToMainMenuButton);
        componentsToRemove.add(clearWithoutSavingButton);
        componentsToRemove.add(saveTeamDataButton);
        componentsToRemove.add(teamNameTextBox);
        componentsToRemove.add(regionComboBox);
        componentsToRemove.add(listOfTeamsComboBox);

        removeComponentsFromView(manageTeamsMenuView, componentsToRemove);

        componentsToAdd.add(regionComboBox);
        componentsToAdd.add(addTeamButton);
        componentsToAdd.add(dropTeamButton);
        componentsToAdd.add(backToMainMenuButton);

        addTeamButton.setEnabled(false);
        dropTeamButton.setEnabled(false);

        manageTeamsMenuView.setRegionComboBox(manageTeamsMenuModel.getMajorRegions());
        manageTeamsMenuView.addRegionComboBoxActionListener(comboBoxListener);

        addComponentsToView(manageTeamsMenuView, componentsToAdd);

        componentsToRemove.clear();
        componentsToAdd.clear();
    }

    public void goToAddOrDropTeamState(boolean addingTeam){

        this.addingTeam = addingTeam;

        componentsToRemove.add(regionComboBox);
        componentsToRemove.add(addTeamButton);
        componentsToRemove.add(dropTeamButton);
        componentsToRemove.add(backToMainMenuButton);

        removeComponentsFromView(manageTeamsMenuView, componentsToRemove);

        if(addingTeam){
            componentsToAdd.add(teamNameTextBox);
        }else {
            componentsToAdd.add(listOfTeamsComboBox);
            try{
                manageTeamsMenuView.setListOfTeamsComboBox(manageTeamsMenuModel.getListOfTeamsFromRegion());
            }catch (Exception exception){
                JOptionPane.showMessageDialog(parentFrame, "There was an issue getting the teams from the region");
                onInit();
            }
        }


        componentsToAdd.add(saveTeamDataButton);
        componentsToAdd.add(clearWithoutSavingButton);

        addComponentsToView(manageTeamsMenuView, componentsToAdd);

        componentsToRemove.clear();
        componentsToAdd.clear();
    }


    private class InitialStateButtonActionListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {

            manageTeamsMenuModel.setRegion(manageTeamsMenuView.getRegionComboBoxSelectedItem());

            if(e.getSource() == addTeamButton){

                goToAddOrDropTeamState(true);

            }else if(e.getSource() == dropTeamButton){
                
                goToAddOrDropTeamState(false);
                
            }else if(e.getSource() == backToMainMenuButton){
                parentFrame.removeCurrentScreenOverlay();
                parentFrame.addOverlay(screenOverlayStack.pop());
            }
        }
    }

    private class AddOrDropTeamStateActionListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            if(e.getSource() == clearWithoutSavingButton){
                onInit();
            }else if(e.getSource() == saveTeamDataButton){

                if(addingTeam){

                    try{
                        manageTeamsMenuModel.addTeamToDatabase(manageTeamsMenuView.getTeamNameTextBoxValue());
                    }catch (Exception exception){
                        System.out.println(exception + " : problem in ManageTeamsController AddOrDrop action listener saveTeamDataButton");
                        JOptionPane.showMessageDialog(parentFrame, "There was a problem adding this team");
                    }
                    onInit();

                }else{

                    try{
                        manageTeamsMenuModel.dropTeamFromDatabase(manageTeamsMenuView.getListOfTeamsComboBoxSelectedItem());
                    }catch (Exception exception){
                        System.out.println(exception + " : problem in ManageTeamsController AddOrDrop action listener saveTeamDataButton");
                        JOptionPane.showMessageDialog(parentFrame, "There was a problem removing this team");
                    }
                    onInit();
                }
            }
        }
    }

    private class ComboBoxListener implements ActionListener {
        public ComboBoxListener(){
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (manageTeamsMenuView.getRegionComboBoxSelectedItem() != manageTeamsMenuView.getRegionComboBox().getItemAt(0)){

                manageTeamsMenuView.getDropTeamButton().setEnabled(true);
                manageTeamsMenuView.getAddTeamButton().setEnabled(true);

                parentFrame.validate();
                parentFrame.repaint();

            } else {

                manageTeamsMenuView.getDropTeamButton().setEnabled(false);
                manageTeamsMenuView.getAddTeamButton().setEnabled(false);

                parentFrame.validate();
                parentFrame.repaint();

            }

        }
    }

}
