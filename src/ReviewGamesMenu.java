import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

/**
 * Created by timbauer on 12/27/15.
 */
public class ReviewGamesMenu extends TransparentOverlayBaseClass {

    private ArrayList<String> listOfGames;
    public static String selectedGame;
    private JList<String> games;


    public ReviewGamesMenu(final Dimension windowDimension, MainWindow originalParentFrame){
        super(MainWindow.windowDimension, ButtonListenerFactory.getMainWindow());
        super.setParentFrame(ButtonListenerFactory.getMainWindow());

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
                    new ReviewGameOverlay();
                }catch (Exception e1){System.out.println("GetGamesMenu " + e1);}
            }
        };
        games.addMouseListener(mouseListener);

    }
}

