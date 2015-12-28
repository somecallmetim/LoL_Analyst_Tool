import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by timbauer on 12/27/15.
 */
public class RecordGameMenu extends JPanel{


    JTextField teamNameTextBox;
    JComboBox teamNameFromDb;
    JComboBox<String> teamRegionComboBox;
    JButton goToRecordingMapWindow;
    String teamName, teamRegion;
    JLayeredPane mainLayeredPane;
    int width = 1000;
    int height = 800;
    Dimension windowDimension = new Dimension(width, height);
    MainWindow parentFrame;

    public RecordGameMenu(MainWindow parentFrame){

        this.parentFrame = parentFrame;

        mainLayeredPane = new JLayeredPane();

        this.setLayout(new FlowLayout());
        this.setPreferredSize(windowDimension);
        this.setBounds(0, 0, width, height);
        this.setBackground(new Color(0, 0, 0, 0));
        this.setOpaque(false);

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
                    new RecordGame(teamName, teamRegion, parentFrame);
                }catch (Exception e1){
                    System.out.println("Recording Map Menu\n" + e1);
                }

            }
        }
    }
}
