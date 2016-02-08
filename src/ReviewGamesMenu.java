import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

/**
 * Created by timbauer on 12/27/15.
 */
public class ReviewGamesMenu extends TransparentOverlayBaseClass {

    ArrayList<String> listOfGames;
    String selectedGame;
    JList<String> games;
    ButtonListener buttonListener;
    JButton backButton;

    ScreenOverlayStack screenOverlayStack = ScreenOverlayStack.getScreenOverlayStack();

    public ReviewGamesMenu(final Dimension windowDimension, MainWindow originalParentFrame){
        super(windowDimension, originalParentFrame);
        super.setParentFrame(originalParentFrame);
        buttonListener = new ButtonListener();

        try{
            listOfGames = DatabaseManager.getListOfRecordedGames();
        }catch (Exception e){
            System.out.println(e);
        }

        String[] gamesList = new String[listOfGames.size()];

        gamesList = listOfGames.toArray(gamesList);

        games = new JList<>(gamesList);


        this.setLayout(new FlowLayout());
        this.add(games);

        MouseListener mouseListener = new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                selectedGame = games.getSelectedValue();
                parentFrame.removeMenu();

                try{
                    new ReviewGameOverlay(selectedGame, windowDimension, parentFrame);
                }catch (Exception e1){System.out.println("GetGamesMenu " + e1);}
            }
        };
        games.addMouseListener(mouseListener);
        backButton = new JButton("Back");
        backButton.addActionListener(buttonListener);
        this.add(backButton);

        screenOverlayStack.push(this);

    }

    private class ButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e){
            if (e.getSource() == backButton){
                parentFrame.removeMenu();
                parentFrame.addMenu(screenOverlayStack.pop());
            }
        }
    }
}

