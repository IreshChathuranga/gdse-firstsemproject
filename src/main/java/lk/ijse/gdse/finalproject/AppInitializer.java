package lk.ijse.gdse.finalproject;

import javafx.application.Application;
import javafx.concurrent.Task;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class AppInitializer extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws IOException {
        Scene loadingScene = new Scene(FXMLLoader.load(getClass().getResource("/view/LoadingScreen.fxml")));
        stage.setScene(loadingScene);
        stage.show();
        Task<Scene> loadingTask = new Task<>() {
            @Override
            protected Scene call() throws Exception {
                FXMLLoader fxmlLoader = new FXMLLoader(AppInitializer.class.getResource("/view/LogIn.fxml"));
                return new Scene(fxmlLoader.load());
            }
        };
        loadingTask.setOnSucceeded(event -> {
            try {
                Scene value = loadingTask.getValue();
                stage.setTitle("Learn Drive");
                stage.setScene(value);

                Image image = new Image(getClass().getResourceAsStream("/images/pexels-taras-makarenko-188506-593172.jpg"));
                stage.getIcons().add(image);
            } catch (Exception e) {
                System.err.println("Error while loading the scene: " + e.getMessage());
            }
        });
        loadingTask.setOnFailed(event -> {
            System.err.println("Failed to load the main layout.");
            event.getSource().getException().printStackTrace();
        });
        new Thread(loadingTask).start();
    }
}
