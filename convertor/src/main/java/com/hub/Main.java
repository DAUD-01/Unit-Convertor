package com.hub;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import java.io.InputStream;

public class Main extends Application { // this is the main class that launches the application, it extends the
                                        // Application class from JavaFX which provides the necessary methods to create
                                        // and manage a JavaFX application

    @Override
    public void start(Stage stage) throws Exception {

        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/fxml/dashboard.fxml"));

        Scene scene = new Scene(loader.load());

        scene.getStylesheets().add(getClass().getResource("/css/style.css").toExternalForm());

        stage.setTitle("Convertor+");
        stage.setScene(scene);
        stage.setFullScreen(true);

        // --- ADD APPLICATION WINDOW ICON ---
        try (InputStream iconStream = getClass().getResourceAsStream("/assets/icon.png")) {
            if (iconStream != null) {
                stage.getIcons().add(new Image(iconStream));
            } else {
                System.err.println("Warning: Icon file not found at /src/main/resources/assets/icon.png");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}