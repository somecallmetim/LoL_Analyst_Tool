package MenusAndOverlays;

import DataManagement.ScreenOverlayStack;

import java.awt.*;
import java.util.ArrayList;

/**
 * Created by timbauer on 2/23/16.
 */
abstract public class TransparentOverlayBaseClassController {

    ArrayList<Component> componentsToAdd = new ArrayList<>();
    ArrayList<Component> componentsToRemove = new ArrayList<>();
    Component[] arrayOfComponentsToAdd, arrayOfComponentsToRemove;


    MainWindow parentFrame = MainWindow.getMainWindow();
    ScreenOverlayStack screenOverlayStack = ScreenOverlayStack.getScreenOverlayStack();

    public TransparentOverlayBaseClassController(){}

    abstract public void onInit();
    
    public void addComponentsToView(TransparentOverlayBaseClassView currentView, ArrayList<Component> componentsToAdd){
        Component[] convertedArray = new Component[componentsToAdd.size()];
        convertedArray = componentsToAdd.toArray(convertedArray);
        
        currentView.addComponents(convertedArray);
    }

    public void removeComponentsFromView(TransparentOverlayBaseClassView currentView, ArrayList<Component> componentsToRemove){
        Component[] convertedArray = new Component[componentsToRemove.size()];
        convertedArray = componentsToRemove.toArray(convertedArray);

        currentView.removeComponents(convertedArray);
    }
}
