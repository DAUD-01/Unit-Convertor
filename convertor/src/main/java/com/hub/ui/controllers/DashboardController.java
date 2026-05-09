package com.hub.ui.controllers;

import com.hub.models.RootData;
import com.hub.utils.FileLoader;
import com.hub.ui.utils.FXAnimation;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class DashboardController {

    @FXML
    private VBox root;

    @FXML
    public void initialize() {

        createCategoryButton();
    }

    public void createCategoryButton() {

        String[] categories = {
                "Common",
                "Finance",
                "Science",
                "Computing",
                "Physics",
                "Health",
                "Tools"
        };

        for (String category : categories) {

            Button btn = new Button(category);
            btn.setPrefWidth(260);
            btn.setPrefHeight(55);
            btn.getStyleClass().add("card");

            btn.setOnAction(e -> openCategory(category));

            root.getChildren().add(btn);
        }
    }

    public void openCategory(String category) {

        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/category.fxml"));

            VBox view = loader.load();

            CategoryController controller = loader.getController();

            RootData data = FileLoader.loadData("units.json");

            switch (category) {
                case "Common" -> controller.setCategory(data.Common);
                case "Finance" -> controller.setCategory(data.Finance);
                case "Science" -> controller.setCategory(data.Science);
                case "Computing" -> controller.setCategory(data.Computing);
                case "Physics" -> controller.setCategory(data.Physics);
                case "Health" -> controller.setCategory(data.Health);
                case "Tools" -> controller.setCategory(data.Tools);
            }

            FXAnimation.fadeIn(view);
            FXAnimation.fadeIn(view);

            Stage stage = (Stage) root.getScene().getWindow();

            stage.setScene(new Scene(view, 1920, 1080));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}