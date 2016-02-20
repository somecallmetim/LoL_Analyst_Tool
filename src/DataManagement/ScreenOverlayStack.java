package DataManagement; /**
 * Created by timbauer on 1/10/16.
 */
import MenusAndOverlays.TransparentOverlayBaseClass;

import java.util.Stack;

public class ScreenOverlayStack extends Stack<TransparentOverlayBaseClass> {

    private static ScreenOverlayStack screenOverlayStack = new ScreenOverlayStack();
    private TransparentOverlayBaseClass currentScreen = null;
    private TransparentOverlayBaseClass holdingVariable;

    private ScreenOverlayStack() {
    }

    public static ScreenOverlayStack getScreenOverlayStack() {
        return screenOverlayStack;
    }

    @Override
    public TransparentOverlayBaseClass push(TransparentOverlayBaseClass newPanel){
        if(currentScreen != null){
            super.push(currentScreen);
            currentScreen = newPanel;
        }else {
            currentScreen = newPanel;
        }
        return newPanel;
    }

    @Override
    public TransparentOverlayBaseClass pop(){
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
