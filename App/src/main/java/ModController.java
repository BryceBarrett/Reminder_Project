import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.sql.Time;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author brycebarrett
 */
public class ModController implements Initializable {


    @FXML
    private Button settingButton;
    @FXML
    private TextArea messageText;
    @FXML
    private TextField timeText;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void modReminderAction(ActionEvent event) throws Exception {

        String message = messageText.getText();
        Time remTime = Time.valueOf(timeText.getText());
        Reminder createReminder = new Reminder(message, remTime);

        System.out.println();

        Reminder.getListSem().acquire();

        Reminder.getReminderList().add(createReminder);
        MainPaneController.remList.add(createReminder);

        Reminder.getListSem().release();

        MainLauncher.primaryStage.requestFocus();
        Stage stage = (Stage) settingButton.getScene().getWindow();
        stage.close();


    }

    public void initValues(String message, String timeIn){
        messageText.setText(message);
        timeText.setText(timeIn);
    }

}