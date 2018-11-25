import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import java.sql.Time;

/**
 * FXML Controller class
 *
 * @author brycebarrett
 */
public class PopOutController implements Initializable {

    @FXML
    private ListView<Reminder> reminderListView;
    @FXML
    private Button newReminderButton;
    @FXML
    private TextArea messageText;

    @FXML
    private TextField timeText;

    private Reminder createReminder;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void newReminderAction(ActionEvent event) {

        String message = messageText.getText();
        Time remTime = Time.valueOf(timeText.getText());
        createReminder = new Reminder(message, remTime);

    }

}