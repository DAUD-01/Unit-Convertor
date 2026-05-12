package com.hub.ui.controllers;

import com.hub.models.RootData;
import com.hub.utils.FileLoader;
import com.hub.ui.utils.FXAnimation;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.control.ContentDisplay;

public class DashboardController {

    @FXML
    private VBox root;

    @FXML
    public void initialize() {
        renderDashboard();
    }

    private void renderDashboard() {
        // 1. Setup the FlowPane (The Grid)
        FlowPane flow = new FlowPane();
        flow.setHgap(35); // Horizontal spacing between cards
        flow.setVgap(35); // Vertical spacing between rows
        flow.setAlignment(Pos.CENTER);

        // This ensures a 4x2 or 3x3 layout depending on window size
        flow.setPrefWrapLength(1000);

        String[] categories = {
                "Common", "Finance", "Science",
                "Computing", "Physics", "Health", "Tools"
        };

        for (String category : categories) {
            Button card = createCard(category);
            flow.getChildren().add(card);
        }

        // 2. Add the grid to your root layout
        root.getChildren().add(flow);
    }

    private Button createCard(String title) {
        Button btn = new Button(title);

        // Apply the CSS class for the glow and dark background
        btn.getStyleClass().add("category-card");

        // 3. Match the Geometry (Square Cards)
        btn.setPrefSize(220, 220);
        btn.setMinSize(220, 220);

        // Prepares the card for icons (Top) and Text (Bottom)
        btn.setContentDisplay(ContentDisplay.TOP);
        btn.setGraphicTextGap(20);

        // 4. Navigation Logic
        btn.setOnAction(e -> openCategory(title));

        return btn;
    }

    public void openCategory(String category) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/category.fxml"));
            VBox view = loader.load();

            CategoryController controller = loader.getController();
            RootData data = FileLoader.loadData("units.json");

            // Map data based on category name
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

            Stage stage = (Stage) root.getScene().getWindow();
            stage.setScene(new Scene(view, 1920, 1080));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}