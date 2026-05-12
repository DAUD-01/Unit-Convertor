package com.hub.ui.controllers;

import com.hub.models.RootData;
import com.hub.utils.FileLoader;
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

// Ikonli Imports
import org.kordamp.ikonli.javafx.FontIcon;

public class DashboardController {

    @FXML
    private VBox root;

    @FXML
    public void initialize() {
        // Cleaning the root to ensure the white bar/ghost nodes are gone
        root.getChildren().removeIf(node -> !(node instanceof javafx.scene.control.Label));
        renderDashboard();
    }

    private void renderDashboard() {
        FlowPane flow = new FlowPane();
        flow.setHgap(40);
        flow.setVgap(40);
        flow.setAlignment(Pos.CENTER);

        // This width allows for a clean 3 or 4 column grid on a 1080p screen
        flow.setPrefWrapLength(1100);

        String[] categories = {
                "Common", "Finance", "Science",
                "Computing", "Physics", "Health", "Tools"
        };

        for (String category : categories) {
            Button card = createCategoryCard(category);
            flow.getChildren().add(card);
        }

        root.getChildren().add(flow);
    }

    private Button createCategoryCard(String title) {
        Button btn = new Button(title);
        btn.getStyleClass().add("category-card");

        // Square dimensions matching your sample image
        btn.setPrefSize(240, 240);
        btn.setMinSize(240, 240);

        // Positions Icon on Top, Text at Bottom
        btn.setContentDisplay(ContentDisplay.TOP);
        btn.setGraphicTextGap(20);

        // Icon Logic
        FontIcon icon = new FontIcon();
        icon.getStyleClass().add("category-icon");

        // Mapping icons based on FontAwesome 5 literals
        switch (title) {
            case "Common" -> icon.setIconLiteral("fas-layer-group");
            case "Finance" -> icon.setIconLiteral("fas-wallet");
            case "Science" -> icon.setIconLiteral("fas-microscope");
            case "Computing" -> icon.setIconLiteral("fas-desktop");
            case "Physics" -> icon.setIconLiteral("fas-atom");
            case "Health" -> icon.setIconLiteral("fas-heartbeat");
            case "Tools" -> icon.setIconLiteral("fas-tools");
        }

        btn.setGraphic(icon);
        btn.setOnAction(e -> openCategory(title));

        return btn;
    }

    public void openCategory(String category) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/category.fxml"));
            VBox view = loader.load();

            CategoryController controller = loader.getController();
            RootData data = FileLoader.loadData("units.json");

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
            Scene scene = new Scene(view, 1920, 1080);

            // Re-apply stylesheets to the new scene
            scene.getStylesheets().add(getClass().getResource("/css/style.css").toExternalForm());

            stage.setScene(scene);
            stage.setFullScreen(true);

        } catch (Exception e) {
            System.err.println("Failed to load category: " + category);
            e.printStackTrace();
        }
    }
}