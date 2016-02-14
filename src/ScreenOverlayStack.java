/**
 * Created by timbauer on 1/10/16.
 */

import javax.swing.*;
import java.util.Stack;

public class ScreenOverlayStack extends Stack<JPanel> {

    private static ScreenOverlayStack screenOverlayStack = new ScreenOverlayStack();
    private JPanel currentScreen = null;
    private JPanel holdingVariable;

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
        if(!this.isEmpty()){
            currentScreen = super.pop();
            return currentScreen;
        }else {
            holdingVariable = currentScreen;
            currentScreen = null;
            return holdingVariable;
        }

    }


}
