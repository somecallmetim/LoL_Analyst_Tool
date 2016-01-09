import javax.swing.*;
import java.util.Stack;


/**
 * Created by timbauer on 1/7/16.
 */

public class ScreenOverlayStack extends Stack<JPanel> {

    private static ScreenOverlayStack screenOverlayStack = new ScreenOverlayStack();
    private JPanel currentScreen = null;

    private ScreenOverlayStack() {
    }

    public static ScreenOverlayStack getScreenOverlayStack() {
        return screenOverlayStack;
    }

    @Override
    public JPanel push(JPanel newPanel){
        if(currentScreen != null){
            super.push(currentScreen);
            currentScreen = newPanel;
        }else {
            currentScreen = newPanel;
        }
        return newPanel;
    }
    
    @Override
    public JPanel pop(){
        currentScreen = super.pop();
        return currentScreen;
    }


}
