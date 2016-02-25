package DataManagement; /**
 * Created by timbauer on 1/10/16.
 */
import MenusAndOverlays.TransparentOverlayBaseClassView;

import java.util.Stack;

public class ScreenOverlayStack extends Stack<TransparentOverlayBaseClassView> {

    private static ScreenOverlayStack screenOverlayStack = new ScreenOverlayStack();
    private TransparentOverlayBaseClassView currentScreen = null;
    private TransparentOverlayBaseClassView holdingVariable;

    private ScreenOverlayStack() {
    }

    public static ScreenOverlayStack getScreenOverlayStack() {
        return screenOverlayStack;
    }

    @Override
    public TransparentOverlayBaseClassView push(TransparentOverlayBaseClassView newPanel){
        if(currentScreen != null){
            super.push(currentScreen);
            currentScreen = newPanel;
        }else {
            currentScreen = newPanel;
        }
        return newPanel;
    }

    @Override
    public TransparentOverlayBaseClassView pop(){
        if(!this.isEmpty()){
            currentScreen = super.pop();
            return currentScreen;
        }else {
            holdingVariable = currentScreen;
            currentScreen = null;
            return holdingVariable;
        }
    }

    public void clearStack(){
        this.clear();
        currentScreen = null;
    }


}
