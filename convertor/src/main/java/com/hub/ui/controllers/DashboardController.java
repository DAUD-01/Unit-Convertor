package com.hub.ui.controllers;

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

            btn.setOnAction(e -> openCategory(category));

            root.getChildren().add(btn);
        }
    }

    public void openCategory(String category) {

        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/category.fxml"));

            VBox view = loader.load();

            CategoryController controller = loader.getController();

            controller.setCategory(category);

            Stage stage = (Stage) root.getScene().getWindow();

            stage.setScene(new Scene(view, 900, 600));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}