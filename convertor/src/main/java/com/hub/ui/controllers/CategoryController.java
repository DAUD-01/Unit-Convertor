package com.hub.ui.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

public class CategoryController {
    @FXML
    private VBox root;

    private String currentCategory;

    public void setCategory(String category) {
        this.currentCategory = category;

        System.out.println("Loaded Category: " + category);

        loadSubCategories();
    }

    private void loadSubCategories() {
        root.getChildren().clear();

        String[] subCategories = {
                "Length", "Weight", "Area", "Volume"
        };

        for (String sub : subCategories) {
            Button btn = new Button(sub);

            btn.setOnAction(e -> openConvertor(sub));

            root.getChildren().add(btn);
        }
    }

    private void openConvertor(String subCategory) {
        System.out.println("Opening convertor: " + subCategory);
    }
}
