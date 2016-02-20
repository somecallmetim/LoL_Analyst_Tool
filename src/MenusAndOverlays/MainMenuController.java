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
public class MainMenuController {

    ScreenOverlayStack screenOverlayStack = ScreenOverlayStack.getScreenOverlayStack();
    JPanel recordingMapMenu, reviewGamesMenu, manageTeamsMenu;
    MainWindow parentFrame = MainWindow.getMainWindow();
    static ButtonListener buttonListener;

    public MainMenuController(){


        buttonListener = new ButtonListener();
        MainMenuView mainMenuView = new MainMenuView();
        parentFrame.addOverlay(mainMenuView);
        screenOverlayStack.push(mainMenuView);


    }

    class ButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if(e.getSource() == MainMenuView.recordNewGame){
                parentFrame.removeCurrentScreenOverlay();
                recordingMapMenu = new RecordGameMenu();
                parentFrame.addOverlay(recordingMapMenu);
            }else if (e.getSource() == MainMenuView.reviewRecordedGames){
                parentFrame.removeCurrentScreenOverlay();
                reviewGamesMenu = new ReviewGamesMenu();
                parentFrame.addOverlay(reviewGamesMenu);
            } else if (e.getSource() == MainMenuView.manageTeamsByRegion){
                parentFrame.removeCurrentScreenOverlay();
                manageTeamsMenu = new ManageTeamsMenu();
                parentFrame.addOverlay(manageTeamsMenu);
            }
        }
    }
}

