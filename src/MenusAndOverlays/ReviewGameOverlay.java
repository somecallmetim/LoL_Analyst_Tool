package MenusAndOverlays;

import DataManagement.DatabaseManager;
import DataManagement.ImageManager;
import DataManagement.ScreenOverlayStack;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 * Created by timbauer on 12/27/15.
 */
public class ReviewGameOverlay extends TransparentOverlayBaseClass {


    ArrayList<Object[]> gameEventResults;
    Object[][] gameResults;
    Object[] eventHolder;
    JLabel event;
    JButton backButton;
    BufferedImage icon;
    Boolean invalidEvent = false;
    ButtonListener buttonListener;
    long time;
    int xCoord, yCoord;

    MainWindow parentFrame;

    ScreenOverlayStack screenOverlayStack = ScreenOverlayStack.getScreenOverlayStack();


    public ReviewGameOverlay(String gameId, Dimension windowDimension, MainWindow parentFrame)throws Exception{
        super(windowDimension, parentFrame);
        buttonListener = new ButtonListener();
        this.parentFrame = parentFrame;

        this.setLayout(null);

        parentFrame.addOverlay(this);

        gameEventResults = DatabaseManager.getGameData(gameId);

        int numOfRecordedEvents = gameEventResults.size();
        gameResults = new Object[numOfRecordedEvents][5];
        Object[] transitionArray = gameEventResults.toArray(gameResults);

        for(int i = 0; i < numOfRecordedEvents; i++){
            gameResults[i] = (Object[])transitionArray[i];
        }

        for(int i = 0; i < numOfRecordedEvents; i++){
            invalidEvent = false;

            switch ((String)gameResults[i][0]){
                case "<html><font color='white'>Q: Top Laner Ward</font></html>":
                    icon = ImageManager.topIcon;
                    break;
                case "<html><font color='white'>W: Mid Laner Ward</font></html>":
                    icon = ImageManager.midIcon;
                    break;
                case "<html><font color='white'>E: ADC Ward</font></html>":
                    icon = ImageManager.adcIcon;
                    break;
                case "<html><font color='white'>R: Support Ward</font></html>":
                    icon = ImageManager.suppIcon;
                    break;
                case "<html><font color='white'>T: Jungler Ward</font></html>":
                    icon = ImageManager.jungleIcon;
                    break;
                default:
                    invalidEvent = true;
                    System.out.println("Invalid Event " + i);
            }

            if(!invalidEvent){
                event = new JLabel(new ImageIcon(icon));
                time = (Long)gameResults[i][1];
                xCoord = (Integer)gameResults[i][2];
                yCoord = (Integer)gameResults[i][3];

                Dimension size = event.getPreferredSize();
                event.setBounds(xCoord, yCoord, size.width, size.height);
                this.add(event);

            }
        }

        event = new JLabel(new ImageIcon(ImageManager.jungleIcon));
        event.setVisible(false);
        this.add(event);



        backButton = new JButton("Back");
        backButton.addActionListener(buttonListener);
        backButton.setBounds(410, 10, 180, 30);
        this.add(backButton);

        this.validate();
        this.repaint();

        screenOverlayStack.push(this);
    }

    private class ButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e){
            if (e.getSource() == backButton){
                parentFrame.removeCurrentScreenOverlay();
                parentFrame.addOverlay(screenOverlayStack.pop());
            }
        }
    }
}

