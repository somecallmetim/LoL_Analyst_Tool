package MenusAndOverlays; /**
 * Created by timbauer on 12/27/15.
 */

import DataManagement.ScreenOverlayStack;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by timbauer on 12/7/15.
 */
public class MainMenuController extends TransparentOverlayBaseClass {

    JButton recordNewGame, reviewRecordedGames, manageTeamsByRegion;
    JPanel recordingMapMenu, reviewGamesMenu, manageTeamsMenu;
    Dimension buttonSize = new Dimension(180, 30);
    Dimension windowDimension;
    MainWindow parentFrame;

    ScreenOverlayStack screenOverlayStack = ScreenOverlayStack.getScreenOverlayStack();



    public MainMenuController(Dimension windowDimension, MainWindow parentFrame){
        super(windowDimension, parentFrame);
        this.windowDimension = windowDimension;
        this.parentFrame = parentFrame;


        ButtonListener buttonListener = new ButtonListener();


        recordNewGame = new JButton("Record New Game");
        recordNewGame.addActionListener(buttonListener);
        recordNewGame.setPreferredSize(buttonSize);
        this.add(recordNewGame);



        reviewRecordedGames = new JButton("Review Recorded Games");
        reviewRecordedGames.addActionListener(buttonListener);
        reviewRecordedGames.setPreferredSize(buttonSize);
        this.add(reviewRecordedGames);

        manageTeamsByRegion = new JButton("Manage Teams By Region");
        manageTeamsByRegion.addActionListener(buttonListener);
        manageTeamsByRegion.setPreferredSize(buttonSize);
        this.add(manageTeamsByRegion);


        screenOverlayStack.push(this);



    }

    private class ButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if(e.getSource() == recordNewGame){
                parentFrame.removeCurrentScreenOverlay();
                recordingMapMenu = new RecordGameMenu(windowDimension, parentFrame);
                parentFrame.addOverlay(recordingMapMenu);
            }else if (e.getSource() == reviewRecordedGames){
                parentFrame.removeCurrentScreenOverlay();
                reviewGamesMenu = new ReviewGamesMenu(windowDimension, parentFrame);
                parentFrame.addOverlay(reviewGamesMenu);
            } else if (e.getSource() == manageTeamsByRegion){
                parentFrame.removeCurrentScreenOverlay();
                manageTeamsMenu = new ManageTeamsMenu(windowDimension, parentFrame);
                parentFrame.addOverlay(manageTeamsMenu);

            }
        }
    }

}

