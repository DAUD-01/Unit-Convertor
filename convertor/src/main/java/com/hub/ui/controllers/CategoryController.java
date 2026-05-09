package com.hub.ui.controllers;

import com.hub.models.Category;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

import java.util.Map;

public class CategoryController {

    @FXML
    private VBox root;

    private Map<String, Category> currentCategories;

    public void setCategory(Map<String, Category> categories) {
        this.currentCategories = categories;

        System.out.println("Loaded Category Group");

        loadSubCategories();
    }

    private void loadSubCategories() {
        root.getChildren().clear();

        if (currentCategories == null)
            return;

        for (String sub : currentCategories.keySet()) {

            Button btn = new Button(sub);

            btn.setOnAction(e -> openConvertor(sub));

            root.getChildren().add(btn);
        }
    }

    private void openConvertor(String subCategory) {

    }
}