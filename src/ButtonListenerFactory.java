import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

/**
 * Created by timbauer on 1/8/16.
 */
public class ButtonListenerFactory {

    private HashMap<JButton, Class<?>> buttonToScreenMap;
    private static MainWindow mainWindow;
    private ButtonListener buttonListener;

    public ButtonListenerFactory() {
        mainWindow = MainWindow.getMainWindow();
    }

    public IButtonListener getButtonListener(HashMap<JButton, Class<?>> buttonToScreenMap){
        this.buttonToScreenMap = buttonToScreenMap;
        buttonListener = new ButtonListener();
        return buttonListener;
    }


    public static MainWindow getMainWindow() {
        return mainWindow;
    }

    private class ButtonListener implements IButtonListener {
        @Override
        public void actionPerformed(ActionEvent e) {

            if(buttonToScreenMap.containsKey(e.getSource())){
                try{
                    mainWindow.removeMenu();
                    Object newScreen = buttonToScreenMap.get(e.getSource()).getConstructor();
                    mainWindow.addMenu((JPanel)newScreen);
                }catch (Exception exception){
                    System.out.println("ButtonListenerFactroy error" + exception);                }
            }
        }
    }
}
