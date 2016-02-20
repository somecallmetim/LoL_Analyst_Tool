import DataManagement.DatabaseManager;
import DataManagement.EventTimer;
import DataManagement.ImageManager;
import MenusAndOverlays.MainWindow;
import TemplatesAndBaseClasses.ScreenOverlayStack;

import java.io.IOException;

/**
 * Created by timbauer on 12/27/15.
 */
public class Main {

    public static void main(String[] args) throws IOException{
        //initialize all my singletons
        EventTimer.getEventTimer();
        DatabaseManager.getDatabaseManager();
        ImageManager.getImageManager();
        ScreenOverlayStack.getScreenOverlayStack();

        //open main window
        MainWindow.getMainWindow();
    }
}
