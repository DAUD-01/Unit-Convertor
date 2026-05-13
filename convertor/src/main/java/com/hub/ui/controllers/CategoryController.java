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

    /**
     * Injects the data and triggers the UI render.
     * 
     * @param categories The map of sub-categories (e.g., Length, Mass, etc.)
     */
    public void setCategory(Map<String, Category> categories) {
        this.currentCategories = categories;
        loadSubCategories();
    }

    /**
     * Dynamically builds the grid of sub-category buttons.
     */
    private void loadSubCategories() {
        // 1. Clean the view of previous dynamic elements but keep the static FXML title
        root.getChildren().removeIf(node -> node instanceof FlowPane || node instanceof Button);

        if (currentCategories == null) {
            System.err.println("Warning: currentCategories is null. Nothing to render.");
            return;
        }

        // 2. Setup the Grid Layout (FlowPane)
        FlowPane flow = new FlowPane();
        flow.setHgap(35);
        flow.setVgap(35);
        flow.setAlignment(Pos.CENTER);
        flow.setPrefWrapLength(1200); // Prevents stretching on 1080p

        // 3. Create a card for each Sub-Category
        for (String sub : currentCategories.keySet()) {
            Button btn = new Button(sub);

            // Apply consistent styling
            btn.getStyleClass().add("category-card");
            btn.setPrefSize(240, 240);
            btn.setMinSize(240, 240);
            btn.setContentDisplay(ContentDisplay.CENTER);

            // Pass the sub-category name to the click handler
            btn.setOnAction(e -> openConvertor(sub));

            flow.getChildren().add(btn);
        }

        // 4. Create and Style the Back Button
        Button backBtn = new Button("← Back to Dashboard");
        backBtn.getStyleClass().add("back-button");

        // Add vertical spacing so it's not glued to the grid
        VBox.setMargin(backBtn, new javafx.geometry.Insets(40, 0, 0, 0));

        backBtn.setOnAction(e -> goBack());

        // 5. Add components to the VBox root
        root.getChildren().addAll(flow, backBtn);
    }

    /**
     * Navigates to the actual conversion screen.
     * Passes the specific category and the whole group for the return trip.
     */
    private void openConvertor(String subCategoryName) {
        try {
            Category selected = currentCategories.get(subCategoryName);
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/convertor.fxml"));
            VBox view = loader.load();

            ConvertorController controller = loader.getController();
            controller.setCategory(selected);
            // CRITICAL: Hand off the whole map so we can come back to it
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
            FXAnimation.fadeIn(view);

            Scene scene = new Scene(view, 1920, 1080);
            scene.getStylesheets().add(getClass().getResource("/css/style.css").toExternalForm());

            FXAnimation.fadeIn(view);
            stage.setScene(scene);
            stage.setFullScreen(true);

        } catch (Exception e) {
            System.err.println("Error navigating back to Dashboard:");
            e.printStackTrace();
        }
    }
}