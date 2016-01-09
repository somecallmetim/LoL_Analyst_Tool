import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by timbauer on 1/8/16.
 */
public interface IButtonListener extends ActionListener {
    @Override
    public void actionPerformed(ActionEvent event);
}
