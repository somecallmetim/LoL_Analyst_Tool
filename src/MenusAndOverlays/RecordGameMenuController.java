package MenusAndOverlays;


import DataManagement.ScreenOverlayStack;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.util.ArrayList;

/**
 * Created by timbauer on 2/20/16.
 */
public class RecordGameMenuController extends TransparentOverlayBaseClassController{


    RecordGameMenuView recordGameMenuView;
    RecordGameMenuModel recordGameMenuModel;

    Component regionComboBox, teamNameComboBox, monthComboBox, dayComboBox, yearComboBox, gameNumberComboBox,
            goToRecordingMapWindowButton, backButton;

    ComboBoxListener comboBoxListener = new ComboBoxListener();
    ButtonListener buttonListener = new ButtonListener();

    String region;

    StartRecordingOverlay startRecordingOverlay;


    public RecordGameMenuController(RecordGameMenuView recordGameMenuView, RecordGameMenuModel recordGameMenuModel){
        this.recordGameMenuView = recordGameMenuView;
        this.recordGameMenuModel = recordGameMenuModel;

        parentFrame.addOverlay(recordGameMenuView);

        regionComboBox = recordGameMenuView.getRegionComboBox();
        teamNameComboBox = recordGameMenuView.getTeamNameComboBox();
        monthComboBox = recordGameMenuView.getMonthComboBox();
        dayComboBox = recordGameMenuView.getDayComboBox();
        yearComboBox = recordGameMenuView.getYearComboBox();
        gameNumberComboBox = recordGameMenuView.getGameNumber();
        goToRecordingMapWindowButton = recordGameMenuView.getGoToRecordingMapWindowButton();
        backButton = recordGameMenuView.getBackButton();

        recordGameMenuView.addRegionComboBoxActionListener(comboBoxListener);
        recordGameMenuView.addBackButtonActionListener(buttonListener);
        recordGameMenuView.addGoToRecordingMapWindowButtonActionListener(buttonListener);


        onInit();


    }

    public void onInit(){

        componentsToRemove.add(regionComboBox);
        componentsToRemove.add(teamNameComboBox);
        componentsToRemove.add(monthComboBox);
        componentsToRemove.add(dayComboBox);
        componentsToRemove.add(yearComboBox);
        componentsToRemove.add(gameNumberComboBox);
        componentsToRemove.add(goToRecordingMapWindowButton);
        componentsToRemove.add(backButton);

        removeComponentsFromView(recordGameMenuView, componentsToRemove);



        componentsToAdd.add(regionComboBox);
        componentsToAdd.add(backButton);

        recordGameMenuView.setRegionComboBoxValues(recordGameMenuModel.getMajorRegions());

        addComponentsToView(recordGameMenuView, componentsToAdd);

        componentsToRemove.clear();
        componentsToAdd.clear();
    }

    public void regionHasBeenSelected(String region){

        componentsToRemove.add(regionComboBox);
        componentsToRemove.add(backButton);

        removeComponentsFromView(recordGameMenuView, componentsToRemove);

        componentsToAdd.add(teamNameComboBox);
        componentsToAdd.add(monthComboBox);
        componentsToAdd.add(dayComboBox);
        componentsToAdd.add(yearComboBox);
        componentsToAdd.add(gameNumberComboBox);

        componentsToAdd.add(goToRecordingMapWindowButton);
        componentsToAdd.add(backButton);


        recordGameMenuView.setTeamNameComboBox(recordGameMenuModel.getTeamsFromCurrentRegion(region));
        recordGameMenuView.setMonthComboBox(recordGameMenuModel.getMonths());
        recordGameMenuView.setDayComboBox(recordGameMenuModel.getDays());
        recordGameMenuView.setYearComboBox(recordGameMenuModel.getYears());
        recordGameMenuView.setGameNumber(recordGameMenuModel.getNumberOfGameInCurrentSeries());

        recordGameMenuView.setMonthComboBoxSelectedItem(recordGameMenuModel.getCurrentMonth());
        recordGameMenuView.setDayComboBoxSelectedItem(recordGameMenuModel.getCurrentDay());
        recordGameMenuView.setYearComboBoxSelectedItem(recordGameMenuModel.getCurrentYear());

        addComponentsToView(recordGameMenuView, componentsToAdd);

        componentsToRemove.clear();
        componentsToAdd.clear();
    }





    private class ButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e){
            if(e.getSource() == goToRecordingMapWindowButton){

                String year = recordGameMenuView.getYearComboBoxSelectedItem();
                String month = recordGameMenuView.getMonthComboBoxSelected();
                String day = recordGameMenuView.getDayComboBoxSelectedItem();


                String teamName = recordGameMenuView.getTeamNameComboBoxSelectedItem();
                int gameNumber = recordGameMenuView.getGameNumberSelected();
                Date sqlDate = recordGameMenuModel.convertStringToSqlDate(month, day, year);


                RecordGameMenuView.parentFrame.removeCurrentScreenOverlay();

                try{
                    startRecordingOverlay = new StartRecordingOverlay(teamName, region, sqlDate, gameNumber);
                }catch (Exception exception){
                    System.out.println("Recording Map Menu\n" + exception);
                }
                parentFrame.addOverlay(startRecordingOverlay);

            }else if (e.getSource() == backButton){
                parentFrame.removeCurrentScreenOverlay();
                parentFrame.addOverlay(screenOverlayStack.pop());
            }
        }
    }

    private class ComboBoxListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (recordGameMenuView.getRegionComboBoxSelectedItem() != recordGameMenuView.getRegionComboBox().getItemAt(0)) {

                region = recordGameMenuView.getRegionComboBoxSelectedItem();
                regionHasBeenSelected(region);

                parentFrame.validate();
                parentFrame.repaint();
            } else {
                //ToDo
            }

        }
    }
}
