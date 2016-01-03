import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

/**
 * Created by timbauer on 12/27/15.
 */
public class ReviewGamesMenu extends JPanel {

    ArrayList<String> listOfGames;
    String selectedGame;
    JList<String> games;
    int width = 1000;
    int height = 800;
    Dimension windowDimension = new Dimension(width, height);

    public ReviewGamesMenu(MainWindow originalParentFrame){

        final MainWindow parentFrame = originalParentFrame;

        try{
            listOfGames = DatabaseManager.getListOfRecordedGames();
        }catch (Exception e){
            System.out.println(e);
        }

        String[] gamesList = new String[listOfGames.size()];

        gamesList = listOfGames.toArray(gamesList);

        games = new JList<>(gamesList);


        this.setLayout(new FlowLayout());
        this.setPreferredSize(windowDimension);
        this.setBounds(0, 0, width, height);
        this.setBackground(new Color(0, 0, 0, 0));
        this.setOpaque(false);
        this.add(games);

        MouseListener mouseListener = new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                selectedGame = games.getSelectedValue();
                parentFrame.removeMenu();

                try{
                    new ReviewGameOverlay(selectedGame, parentFrame);
                }catch (Exception e1){System.out.println("GetGamesMenu " + e1);}
            }
        };
        games.addMouseListener(mouseListener);

    }
}

