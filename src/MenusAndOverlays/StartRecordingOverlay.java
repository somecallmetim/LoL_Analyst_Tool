package MenusAndOverlays;

import DataManagement.ScreenOverlayStack;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;

/**
 * Created by timbauer on 1/7/16.
 */
public class StartRecordingOverlay extends TransparentOverlayBaseClassView {

    JButton startRecording, backButton;
    RecordGameOverlay recordGameOverlay;
    Dimension buttonSize = new Dimension(180, 30);
    String team, teamRegion;
    int numOfGameInCurrentSeries;
    Date gameDate;


    ScreenOverlayStack screenOverlayStack = ScreenOverlayStack.getScreenOverlayStack();

    public StartRecordingOverlay (String team, String teamRegion,  Date gameDate, int numOfGameInCurrentSeries){
        super();
        this.team = team;
        this.teamRegion = teamRegion;
        this.gameDate = gameDate;
        this.numOfGameInCurrentSeries = numOfGameInCurrentSeries;

        ButtonListener buttonListener = new ButtonListener();

        startRecording = new JButton("Start Recording Game!");
        startRecording.addActionListener(buttonListener);
        startRecording.setPreferredSize(buttonSize);
        this.add(startRecording);

        backButton = new JButton("Back");
        backButton.addActionListener(buttonListener);
        backButton.setPreferredSize(buttonSize);
        this.add(backButton);

        screenOverlayStack.push(this);
    }

    private class ButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if(e.getSource() == startRecording){
                parentFrame.removeCurrentScreenOverlay();
                try {
                    recordGameOverlay = new RecordGameOverlay(team, teamRegion, gameDate, numOfGameInCurrentSeries);
                } catch (Exception exception){
                    System.out.println("MenusAndOverlays.StartRecordingOverlay problem: " + exception);
                }
                parentFrame.addOverlay(recordGameOverlay);
            }else if (e.getSource() == backButton){
                parentFrame.removeCurrentScreenOverlay();
                parentFrame.addOverlay(screenOverlayStack.pop());
            }
        }
    }
}
