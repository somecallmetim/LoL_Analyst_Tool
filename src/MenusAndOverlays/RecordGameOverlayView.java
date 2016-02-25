package MenusAndOverlays;

import DataManagement.DatabaseManager;
import DataManagement.EventTimer;
import DataManagement.ImageManager;
import DataManagement.ScreenOverlayStack;
import TemplatesAndBaseClasses.HorizontalMenuBar;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.sql.Date;

/**
 * Created by timbauer on 12/27/15.
 */
public class RecordGameOverlayView extends TransparentOverlayBaseClassView implements KeyListener {

    long timeEventOccurred = 0;
    String top = "<html><font color='white'>Q: Top Laner Ward</font></html>";
    String mid = "<html><font color='white'>W: Mid Laner Ward</font></html>";
    String adc = "<html><font color='white'>E: ADC Ward</font></html>";
    String supp = "<html><font color='white'>R: Support Ward</font></html>";
    String jungle = "<html><font color='white'>T: Jungler Ward</font></html>";
    String whatOccurred = top;
    String team, teamRegion, game_Id;
    Date sqlDate;
    int numOfGameInCurrentSeries;

    JPanel mapMarkerHoldingPanel;
    HorizontalMenuBar topMenuBar, bottomMenuBar;
    JButton exitToMainMenu;
    JLabel topMarker, midMarker, jungleMarker, adcMarker, supportMarker;
    ButtonListener buttonListener;

    Cursor cursor;
    Toolkit toolkit = Toolkit.getDefaultToolkit();


    int xCoord, yCoord;

    ScreenOverlayStack screenOverlayStack = ScreenOverlayStack.getScreenOverlayStack();

    public RecordGameOverlayView(String team, String teamRegion, Date sqlDate, int numOfGameInCurrentSeries) throws IOException, Exception {
        super();

        buttonListener = new ButtonListener();

        this.team = team;
        this.teamRegion = teamRegion;
        this.sqlDate = sqlDate;
        this.numOfGameInCurrentSeries = numOfGameInCurrentSeries;

        game_Id = DatabaseManager.addGameToGamesTable(this.team, this.sqlDate, this.numOfGameInCurrentSeries);

        mapMarkerHoldingPanel = new JPanel();
        topMenuBar = new HorizontalMenuBar(windowDimension, parentFrame);
        bottomMenuBar = new HorizontalMenuBar(windowDimension, parentFrame);

        parentFrame.addKeyListener(this);

        //setting layout to null so the GUI doesn't override where we tell images to go
        mapMarkerHoldingPanel.setLayout(null);
        mapMarkerHoldingPanel.setPreferredSize(windowDimension);
        mapMarkerHoldingPanel.setBounds(0, 0, (int)windowDimension.getWidth(), (int)windowDimension.getHeight());
        mapMarkerHoldingPanel.setBackground(new Color(0, 0, 0, 0));
        mapMarkerHoldingPanel.setOpaque(false);


        exitToMainMenu = new JButton("Exit to Main Menu");
        exitToMainMenu.addActionListener(buttonListener);
        topMenuBar.add(exitToMainMenu);

        bottomMenuBar.setBounds(0, 740, parentFrame.getWidth(), bottomMenuBar.getHeight());
        bottomMenuBar.setLayout(new FlowLayout());


        topMarker = new JLabel(top, new ImageIcon(ImageManager.topIcon), JLabel.LEFT);
        midMarker = new JLabel(mid, new ImageIcon(ImageManager.midIcon), JLabel.LEFT);
        adcMarker = new JLabel(adc, new ImageIcon(ImageManager.adcIcon), JLabel.LEFT);
        supportMarker = new JLabel(supp, new ImageIcon(ImageManager.suppIcon), JLabel.LEFT);
        jungleMarker = new JLabel(jungle, new ImageIcon(ImageManager.jungleIcon), JLabel.LEFT);

        bottomMenuBar.add(topMarker);
        bottomMenuBar.add(midMarker);
        bottomMenuBar.add(adcMarker);
        bottomMenuBar.add(supportMarker);
        bottomMenuBar.add(jungleMarker);



        cursor = toolkit.createCustomCursor(ImageManager.getCurrentIcon(), new Point(parentFrame.getX(),
                parentFrame.getY()), "img");

        parentFrame.setCursor(cursor);
        parentFrame.addOverlay(mapMarkerHoldingPanel);
        parentFrame.addOverlay(topMenuBar, JLayeredPane.MODAL_LAYER);
        parentFrame.addOverlay(bottomMenuBar, JLayeredPane.MODAL_LAYER);

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
                Dimension size = marker.getPreferredSize();
                marker.setBounds(xCoord, yCoord, size.width, size.height);
                parentFrame.addIconToModalLayer(marker);

                //redraws the map with new map icon on top
                mapMarkerHoldingPanel.validate();
                mapMarkerHoldingPanel.repaint();

                try{
                    DatabaseManager.addEntryToCurrentGameTable(game_Id, whatOccurred, timeEventOccurred, xCoord, yCoord);
                }catch (Exception e2){
                    System.out.println(e2);
                }
            }
        });

        topMenuBar.addMouseMotionListener(new MouseMotionListener() {
            @Override
            public void mouseDragged(MouseEvent e) {

            }

            @Override
            public void mouseMoved(MouseEvent e) {
                int x = e.getX();
                int y = e.getY();
                Rectangle buttonBoundaries = topMenuBar.getBounds();
                if(buttonBoundaries != null && buttonBoundaries.contains(x,y)){
                    Cursor defaultCursor = new Cursor(Cursor.DEFAULT_CURSOR);
                    topMenuBar.setCursor(defaultCursor);
                }else {
                    cursor = toolkit.createCustomCursor(ImageManager.getCurrentIcon(), new Point(parentFrame.getX(),
                            parentFrame.getY()), "img");
                    parentFrame.setCursor(cursor);
                    e.consume();
                }
            }
        });

        screenOverlayStack.push(this);

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
                cursor = toolkit.createCustomCursor(ImageManager.getCurrentIcon(), new Point(parentFrame.getX(),
                        parentFrame.getY()), "img");
                parentFrame.setCursor(cursor);
                e.consume();
                break;
            case KeyEvent.VK_W:
                ImageManager.setCurrentIcon(2);
                whatOccurred = mid;
                cursor = toolkit.createCustomCursor(ImageManager.getCurrentIcon(), new Point(parentFrame.getX(),
                        parentFrame.getY()), "img");
                parentFrame.setCursor(cursor);
                e.consume();
                break;
            case KeyEvent.VK_E:
                ImageManager.setCurrentIcon(3);
                whatOccurred = adc;
                cursor = toolkit.createCustomCursor(ImageManager.getCurrentIcon(), new Point(parentFrame.getX(),
                        parentFrame.getY()), "img");
                parentFrame.setCursor(cursor);
                e.consume();
                break;
            case KeyEvent.VK_R:
                ImageManager.setCurrentIcon(4);
                whatOccurred = supp;
                cursor = toolkit.createCustomCursor(ImageManager.getCurrentIcon(), new Point(parentFrame.getX(),
                        parentFrame.getY()), "img");
                parentFrame.setCursor(cursor);
                e.consume();
                break;
            case KeyEvent.VK_T:
                ImageManager.setCurrentIcon(5);
                whatOccurred = jungle;
                cursor = toolkit.createCustomCursor(ImageManager.getCurrentIcon(), new Point(parentFrame.getX(),
                        parentFrame.getY()), "img");
                parentFrame.setCursor(cursor);
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

        //JPanel holdingVariable;
        ScreenOverlayStack screenOverlayStack = ScreenOverlayStack.getScreenOverlayStack();

        @Override
        public void actionPerformed(ActionEvent e){
             if (e.getSource() == exitToMainMenu){
                Cursor defaultCursor = new Cursor(Cursor.DEFAULT_CURSOR);
                parentFrame.setCursor(defaultCursor);
                parentFrame.reset();
            }
        }
    }
}



