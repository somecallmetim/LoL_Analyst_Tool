import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by timbauer on 12/27/15.
 */
public class RecordGameMenu extends TransparentOverlayBaseClass {

    private JTextField teamNameTextBox;
    private JComboBox teamNameFromDb;
    private JComboBox<String> teamRegionComboBox;
    private JButton goToRecordingMapWindow;
    public static String teamName, teamRegion;
    StartRecordingOverlay startRecordingOverlay;

    MainWindow mainWindow;
    Dimension windowDimension;

    public RecordGameMenu(){
        super(MainWindow.windowDimension, ButtonListenerFactory.getMainWindow());
        this.mainWindow = ButtonListenerFactory.getMainWindow();
        this.windowDimension = windowDimension;

        this.setLayout(new FlowLayout());

        teamNameTextBox = new JTextField("Team Name");

        String[] majorRegions = {"NA LCS", "EU LCS", "LCK (Korea)", "LPL (China)", "LMS (Taiwan)", "IWC (International Wild Card)"};

        teamRegionComboBox = new JComboBox<String>(majorRegions);

        ButtonListener buttonListener = new ButtonListener();

        goToRecordingMapWindow = new JButton("Ok");
        goToRecordingMapWindow.addActionListener(buttonListener);

        this.add(teamNameTextBox);
        this.add(teamRegionComboBox);
        this.add(goToRecordingMapWindow);



    }

    private class ButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e){
            if(e.getSource() == goToRecordingMapWindow){

                teamName = teamNameTextBox.getText();
                teamRegion = teamRegionComboBox.getSelectedItem().toString();
                mainWindow.removeMenu();
                try{
                    startRecordingOverlay = new StartRecordingOverlay();
                }catch (Exception exception){
                    System.out.println("Recording Map Menu\n" + exception);
                }
                mainWindow.addMenu(startRecordingOverlay);

            }
        }
    }
}
