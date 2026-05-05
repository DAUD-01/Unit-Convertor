package com.hub.ui.controllers;

import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.fxml.FXML;

public class DashboardController {
    @FXML
    private VBox root;

    @FXML
    public void initialize() {
        System.out.println("Dashboard loaded");

        createCategoryButton();
    }

    public void createCategoryButton() {
        String[] categories = {
                "Common", "Finance", "Science", "Computing", "Physics", "Health", "Tools"
        };

        for (String category : categories) {
            Button btn = new Button(category);
            btn.setOnAction(e -> openCategory(category));

            root.getChildren().add(btn);
        }
    }

    public void openCategory(String category) {
        System.out.println("Clicked: " + category);

    }
}
