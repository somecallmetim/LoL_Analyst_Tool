import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by timbauer on 12/27/15.
 */
public class RecordGameMenu extends TransparentOverlayBaseClass {


    JTextField teamNameTextBox;
    JComboBox teamNameFromDb;
    JComboBox<String> teamRegionComboBox;
    JButton goToRecordingMapWindow;
    String teamName, teamRegion;

    MainWindow parentFrame;
    Dimension windowDimension;

    public RecordGameMenu(Dimension windowDimension, MainWindow parentFrame){
        super(windowDimension, parentFrame);
        this.parentFrame = parentFrame;
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
                parentFrame.removeMenu();
                try{
                    new RecordGameOverlay(teamName, teamRegion, windowDimension, parentFrame);
                }catch (Exception e1){
                    System.out.println("Recording Map Menu\n" + e1);
                }

            }
        }
    }
}
