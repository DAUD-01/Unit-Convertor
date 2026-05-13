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

    @FXML
    public void initialize() {
        // Real-time conversion as you type
        inputField.textProperty().addListener((obs, oldVal, newVal) -> autoConvert());

        // Instant conversion when dropdowns change
        fromBox.setOnAction(e -> autoConvert());
        toBox.setOnAction(e -> autoConvert());
    }

    public void setCategory(Category category) {
        this.currentCategory = category;

        fromBox.getItems().clear();
        toBox.getItems().clear();

        if (category != null && category.units != null) {
            fromBox.getItems().addAll(category.units.keySet());
            toBox.getItems().addAll(category.units.keySet());

            // Set default selections safely
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
            String from = fromBox.getValue();
            String to = toBox.getValue();

            double result = ConversionEngine.convert(value, from, to, currentCategory);

            // Formatting to 4 decimal places for a cleaner UI
            resultLabel.setText(String.format("%.4f", result));

        } catch (NumberFormatException e) {
            resultLabel.setText("Invalid Number");
        } catch (Exception e) {
            resultLabel.setText("Error");
            e.printStackTrace();
        }
    }

    @FXML
    private void goBack() {
        try {
            // Goes back to the Dashboard (or you can link to Category page)
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/category.fxml"));
            VBox view = loader.load();

            Stage stage = (Stage) root.getScene().getWindow();
            FXAnimation.fadeIn(view);

            Scene scene = new Scene(view, 1920, 1080);
            scene.getStylesheets().add(getClass().getResource("/css/style.css").toExternalForm());

            stage.setScene(scene);
            stage.setFullScreen(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}