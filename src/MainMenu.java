/**
 * Created by timbauer on 12/27/15.
 */
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

/**
 * Created by timbauer on 12/7/15.
 */
public class MainMenu extends TransparentOverlayBaseClass {

    JButton recordNewGame, reviewRecordedGames;
    JPanel recordingMapMenu, reviewGamesMenu;
    Dimension buttonSize = new Dimension(180, 30);

    ScreenOverlayStack screenOverlayStack = ScreenOverlayStack.getScreenOverlayStack();
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

        screenOverlayStack.push(this);
        HashMap<JButton, Class<?>> buttonListenerArguments = new HashMap<>(2);
        buttonListenerArguments.put(recordNewGame, RecordGameMenu.class);
        buttonListenerArguments.put(reviewRecordedGames, ReviewGamesMenu.class);

    }

    private class ButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if(e.getSource() == recordNewGame){
                parentFrame.removeMenu();
                recordingMapMenu = new RecordGameMenu();
                parentFrame.addMenu(recordingMapMenu);
            }else if (e.getSource() == reviewRecordedGames){
                parentFrame.removeMenu();
                reviewGamesMenu = new ReviewGamesMenu(windowDimension, parentFrame);
                parentFrame.addMenu(reviewGamesMenu);
            }
        }
    }

}

