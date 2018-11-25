import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;

import java.sql.Time;

/**
 * FXML Controller class
 *
 * @author brycebarrett
 */
public class PopOutController implements Initializable {


    @FXML
    private Button newReminderButton;
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


        List<Reminder> rList = Reminder.getReminderList();
        rList.add(createReminder);
        Reminder.setReminderList(rList);

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("MainPane.fxml"));
        //MainPaneController controller = loader.getController();
        //controller.addRem(createReminder);

        MainLauncher.primaryStage.hide();
        Scene scene = new Scene(loader.load());
        MainLauncher.primaryStage.setScene(scene);

        MainLauncher.primaryStage.show();
        MainLauncher.primaryStage.requestFocus();

        MainLauncher.primaryStage.setMinHeight(scene.getHeight());
        MainLauncher.primaryStage.setMinWidth(scene.getWidth());


    }

}