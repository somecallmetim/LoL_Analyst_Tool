package MenusAndOverlays;

import DataManagement.ImageManager;
import DataManagement.ScreenOverlayStack;
import TemplatesAndBaseClasses.BackgroundMapPanel;

import javax.swing.*;
import java.awt.*;

/**
 * Created by timbauer on 12/7/15.
 */
public class MainWindow extends JFrame {

    private static MainWindow mainWindow = null;
    static BackgroundMapPanel backgroundMapPanel;
    static JPanel currentOverlay;
    static JLayeredPane mainLayeredPane;
    static int width = 1000;
    static int height = 800;
    static Dimension windowDimension = new Dimension(width, height);

    ScreenOverlayStack screenOverlayStack = ScreenOverlayStack.getScreenOverlayStack();

    private MainWindow() {

    }

    public static MainWindow getMainWindow(){
        if(mainWindow == null){
            mainWindow = new MainWindow();
            onInit();
        }

        return mainWindow;
    }

    public static void onInit(){

        backgroundMapPanel = new BackgroundMapPanel(ImageManager.SrMap);
        mainLayeredPane = new JLayeredPane();


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

        new MainMenuController();
    }

    public void addIconToModalLayer(JLabel icon){
        mainLayeredPane.add(icon, JLayeredPane.MODAL_LAYER);
    }

    public void addOverlay(JPanel newOverlay){
        currentOverlay = newOverlay;
        mainLayeredPane.add(currentOverlay, JLayeredPane.PALETTE_LAYER);
        mainWindow.validate();
        mainWindow.repaint();
    }

    public void addOverlay(JPanel newOverlay, Integer layer){
        mainLayeredPane.add(newOverlay, layer);
        mainWindow.validate();
        mainWindow.repaint();
    }

    public void removeCurrentScreenOverlay(){
        mainLayeredPane.remove(currentOverlay);
        mainWindow.validate();
        mainWindow.repaint();
    }

    public void removeOverlay(JPanel overlay){
        mainLayeredPane.remove(overlay);
        mainWindow.validate();
        mainWindow.repaint();
    }

    public void removeAllOverlays(){
        mainLayeredPane.removeAll();
        mainWindow.validate();
        mainWindow.repaint();
    }

    public void reset(){
        removeAllOverlays();
        screenOverlayStack.clearStack();
        onInit();
    }

    public static Dimension getWindowDimension() {
        return windowDimension;
    }
}
