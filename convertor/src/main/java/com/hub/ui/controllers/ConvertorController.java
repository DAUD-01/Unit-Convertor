package com.hub.ui.controllers;

import com.hub.core.ConversionEngine;
import com.hub.core.AlgorithmEngine;
import com.hub.core.FormulaEngine;
import com.hub.models.Category;
import com.hub.services.AlgorithmService;
import com.hub.services.FormulaService;
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

            String type = currentCategory.type; // "factor", "formula", or "algorithm"
            String from = fromBox.getValue();
            String to = toBox.getValue();

            if ("factor".equals(type)) {
                double value = Double.parseDouble(text);
                double result = ConversionEngine.convert(value, from, to, currentCategory);
                resultLabel.setText(String.format("%.4f", result));

            } else if ("formula".equals(type)) {
                FormulaService service = new FormulaService();
                // Handle Temperature specifically
                if (currentCategory.getName().equalsIgnoreCase("Temperature")) {
                    double val = Double.parseDouble(text);
                    double res = service.calculateTemperature(val, from, to);
                    resultLabel.setText(String.format("%.2f", res));
                }
                // Handle Health/Finance (BMI, Tax, etc.)
                else {
                    // If the formula needs multiple inputs (e.g. BMI),
                    // users should enter them like "70, 1.75"
                    double[] inputs = parseMultipleInputs(text);
                    double res = service.calculate(currentCategory.getName(), inputs);
                    resultLabel.setText(String.format("%.2f", res));
                }

            } else if ("algorithm".equals(type)) {
                AlgorithmService service = new AlgorithmService();
                if (currentCategory.getName().equalsIgnoreCase("Number Base")) {
                    Object res = service.execute("numberbase", text, from, to);
                    resultLabel.setText(res.toString());
                } else {
                    Object res = service.execute(currentCategory.getName(), text);
                    resultLabel.setText(res.toString());
                }
            }
        } catch (Exception e) {
            resultLabel.setText("Error");
        }
    }

    // Helper to handle formulas that need more than one number
    private double[] parseMultipleInputs(String input) {
        String[] parts = input.split(",");
        double[] vals = new double[parts.length];
        for (int i = 0; i < parts.length; i++) {
            vals[i] = Double.parseDouble(parts[i].trim());
        }
        return vals;
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
            Scene scene = new Scene(view, 1920, 1080);
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