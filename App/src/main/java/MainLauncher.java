
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Window;

public class MainLauncher extends Application {

    public static Stage primaryStage;
    public static Window primaryStageWindow;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        this.primaryStage = primaryStage;

        Parent root = new AnchorPane();
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStageWindow = primaryStage.getScene().getWindow();

        primaryStage.setOnCloseRequest(val -> {
            Reminder.serializeList();
            Platform.exit();
            System.exit(0);
        });

        scene.setRoot(FXMLLoader.load(getClass().getResource("MainPane.fxml")));

        primaryStage.show();
        primaryStage.requestFocus();

        primaryStage.setMinHeight(scene.getHeight());
        primaryStage.setMinWidth(scene.getWidth());
    }
}
