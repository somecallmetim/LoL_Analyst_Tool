/**
 * Created by timbauer on 12/27/15.
 */
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by timbauer on 12/7/15.
 */
public class MainMenu extends TransparentOverlayBaseClass {

    JButton recordNewGame, reviewRecordedGames;
    JPanel recordingMapMenu, reviewGamesMenu;
    Dimension buttonSize = new Dimension(180, 30);
    Dimension windowDimension;
    MainWindow parentFrame;



    public MainMenu(Dimension windowDimension, MainWindow parentFrame){
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



    }

    private class ButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if(e.getSource() == recordNewGame){
                parentFrame.removeMenu();
                recordingMapMenu = new RecordGameMenu(windowDimension, parentFrame);
                parentFrame.addMenu(recordingMapMenu);
            }else if (e.getSource() == reviewRecordedGames){
                parentFrame.removeMenu();
                reviewGamesMenu = new ReviewGamesMenu(windowDimension, parentFrame);
                parentFrame.addMenu(reviewGamesMenu);
            }
        }
    }

}

