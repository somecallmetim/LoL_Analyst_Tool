import java.io.IOException;

/**
 * Created by timbauer on 12/27/15.
 */
public class Main {

    public static void main(String[] args) throws IOException{
        EventTimer.getEventTimer();
        DatabaseManager.getDatabaseManager();
        ImageManager.getImageManager();
        MainWindow.getMainWindow();
    }
}
