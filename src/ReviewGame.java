import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 * Created by timbauer on 12/27/15.
 */
public class ReviewGame extends JPanel{


    ArrayList<Object[]> gameEventResults;
    Object[][] gameResults;
    Object[] eventHolder;
    JLabel event;
    BufferedImage icon;
    Boolean invalidEvent = false;
    long time;
    int xCoord, yCoord;
    MainWindow parentFrame;
    int width = 1000;
    int height = 800;
    Dimension windowDimension = new Dimension(width, height);



    public ReviewGame(String gameName, MainWindow parentFrame)throws Exception{

        this.parentFrame = parentFrame;

        this.setLayout(null);
        this.setPreferredSize(windowDimension);
        this.setBounds(0, 0, width, height);
        this.setBackground(new Color(0, 0, 0, 0));
        this.setOpaque(false);

        parentFrame.addMenu(this);

        gameEventResults = DatabaseManager.getGameData(gameName);

        int numOfRecordedEvents = gameEventResults.size();
        gameResults = new Object[numOfRecordedEvents][5];
        Object[] transitionArray = gameEventResults.toArray(gameResults);

        for(int i = 0; i < numOfRecordedEvents; i++){
            gameResults[i] = (Object[])transitionArray[i];
        }

        for(int i = 0; i < numOfRecordedEvents; i++){
            invalidEvent = false;

            switch ((String)gameResults[i][0]){
                case "top warded":
                    icon = ImageManager.topIcon;
                    break;
                case "mid warded":
                    icon = ImageManager.midIcon;
                    break;
                case "adc warded":
                    icon = ImageManager.adcIcon;
                    break;
                case "support warded":
                    icon = ImageManager.suppIcon;
                    break;
                case "jungle warded":
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

        this.validate();
        this.repaint();
    }
}

