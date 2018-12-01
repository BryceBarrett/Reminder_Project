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
public class PopOutController implements Initializable {


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
    private void newReminderAction(ActionEvent event) throws Exception {

        String message = messageText.getText();
        Time remTime = Time.valueOf(timeText.getText());
        Reminder createReminder = new Reminder(message, remTime);


        Reminder.getReminderList().add(createReminder);
        
        MainPaneController.remListSem.acquire();
        MainPaneController.remList.add(createReminder);
        MainPaneController.remListSem.release();

        //FXMLLoader loader = new FXMLLoader();
        //loader.setLocation(getClass().getResource("MainPane.fxml"));
        //MainPaneController controller = loader.getController();
        //controller.addRem(createReminder);
        
        //Stage stage = new Stage();
        

        //MainLauncher.primaryStage.hide();
        //Scene scene = new Scene(loader.load());
       // MainLauncher.primaryStage.setScene(scene);

        //MainLauncher.primaryStage.show();
        MainLauncher.primaryStage.requestFocus();
        Stage stage = (Stage) settingButton.getScene().getWindow();
        stage.close();
        //MainLauncher.primaryStage.setMinHeight(scene.getHeight());
        //MainLauncher.primaryStage.setMinWidth(scene.getWidth());
        
        


    }

}