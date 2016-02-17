import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * Created by timbauer on 12/27/15.
 */
public class RecordGameMenu extends TransparentOverlayBaseClass {

    JComboBox<String> regionComboBox, teamNameFromDb;
    JButton goToRecordingMapWindow, backButton;
    String teamName, teamRegion;
    ArrayList<String> listOfTeams;
    String[] teamList;
    StartRecordingOverlay startRecordingOverlay;

    MainWindow parentFrame;
    Dimension windowDimension;

    ScreenOverlayStack screenOverlayStack = ScreenOverlayStack.getScreenOverlayStack();

    public RecordGameMenu(Dimension windowDimension, MainWindow parentFrame){
        super(windowDimension, parentFrame);
        this.parentFrame = parentFrame;
        this.windowDimension = windowDimension;

        String[] majorRegions = {"Please Select A Region", "NA LCS", "EU LCS", "LCK (Korea)", "LPL (China)", "LMS (Taiwan)", "IWC (International Wild Card)"};

        this.setLayout(new FlowLayout());

        ButtonListener buttonListener = new ButtonListener();
        ComboBoxListener comboBoxListener = new ComboBoxListener(majorRegions);


        regionComboBox = new JComboBox<String>(majorRegions);
        regionComboBox.addActionListener(comboBoxListener);

        goToRecordingMapWindow = new JButton("Ok");
        goToRecordingMapWindow.addActionListener(buttonListener);


        this.add(regionComboBox);

        backButton = new JButton("Back");
        backButton.addActionListener(buttonListener);
        this.add(backButton);

        screenOverlayStack.push(this);


    }

    private class ButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e){
            if(e.getSource() == goToRecordingMapWindow){

                teamName = teamNameFromDb.getSelectedItem().toString();
                teamRegion = regionComboBox.getSelectedItem().toString();
                parentFrame.removeCurrentScreenOverlay();
                try{
                    startRecordingOverlay = new StartRecordingOverlay(teamName, teamRegion, windowDimension, parentFrame);
                }catch (Exception exception){
                    System.out.println("Recording Map Menu\n" + exception);
                }
                parentFrame.addOverlay(startRecordingOverlay);

            }else if (e.getSource() == backButton){
                try{
                    remove(teamNameFromDb);
                }catch (Exception exception){}
                parentFrame.removeCurrentScreenOverlay();
                parentFrame.addOverlay(screenOverlayStack.pop());
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
            if (regionComboBox.getSelectedItem() != regionComboBox.getItemAt(0)){
                try{
                    remove(teamNameFromDb);
                }catch (Exception exception){}

                try{
                    listOfTeams = DatabaseManager.getListOfTeamsByRegion(regionComboBox.getSelectedItem().toString());
                }catch (Exception exception){
                    System.out.println(exception + " : RecordGameMenu ComboBoxListener actionPerformed");
                }

                teamList = new String[listOfTeams.size()];
                teamList = listOfTeams.toArray(teamList);
                teamNameFromDb = new JComboBox<>(teamList);
                remove(backButton);

                add(teamNameFromDb);
                add(goToRecordingMapWindow);
                add(backButton);

                parentFrame.validate();
                parentFrame.repaint();
            } else {

            }

        }
    }
}
