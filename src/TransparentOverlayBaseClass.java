import javax.swing.*;
import java.awt.*;

/**
 * Created by timbauer on 1/6/16.
 */
abstract public class TransparentOverlayBaseClass extends JPanel {

    static int width;
    static int height;
    static Dimension windowDimension;
    static MainWindow parentFrame;

    public TransparentOverlayBaseClass(){

        this.setPreferredSize(windowDimension);
        this.setBounds(0, 0, width, height);
        this.setBackground(new Color(0, 0, 0, 0));
        this.setOpaque(false);
    }

    public TransparentOverlayBaseClass(int width, int height, MainWindow parentFrame){

        setWidth(width);
        setHeight(height);
        setWindowDimension(width, height);
        setParentFrame(parentFrame);

        this.setPreferredSize(windowDimension);
        this.setBounds(0, 0, width, height);
        this.setBackground(new Color(0, 0, 0, 0));
        this.setOpaque(false);
    }

    public TransparentOverlayBaseClass(Dimension windowDimension, MainWindow parentFrame){

        setHeight((int)windowDimension.getHeight());
        setWidth((int)windowDimension.getWidth());
        setParentFrame(parentFrame);
        this.setPreferredSize(windowDimension);
        this.setBounds(0, 0, width, height);
        this.setBackground(new Color(0, 0, 0, 0));
        this.setOpaque(false);
    }

    public static void setWidth(int width) {
        TransparentOverlayBaseClass.width = width;
    }

    public static void setHeight(int height) {
        TransparentOverlayBaseClass.height = height;
    }

    public static void setWindowDimension(Dimension windowDimension) {
        TransparentOverlayBaseClass.windowDimension = windowDimension;
    }

    public static void setWindowDimension(int width, int height){
        TransparentOverlayBaseClass.windowDimension = new Dimension(width, height);
    }

    public static void setParentFrame(MainWindow newParentFrame) {
        parentFrame = newParentFrame;
    }



    public static int getWindowWidth() {
        return width;
    }


    public static int getWindowHeight() {
        return height;
    }

    public static Dimension getWindowDimension() {
        return windowDimension;
    }

    public static MainWindow getParentFrame() {
        return parentFrame;
    }
}
