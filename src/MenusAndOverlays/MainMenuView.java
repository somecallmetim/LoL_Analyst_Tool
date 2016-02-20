package MenusAndOverlays;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import MenusAndOverlays.MainMenuController.ButtonListener;

/**
 * Created by timbauer on 2/20/16.
 */
public class MainMenuView extends TransparentOverlayBaseClassView {

    public static JButton recordNewGame, reviewRecordedGames, manageTeamsByRegion;
    Dimension buttonSize = new Dimension(180, 30);

    public MainMenuView (){
        super();

        recordNewGame = new JButton("Record New Game");
        recordNewGame.addActionListener(MainMenuController.buttonListener);
        recordNewGame.setPreferredSize(buttonSize);
        this.add(recordNewGame);



        reviewRecordedGames = new JButton("Review Recorded Games");
        reviewRecordedGames.addActionListener(MainMenuController.buttonListener);
        reviewRecordedGames.setPreferredSize(buttonSize);
        this.add(reviewRecordedGames);

        manageTeamsByRegion = new JButton("Manage Teams By Region");
        manageTeamsByRegion.addActionListener(MainMenuController.buttonListener);
        manageTeamsByRegion.setPreferredSize(buttonSize);
        this.add(manageTeamsByRegion);
    }


}
