import javax.swing.*;
import java.awt.*;
import java.io.IOException;

/**
 * Created by timbauer on 12/7/15.
 */
public class MainWindow extends JFrame {

    private static MainWindow mainWindow = null;
    static BackgroundMapPanel backgroundMapPanel;
    static JPanel currentMenu;
    static JLayeredPane mainLayeredPane;
    static int width = 1000;
    static int height = 800;
    static Dimension windowDimension = new Dimension(width, height);

    private MainWindow() {

    }

    public static MainWindow getMainWindow(){
        if(mainWindow == null){
            mainWindow = new MainWindow();

            backgroundMapPanel = new BackgroundMapPanel(ImageManager.SrMap);
            mainLayeredPane = new JLayeredPane();
            currentMenu = new MainMenu(windowDimension, mainWindow);

            backgroundMapPanel.setPreferredSize(windowDimension);
            backgroundMapPanel.setBounds(0, 0, width, height);


            //primary setup for just getting the map panel/mainLayeredPane on the JFrame
            mainWindow.setFocusable(true);
            mainWindow.setResizable(false);
            mainWindow.setVisible(true);
            mainWindow.setSize(width,height);
            mainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            mainWindow.getContentPane().add(mainLayeredPane);

            mainLayeredPane.add(backgroundMapPanel, JLayeredPane.DEFAULT_LAYER);
            mainLayeredPane.add(currentMenu, JLayeredPane.PALETTE_LAYER);

        }

        return mainWindow;
    }

    public void addMenu(JPanel newMenu){
        currentMenu = newMenu;
        mainLayeredPane.add(currentMenu, JLayeredPane.PALETTE_LAYER);
    }

    public void removeMenu(){
        mainLayeredPane.remove(currentMenu);
        mainWindow.validate();
        mainWindow.repaint();
    }
}
