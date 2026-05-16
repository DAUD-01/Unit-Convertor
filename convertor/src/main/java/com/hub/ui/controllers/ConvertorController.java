package com.hub.ui.controllers;

import com.hub.core.ConversionEngine;
import com.hub.models.Category;
import com.hub.ui.utils.FXAnimation;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.Map;

public class ConvertorController {

    @FXML
    private VBox root;

    @FXML
    private TextField inputField;

    @FXML
    private ComboBox<String> fromBox;

    @FXML
    private ComboBox<String> toBox;

    @FXML
    private Label resultLabel;

    private Category currentCategory;
    private Map<String, Category> parentGroup;

    @FXML
    public void initialize() {
        inputField.textProperty().addListener((obs, oldVal, newVal) -> autoConvert());
        fromBox.setOnAction(e -> autoConvert());
        toBox.setOnAction(e -> autoConvert());
    }

    public void setParentData(Map<String, Category> group) {
        this.parentGroup = group;
    }

    public void setCategory(Category category) {
        this.currentCategory = category;
        fromBox.getItems().clear();
        toBox.getItems().clear();

        if (category != null && category.units != null) {
            fromBox.getItems().addAll(category.units.keySet());
            toBox.getItems().addAll(category.units.keySet());

            if (!fromBox.getItems().isEmpty())
                fromBox.setValue(fromBox.getItems().get(0));
            if (toBox.getItems().size() > 1)
                toBox.setValue(toBox.getItems().get(1));
        }
    }

    private void autoConvert() {
        if (currentCategory == null)
            return;
        try {
            String text = inputField.getText().trim();
            if (text.isEmpty()) {
                resultLabel.setText("0.00");
                return;
            }
            double value = Double.parseDouble(text);
            double result = ConversionEngine.convert(value, fromBox.getValue(), toBox.getValue(), currentCategory);
            resultLabel.setText(String.format("%.4f", result));
        } catch (Exception e) {
            resultLabel.setText("Error");
        }
    }

    @FXML
    private void goback() {
        try {
            // 1. Load the FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/category.fxml"));
            VBox view = loader.load();

            // 2. Pass the data back to the controller
            CategoryController controller = loader.getController();
            controller.setCategory(parentGroup);

            // 3. Find the stage safely
            Stage stage = (Stage) inputField.getScene().getWindow();

            // 4. Update the Scene
            Scene scene = new Scene(view);
            scene.getStylesheets().add(getClass().getResource("/css/style.css").toExternalForm());

            stage.setScene(scene);
            stage.setFullScreen(true);
            FXAnimation.fadeIn(view);

        } catch (Exception e) {
            System.err.println("Navigation Failed: " + e.getMessage());
            e.printStackTrace();
        }
    }
}