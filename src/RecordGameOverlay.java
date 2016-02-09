import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * Created by timbauer on 12/27/15.
 */
public class RecordGameOverlay extends TransparentOverlayBaseClass implements KeyListener {

    long timeEventOccurred = 0;
    String top = "top warded";
    String mid = "mid warded";
    String adc = "adc warded";
    String supp = "support warded";
    String jungle = "jungle warded";
    String whatOccurred = jungle;
    String team, teamRegion, gameName;

    Dimension windowDimension;

    JPanel mapMarkerHoldingPanel;
    MenuBarTopSide menuBarTopSide;
    JButton backButton;
    ButtonListener buttonListener;
    MainWindow parentFrame;


    int xCoord, yCoord;

    ScreenOverlayStack screenOverlayStack = ScreenOverlayStack.getScreenOverlayStack();

    public RecordGameOverlay(String team, String teamRegion, Dimension windowDimension, MainWindow parentFrame) throws IOException, Exception {
        buttonListener = new ButtonListener();

        this.windowDimension = windowDimension;
        this.team = team;
        this.teamRegion = teamRegion;
        this.parentFrame = parentFrame;

        addTeamToDatabase();
        gameName = DatabaseManager.addGameToGamesTable(this.team);

        mapMarkerHoldingPanel = new JPanel();
        menuBarTopSide = new MenuBarTopSide(windowDimension, parentFrame);
        parentFrame.addKeyListener(this);

        //setting layout to null so the GUI doesn't override where we tell images to go
        mapMarkerHoldingPanel.setLayout(null);
        mapMarkerHoldingPanel.setPreferredSize(windowDimension);
        mapMarkerHoldingPanel.setBounds(0, 0, (int)windowDimension.getWidth(), (int)windowDimension.getHeight());
        mapMarkerHoldingPanel.setBackground(new Color(0, 0, 0, 0));
        mapMarkerHoldingPanel.setOpaque(false);


        backButton = new JButton("Back");
        backButton.addActionListener(buttonListener);
        menuBarTopSide.add(backButton);



        parentFrame.addOverlay(mapMarkerHoldingPanel);
        parentFrame.addOverlay(menuBarTopSide, JLayeredPane.MODAL_LAYER);

        //listens for mouse clicks and attempts to place icon at mouse click location
        this.addMouseListener(new MouseAdapter() {
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
                mapMarkerHoldingPanel.add(marker);
                Dimension size = marker.getPreferredSize();
                marker.setBounds(xCoord, yCoord, size.width, size.height);

                //redraws the map with new map icon on top
                mapMarkerHoldingPanel.validate();
                mapMarkerHoldingPanel.repaint();

                try{
                    DatabaseManager.addEntryToCurrentGameTable(gameName, whatOccurred, timeEventOccurred,xCoord, yCoord);
                }catch (Exception e2){
                    System.out.println(e2);
                }
            }
        });



        screenOverlayStack.push(this);

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

    private class ButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e){
            if (e.getSource() == backButton){
                //shouldn't need all these, but if top remove call isn't present, the previous screens buttons
                //become unclickable. Since their at the top of the screen, this makes me wonder if the top
                //menu bar is somehow not being completely removed (ie it's at on a higher tier on my
                //layered pane and maybe it's blocking the buttons). If the second remove call isn't present,
                //then the main overlay from this screen is never removed. The only one of these that's behaving as
                //expected is the third remove call where removing it causes the top menu bar to completely remain
                //intact including the back button. However, removing this call, while leaving the back button in place
                //where it shouldn't be on the previous screen, still allows me to click on the buttons below it
                //on the layered pane, which definitely puts a hole in my first theory
                parentFrame.removeCurrentScreenOverlay();
                parentFrame.removeOverlay(mapMarkerHoldingPanel);
                parentFrame.removeOverlay(menuBarTopSide);
                parentFrame.addOverlay(screenOverlayStack.pop());
            }
        }
    }
}



