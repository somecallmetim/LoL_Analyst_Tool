import java.awt.*;

/**
 * Created by timbauer on 2/9/16.
 */
public class MenuBarTopSide extends TransparentOverlayBaseClass {

    public MenuBarTopSide(int width, int height, MainWindow parentFrame){
        super(width, height, parentFrame);
        setHeight(40);
    }

    public MenuBarTopSide(Dimension windowDimension, MainWindow parentFrame){
        super(windowDimension, parentFrame);
        setHeight(40);
    }
}
