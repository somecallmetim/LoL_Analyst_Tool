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
public class MainMenu extends JPanel{

    JButton recordNewGame, reviewRecordedGames;
    JPanel recordingMapMenu, reviewGamesMenu;
    MainWindow parentFrame;
    Dimension buttonSize = new Dimension(180, 30);
    int width;
    int height;
    Dimension windowDimension;


    public MainMenu(int width, int height, MainWindow parentFrame){

        this.width = width;
        this.height = height;
        windowDimension = new Dimension(width, height);

        this.parentFrame = parentFrame;

        this.setPreferredSize(windowDimension);
        this.setBounds(0, 0, width, height);
        this.setBackground(new Color(0, 0, 0, 0));
        this.setOpaque(false);


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
                recordingMapMenu = new RecordGameMenu(parentFrame);
                parentFrame.addMenu(recordingMapMenu);
            }else if (e.getSource() == reviewRecordedGames){
                parentFrame.removeMenu();
                reviewGamesMenu = new ReviewGamesMenu(parentFrame);
                parentFrame.addMenu(reviewGamesMenu);
            }
        }
    }

}

