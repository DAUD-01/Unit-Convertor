package com.hub;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application { // this is the main class that launches the application, it extends the Application class from JavaFX which provides the necessary methods to create and manage a JavaFX application

    @Override
    public void start(Stage stage) throws Exception {

        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/fxml/dashboard.fxml"));

        Scene scene = new Scene(loader.load());

        scene.getStylesheets().add(getClass().getResource("/css/style.css").toExternalForm());

        stage.setTitle("Convertor+");
        stage.setScene(scene);
        stage.setFullScreen(true);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}