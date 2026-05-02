package com.hub;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage stage) {
        System.out.println("START METHOD RUNNING");

        var data = com.hub.utils.FileLoader.loadData("units.json");

        if (data != null && data.Common != null) {

            System.out.println("=== COMMON CATEGORY ===");

            for (String key : data.Common.keySet()) {
                System.out.println("SubCategory: " + key);

                var sub = data.Common.get(key);

                if (sub.units != null) {
                    for (String unit : sub.units.keySet()) {
                        System.out.println(" - " + unit + " = " + sub.units.get(unit));
                    }
                }
            }
        }

        Label label = new Label("Convertor app is running");

        StackPane root = new StackPane(label);
        Scene scene = new Scene(root, 900, 600);

        stage.setTitle("Converter+");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}