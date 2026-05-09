package com.hub.ui.controllers;

import com.hub.models.Category;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.Scene;

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
        try {
            Category selected = currentCategories.get(subCategory);

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/convertor.fxml"));

            VBox view = loader.load();

            ConvertorController controller = loader.getController();
            controller.setCategory(selected);

            Stage stage = (Stage) root.getScene().getWindow();
            stage.setScene(new Scene(view, 900, 600));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}