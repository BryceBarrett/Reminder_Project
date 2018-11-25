import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.Parent;

public class MainLauncher extends Application {
    public static void main(String[] args) {
        Application.launch(MainLauncher.class, args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {


        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("MainPane.fxml"));
        primaryStage.setScene(new Scene(root));
        primaryStage.show();

    }
}