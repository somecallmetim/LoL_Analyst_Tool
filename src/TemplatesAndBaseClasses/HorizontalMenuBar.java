package TemplatesAndBaseClasses;

import MenusAndOverlays.MainWindow;
import MenusAndOverlays.TransparentOverlayBaseClassView;

import java.awt.*;

/**
 * Created by timbauer on 2/9/16.
 */
public class HorizontalMenuBar extends TransparentOverlayBaseClassView {

    public HorizontalMenuBar(int width, int height, MainWindow parentFrame){
        super(width, height, parentFrame);
        setHeight(40);
    }

    public HorizontalMenuBar(Dimension windowDimension, MainWindow parentFrame){
        super(windowDimension, parentFrame);
        setHeight(40);
    }
}
