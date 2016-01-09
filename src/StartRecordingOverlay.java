import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by timbauer on 1/7/16.
 */
public class StartRecordingOverlay extends TransparentOverlayBaseClass {

    JButton startRecording;
    RecordGameOverlay recordGameOverlay;
    Dimension buttonSize = new Dimension(180, 30);
    String team, teamRegion;

    MainWindow parentFrame;
    Dimension windowDimension;

    public StartRecordingOverlay (String team, String teamRegion, Dimension windowDimension, MainWindow parentFrame){
        super(windowDimension, parentFrame);
        this.team = team;
        this.teamRegion = teamRegion;
        this.windowDimension = windowDimension;
        this.parentFrame = parentFrame;

        ButtonListener buttonListener = new ButtonListener();

        startRecording = new JButton("Start Recording Game!");
        startRecording.addActionListener(buttonListener);
        startRecording.setPreferredSize(buttonSize);
        this.add(startRecording);
    }

    private class ButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if(e.getSource() == startRecording){
                parentFrame.removeMenu();
                try {
                    recordGameOverlay = new RecordGameOverlay(team, teamRegion, windowDimension, parentFrame);
                } catch (Exception exception){
                    System.out.println("StartRecordingOverlay problem: " + exception);
                }
                parentFrame.addMenu(recordGameOverlay);
            }
        }
    }
}
