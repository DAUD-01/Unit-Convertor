package com.hub.ui.controllers;

import com.hub.models.Category;
import com.hub.ui.utils.FXAnimation;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
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
        // 1. Clean the view but keep the title if you have one in FXML
        root.getChildren().removeIf(node -> node instanceof FlowPane || node instanceof Button);

        // 2. Setup the Grid Layout
        FlowPane flow = new FlowPane();
        flow.setHgap(35);
        flow.setVgap(35);
        flow.setAlignment(Pos.CENTER);
        flow.setPrefWrapLength(1200);

        if (currentCategories == null)
            return;

        // 3. Create a card for each Sub-Category (Length, Weight, etc.)
        for (String sub : currentCategories.keySet()) {
            Button btn = new Button(sub);

            // Using the same style class as the dashboard for consistency
            btn.getStyleClass().add("category-card");

            // Matching the square geometry from your sample image
            btn.setPrefSize(240, 240);
            btn.setMinSize(240, 240);
            btn.setContentDisplay(ContentDisplay.CENTER); // Centered text since no icons here

            btn.setOnAction(e -> openConvertor(sub));

            flow.getChildren().add(btn);
        }

        // 4. Add a Back Button to return to the Main Dashboard
        Button backBtn = new Button("Back to Hub");
        backBtn.getStyleClass().add("back-button"); // Use a simpler style or a specific 'back' style
        backBtn.setTranslateY(50); // Give it some space from the grid
        backBtn.setOnAction(e -> goBack());

        root.getChildren().addAll(flow, backBtn);
    }

    private void openConvertor(String subCategory) {
        try {
            Category selected = currentCategories.get(subCategory);
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/convertor.fxml"));
            VBox view = loader.load();

            ConvertorController controller = loader.getController();
            controller.setCategory(selected);

            Stage stage = (Stage) root.getScene().getWindow();
            FXAnimation.fadeIn(view);

            Scene scene = new Scene(view, 1920, 1080);
            // Apply the CSS to the new scene
            scene.getStylesheets().add(getClass().getResource("/css/style.css").toExternalForm());

            stage.setScene(scene);
            stage.setFullScreen(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void goBack() {
        try {
            // 1. Load the Dashboard FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/dashboard.fxml"));
            VBox view = loader.load();

            // 2. Get the current Stage
            Stage stage = (Stage) root.getScene().getWindow();

            // 3. Apply the Animation
            FXAnimation.fadeIn(view);

            // 4. Create new Scene and Re-apply CSS
            Scene scene = new Scene(view, 1920, 1080);
            String css = getClass().getResource("/css/style.css").toExternalForm();
            scene.getStylesheets().add(css);

            // 5. Set Scene and Force Full Screen
            stage.setScene(scene);
            stage.setFullScreen(true);

        } catch (Exception e) {
            System.err.println("Error navigating back to Dashboard:");
            e.printStackTrace();
        }
    }
}