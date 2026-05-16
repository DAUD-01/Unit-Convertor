package com.hub.ui.controllers;

import com.hub.models.Category;
import com.hub.ui.utils.FXAnimation;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;

import java.util.Map;

public class CategoryController {

    @FXML
    private VBox root;
    @FXML
    private Label titleLabel; // Injected element field reference from FXML

    private Map<String, Category> currentCategories;

    // Modified signature to receive the clicked Category name string from Dashboard
    public void setCategory(String title, Map<String, Category> categories) {
        this.currentCategories = categories;

        // Update the header label instantly before rendering nodes
        if (titleLabel != null && title != null) {
            titleLabel.setText(title);
        }

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

    @FXML
    private void goBack() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/dashboard.fxml"));
            VBox view = loader.load();

            Scene currentScene = root.getScene();
            if (currentScene != null) {
                currentScene.setFill(javafx.scene.paint.Color.web("#101d2d"));
                currentScene.setRoot(view);

                javafx.stage.Stage stage = (javafx.stage.Stage) currentScene.getWindow();
                if (stage != null) {
                    stage.setFullScreen(true);
                }

                FXAnimation.fadeIn(view);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void openConvertor(String subCategoryName) {
        try {
            Category selected = currentCategories.get(subCategoryName);
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/convertor.fxml"));
            VBox view = loader.load();

            ConvertorController controller = loader.getController();
            controller.setCategory(subCategoryName, selected);

            // FIX: Extract the current category name (e.g., "Common") and pass it as the
            // first argument
            String currentTitle = (titleLabel != null) ? titleLabel.getText() : "Category";
            controller.setParentData(currentTitle, currentCategories);

            Scene currentScene = root.getScene();
            if (currentScene != null) {
                currentScene.setFill(javafx.scene.paint.Color.web("#101d2d"));
                currentScene.setRoot(view);

                // Maintain full-screen lock state
                javafx.stage.Stage stage = (javafx.stage.Stage) currentScene.getWindow();
                if (stage != null) {
                    stage.setFullScreen(true);
                }

                FXAnimation.fadeIn(view);
            }
        } catch (Exception e) {
            System.err.println("Failed to slide into convertor page layout view for: " + subCategoryName);
            e.printStackTrace();
        }
    }
}