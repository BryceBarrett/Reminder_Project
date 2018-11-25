/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.net.URL;
import java.sql.Time;
import java.util.List;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author ryanb
 */
public class MainPaneController implements Initializable {

    @FXML
    private ListView<Reminder> reminderListView;
    @FXML
    private Button newReminderButton;
    @FXML
    private Button deleteReminderButton;
    @FXML
    private Button modifyReminderButton;
    @FXML
    private Button quitButton;

    //private ObservableList<Reminder> remList = FXCollections.observableArrayList();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        /*//String message = messageText.getText();
        Time remTime = Time.valueOf("17:44:44");
        Reminder createReminder = new Reminder("test", remTime);


        List<Reminder> rList = Reminder.getReminderList();
        rList.add(createReminder);
        Reminder.setReminderList(rList);

        for(Reminder rem : Reminder.getReminderList()) {
            System.out.println(rem.getMessage());
        }*/



        reminderListView.setCellFactory(param -> {
            ListCell<Reminder> cell = new ListCell<Reminder>() {
                @Override
                protected void updateItem(Reminder item, boolean empty) {
                    super.updateItem(item, empty);
                    if(item != null) {
                        setText(item.getTime().toString() + "\n" + item.getMessage());
                    }
                }
            };
            return new ListCell<Reminder>();
        });
        
        ObservableList<Reminder> remList = FXCollections.observableArrayList();
        for(Reminder rem : Reminder.getReminderList()) {
            remList.add(rem);
        }
        reminderListView.setItems(remList);
    }    

    @FXML
    private void newReminderAction(ActionEvent event) throws Exception {


        Parent root = FXMLLoader.load(getClass().getResource("PopOut.fxml"));
        Scene scene = new Scene(root);

        MainLauncher.primaryStage.hide();
        MainLauncher.primaryStage.setScene(scene);


        MainLauncher.primaryStage.show();
        MainLauncher.primaryStage.requestFocus();

        MainLauncher.primaryStage.setMinHeight(scene.getHeight());
        MainLauncher.primaryStage.setMinWidth(scene.getWidth());

    }

    @FXML
    private void deleteReminderAction(ActionEvent event) {
    }

    @FXML
    private void modifyReminderAction(ActionEvent event) {
    }

    @FXML
    private void quitAction(ActionEvent event) {
        Reminder.serializeList();
        Platform.exit();
        System.exit(0);
    }
    
}
