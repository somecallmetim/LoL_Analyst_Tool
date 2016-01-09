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

    MainWindow mainWindow;


    public StartRecordingOverlay (){
        super(MainWindow.windowDimension, ButtonListenerFactory.getMainWindow());
        mainWindow = ButtonListenerFactory.getMainWindow();
        this.team = RecordGameMenu.teamName;
        this.teamRegion = RecordGameMenu.teamRegion;

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
                mainWindow.removeMenu();
                try {
                    recordGameOverlay = new RecordGameOverlay();
                } catch (Exception exception){
                    System.out.println("StartRecordingOverlay problem: " + exception);
                }
                mainWindow.addMenu(recordGameOverlay);
            }
        }
    }
}
