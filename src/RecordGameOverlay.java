import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * Created by timbauer on 12/27/15.
 */
public class RecordGameOverlay extends JPanel implements KeyListener {

    long timeEventOccurred = 0;
    String top = "top warded";
    String mid = "mid warded";
    String adc = "adc warded";
    String supp = "support warded";
    String jungle = "jungle warded";
    String whatOccurred = jungle;
    String team, teamRegion, gameName;

    int width = 1000;
    int height = 800;
    Dimension windowDimension = new Dimension(width, height);

    JPanel labelHoldingPanel;
    MainWindow parentFrame;


    int xCoord, yCoord;

    public RecordGameOverlay(String team, String teamRegion, MainWindow parentFrame) throws IOException, Exception {

        this.team = team;
        this.teamRegion = teamRegion;
        this.parentFrame = parentFrame;

        addTeamToDatabase();
        gameName = DatabaseManager.addGameToGamesTable(this.team);

        labelHoldingPanel = new JPanel();
        parentFrame.addKeyListener(this);

        labelHoldingPanel.setLayout(new FlowLayout());
        labelHoldingPanel.setPreferredSize(windowDimension);
        labelHoldingPanel.setBounds(0, 0, width, height);
        labelHoldingPanel.setBackground(new Color(0, 0, 0, 0));
        labelHoldingPanel.setOpaque(false);




        //setting layout to null so the GUI doesn't override where we tell images to go
        labelHoldingPanel.setLayout(null);

        parentFrame.addMenu(labelHoldingPanel);
        //listens for mouse clicks and attempts to place icon at mouse click location
        labelHoldingPanel.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                xCoord = e.getX()-12;
                yCoord = e.getY()-25;

                System.out.println(xCoord + ", " + yCoord);

                timeEventOccurred = EventTimer.getCurrentTimeDelta();
                System.out.println(timeEventOccurred);
                System.out.println(whatOccurred + "\n");

                BufferedImage currentIconSelected = ImageManager.getCurrentIcon();
                //new label to hold map icon
                JLabel marker = new JLabel(new ImageIcon(currentIconSelected));

                //this code tries to add map icon to map
                labelHoldingPanel.add(marker);
                Dimension size = marker.getPreferredSize();
                marker.setBounds(xCoord, yCoord, size.width, size.height);

                //redraws the map with new map icon on top
                labelHoldingPanel.validate();
                labelHoldingPanel.repaint();

                try{
                    DatabaseManager.addEntryToCurrentGameTable(gameName, whatOccurred, timeEventOccurred,xCoord, yCoord);
                }catch (Exception e2){
                    System.out.println(e2);
                }
            }
        });

    }

    private void addTeamToDatabase() throws Exception{
        boolean isTeamAlreadyEntered = DatabaseManager.checkIfTeamIsEntered(team);
        if(!isTeamAlreadyEntered){
            DatabaseManager.addNewTeam(team, teamRegion);
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();

        switch (keyCode){
            case KeyEvent.VK_Q:
                ImageManager.setCurrentIcon(1);
                whatOccurred = top;
                e.consume();
                break;
            case KeyEvent.VK_W:
                ImageManager.setCurrentIcon(2);
                whatOccurred = mid;
                e.consume();
                break;
            case KeyEvent.VK_E:
                ImageManager.setCurrentIcon(3);
                whatOccurred = adc;
                e.consume();
                break;
            case KeyEvent.VK_R:
                ImageManager.setCurrentIcon(4);
                whatOccurred = supp;
                e.consume();
                break;
            case KeyEvent.VK_T:
                ImageManager.setCurrentIcon(5);
                whatOccurred = jungle;
                e.consume();
                break;
            default:
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}



