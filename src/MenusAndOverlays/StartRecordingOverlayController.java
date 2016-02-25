package MenusAndOverlays;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;

/**
 * Created by timbauer on 2/24/16.
 */
public class StartRecordingOverlayController extends TransparentOverlayBaseClassController{

    StartRecordingOverlayView startRecordingOverlayView;
    StartRecordingOverlayModel startRecordingOverlayModel;

    Component startRecordingButton, backButton;

    RecordGameOverlay recordGameOverlay;

    String team, teamRegion;
    Date gameDate;
    int numOfGameInCurrentSeries;

    ButtonListener buttonListener;

    public StartRecordingOverlayController(StartRecordingOverlayView startRecordingOverlayView,
                                           StartRecordingOverlayModel startRecordingOverlayModel,
                                           String team, String teamRegion, Date gameDate,
                                           int numOfGameInCurrentSeries){

        this.startRecordingOverlayView = startRecordingOverlayView;
        this.startRecordingOverlayModel = startRecordingOverlayModel;

        parentFrame.addOverlay(startRecordingOverlayView);

        this.startRecordingButton = startRecordingOverlayView.getStartRecordingButton();
        this.backButton = startRecordingOverlayView.getBackButton();

        this.team = team;
        this.teamRegion = teamRegion;
        this.gameDate = gameDate;
        this.numOfGameInCurrentSeries = numOfGameInCurrentSeries;

        buttonListener = new ButtonListener();

        startRecordingOverlayView.addStartRecordingButtonActionListener(buttonListener);
        startRecordingOverlayView.addBackButtonActionListener(buttonListener);

        onInit();
    }
    @Override
    public void onInit() {

        componentsToRemove.add(startRecordingButton);
        componentsToRemove.add(backButton);

        removeComponentsFromView(startRecordingOverlayView, componentsToRemove);

        componentsToAdd.add(startRecordingButton);
        componentsToAdd.add(backButton);

        addComponentsToView(startRecordingOverlayView, componentsToAdd);

        componentsToRemove.clear();
        componentsToAdd.clear();
    }

    private class ButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if(e.getSource() == startRecordingButton){
                parentFrame.removeCurrentScreenOverlay();
                try {
                    recordGameOverlay = new RecordGameOverlay(team, teamRegion, gameDate, numOfGameInCurrentSeries);
                } catch (Exception exception){
                    System.out.println("MenusAndOverlays.StartRecordingOverlayView problem: " + exception);
                }
                parentFrame.addOverlay(recordGameOverlay);
            }else if (e.getSource() == backButton){
                parentFrame.removeCurrentScreenOverlay();
                parentFrame.addOverlay(screenOverlayStack.pop());
            }
        }
    }

}
