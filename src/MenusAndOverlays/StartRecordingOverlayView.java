package MenusAndOverlays;

import DataManagement.ScreenOverlayStack;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;

/**
 * Created by timbauer on 1/7/16.
 */
public class StartRecordingOverlayView extends TransparentOverlayBaseClassView {

    JButton startRecordingButton, backButton;
    //Dimension buttonSize = new Dimension(180, 30);

    ScreenOverlayStack screenOverlayStack = ScreenOverlayStack.getScreenOverlayStack();

    public StartRecordingOverlayView(){

        startRecordingButton = new JButton("Start Recording Game!");
        backButton = new JButton("Back");

        screenOverlayStack.push(this);
    }

    //..............Methods used to set ActionListeners for various components..............//
    public void addStartRecordingButtonActionListener(ActionListener actionListener){
        startRecordingButton.addActionListener(actionListener);
    }

    public void addBackButtonActionListener(ActionListener actionListener){
        backButton.addActionListener(actionListener);
    }


    //..............Methods used to get data from various components..............//



    //..............Methods used to set data for various components..............//



    //..............Methods used to get specific components from menu..............//


    public JButton getStartRecordingButton() {
        return startRecordingButton;
    }

    public JButton getBackButton() {
        return backButton;
    }
}
