package com.hub.ui.controllers;

import com.hub.models.Category;
import com.hub.ui.utils.FXAnimation;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.Map;

public class CategoryController {

    @FXML
    private VBox root;

    private Map<String, Category> currentCategories;

    public void setCategory(Map<String, Category> categories) {
        this.currentCategories = categories;
        loadSubCategories();
    }

    private void loadSubCategories() {
        root.getChildren().removeIf(node -> node instanceof FlowPane || node instanceof Button);

        if (currentCategories == null) {
            System.err.println("Warning: currentCategories is null. Nothing to render.");
            return;
        }

        FlowPane flow = new FlowPane();
        flow.setHgap(35);
        flow.setVgap(35);
        flow.setAlignment(Pos.CENTER);
        flow.setPrefWrapLength(1200);

        for (String sub : currentCategories.keySet()) {
            Button btn = new Button(sub);
            btn.getStyleClass().add("category-card");
            btn.setPrefSize(240, 240);
            btn.setMinSize(240, 240);
            btn.setContentDisplay(ContentDisplay.CENTER);

            btn.setOnAction(e -> openConvertor(sub));
            flow.getChildren().add(btn);
        }

        Button backBtn = new Button("← Back to Dashboard");
        backBtn.getStyleClass().add("back-button");
        VBox.setMargin(backBtn, new javafx.geometry.Insets(40, 0, 0, 0));
        backBtn.setOnAction(e -> goBack());

        root.getChildren().addAll(flow, backBtn);
    }

    private void openConvertor(String subCategoryName) {
        try {
            Category selected = currentCategories.get(subCategoryName);
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/convertor.fxml"));
            VBox view = loader.load();

            ConvertorController controller = loader.getController();

            // Pass BOTH the unique name string and the model data object
            controller.setCategory(subCategoryName, selected);
            controller.setParentData(currentCategories);

            Stage stage = (Stage) root.getScene().getWindow();
            Scene scene = new Scene(view, 1920, 1080);
            scene.getStylesheets().add(getClass().getResource("/css/style.css").toExternalForm());

            stage.setScene(scene);
            stage.setFullScreen(true);
            FXAnimation.fadeIn(view);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void goBack() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/dashboard.fxml"));
            VBox view = loader.load();
            Stage stage = (Stage) root.getScene().getWindow();
            Scene scene = new Scene(view);
            scene.getStylesheets().add(getClass().getResource("/css/style.css").toExternalForm());
            FXAnimation.fadeIn(view);
            stage.setScene(scene);
            stage.setFullScreen(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}