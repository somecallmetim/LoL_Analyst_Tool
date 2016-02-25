package MenusAndOverlays; /**
 * Created by timbauer on 12/27/15.
 */

import DataManagement.ScreenOverlayStack;

import javax.swing.*;
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
                new RecordGameMenuController(new RecordGameMenuView(), new RecordGameMenuModel());
            }else if (e.getSource() == MainMenuView.reviewRecordedGames){
                parentFrame.removeCurrentScreenOverlay();
                new ReviewGamesMenuController(new ReviewGamesMenuView(), new ReviewGamesMenuModel());
            } else if (e.getSource() == MainMenuView.manageTeamsByRegion){
                parentFrame.removeCurrentScreenOverlay();
                new ManageTeamsMenuController(new ManageTeamsMenuView(), new ManageTeamsMenuModel());
            }
        }
    }
}

