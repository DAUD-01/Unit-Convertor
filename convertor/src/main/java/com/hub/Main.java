package com.hub;

import com.hub.core.ConversionEngine;
import com.hub.utils.FileLoader;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage stage) {
        System.out.println("Mic check 123");

        // var data = com.hub.utils.FileLoader.loadData("units.json");

        // if (data != null && data.Science != null) {

        // System.out.println("=== SCIENCE CATEGORY ===");

        // for (String key : data.Science.keySet()) {
        // System.out.println("SubCategory: " + key);

        // var sub = data.Science.get(key);

        // if (sub.units != null) {
        // for (String unit : sub.units.keySet()) {
        // System.out.println(" - " + unit + " = " + sub.units.get(unit));
        // }
        // }
        // }
        // }

        // Label label = new Label("Convertor app is running");

        // StackPane root = new StackPane(label);
        // Scene scene = new Scene(root, 900, 600);

        // stage.setTitle("Converter+");
        // stage.setScene(scene);
        // stage.show();
    }

    public static void main(String[] args) {
        var data = FileLoader.loadData("units.json");

        var length = data.Common.get("Length");

        double result = ConversionEngine.convert(
                100,
                "cm",
                "km",
                length);

        System.out.println("Result: " + result);

        launch();

    }
}