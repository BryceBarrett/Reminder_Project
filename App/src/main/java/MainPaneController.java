/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.Semaphore;
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
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

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
    @FXML
    private HBox rootHbox;

    public static Semaphore remListSem = new Semaphore(1);
    public static ObservableList<Reminder> remList;

    //private ObservableList<Reminder> remList = FXCollections.observableArrayList();
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        reminderListView.setCellFactory(param -> {
            ListCell<Reminder> cell = new ListCell<Reminder>() {
                @Override
                protected void updateItem(Reminder item, boolean empty) {
                    super.updateItem(item, empty);
                    if (item != null) {
                        setText(item.getTime().toString() + "\n" + item.getMessage());
                    }
                }
            };
            return cell;
        });
        try {
            remListSem.acquire();
            remList = FXCollections.observableArrayList();
            Reminder.getListSem().acquire();
            for (Reminder rem : Reminder.getReminderList()) {
                remList.add(rem);
            }
            Reminder.getListSem().release();
            reminderListView.setItems(remList);
        } catch (InterruptedException e) {
        }
        remListSem.release();

        /*  rootHbox.focusedProperty().addListener(listener -> {
            if (rootHbox.focusedProperty().getValue()) {
                final ObservableList<Reminder> r = FXCollections.observableArrayList();
                for (Reminder rem : Reminder.getReminderList()) {
                    r.add(rem);
                }
                reminderListView.setItems(r);
                reminderListView.refresh();
            }
        }); */
        Thread thread = new Thread(() -> {
            while (true) {
                try {
                    remListSem.acquire();
                    remList = FXCollections.observableArrayList();
                    Reminder.getListSem().acquire();
                    for (Reminder rem : Reminder.getReminderList()) {
                        remList.add(rem);
                    }
                    Reminder.getListSem().release();
                    reminderListView.setItems(remList);
                    remListSem.release();
                    reminderListView.refresh();
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                }
            }
        });
        thread.start();
    }

    @FXML
    private void newReminderAction(ActionEvent event) throws Exception {

        Parent root = FXMLLoader.load(getClass().getResource("PopOut.fxml"));
        Scene scene = new Scene(root);
        Stage stage = new Stage();

        //MainLauncher.primaryStage.hide();
        //MainLauncher.primaryStage.setScene(scene);
        stage.setScene(scene);

        //MainLauncher.primaryStage.show();
        //MainLauncher.primaryStage.requestFocus();
        stage.show();
        //stage.initOwner(MainLauncher.primaryStageWindow);

        // MainLauncher.primaryStage.setMinHeight(scene.getHeight());
        //MainLauncher.primaryStage.setMinWidth(scene.getWidth());
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
