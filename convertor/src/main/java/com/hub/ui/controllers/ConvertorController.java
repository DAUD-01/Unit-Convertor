package com.hub.ui.controllers;

import com.hub.core.ConversionEngine;
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

    private String categoryName;
    private Category currentCategory;
    private Map<String, Category> parentGroup;

    private final FormulaService formulaService = new FormulaService();
    private final AlgorithmService algorithmService = new AlgorithmService();

    @FXML
    public void initialize() {
        inputField.textProperty().addListener((obs, oldVal, newVal) -> autoConvert());
        fromBox.setOnAction(e -> autoConvert());
        toBox.setOnAction(e -> autoConvert());
    }

    public void setParentData(Map<String, Category> group) {
        this.parentGroup = group;
    }

    // CHANGED: Now accepts the name (e.g. "BMI" or "Temperature")
    public void setCategory(String name, Category category) {
        this.categoryName = name;
        this.currentCategory = category;
        fromBox.getItems().clear();
        toBox.getItems().clear();

        String type = category.type != null ? category.type : "factor";

        // UI Adaptation Logic
        if ("formula".equals(type) || "algorithm".equals(type)) {
            fromBox.setDisable(true);
            toBox.setDisable(true);

            if (category.inputs != null) {
                inputField.setPromptText("Enter: " + String.join(", ", category.inputs));
            } else if (name.toLowerCase().contains("roman")) {
                inputField.setPromptText("Enter Number or Roman Numeral");
            } else {
                inputField.setPromptText("Enter values separated by commas");
            }
        } else {
            fromBox.setDisable(false);
            toBox.setDisable(false);
            inputField.setPromptText("0.00");

            if (category.units != null) {
                fromBox.getItems().addAll(category.units.keySet());
                toBox.getItems().addAll(category.units.keySet());

                if (!fromBox.getItems().isEmpty())
                    fromBox.setValue(fromBox.getItems().get(0));
                if (toBox.getItems().size() > 1)
                    toBox.setValue(toBox.getItems().get(1));
            }
        }
    }

    private void autoConvert() {
        if (currentCategory == null)
            return;
        String text = inputField.getText().trim();
        if (text.isEmpty()) {
            resultLabel.setText("0.00");
            return;
        }

        try {
            String type = currentCategory.type != null ? currentCategory.type : "factor";

            // 1. FORMULAS (Finance, Health)
            if ("formula".equals(type)) {
                String[] parts = text.split(",");
                double[] inputs = new double[parts.length];
                for (int i = 0; i < parts.length; i++)
                    inputs[i] = Double.parseDouble(parts[i].trim());

                double result = formulaService.calculate(categoryName, inputs);
                resultLabel.setText(String.format("%.4f", result));
                return;
            }

            // 2. ALGORITHMS (Age, Roman)
            if ("algorithm".equals(type)) {
                if (categoryName.equalsIgnoreCase("AgeCalculator")) {
                    Object res = algorithmService.execute("ageCalculator", text);
                    resultLabel.setText(res + " Years");
                } else if (categoryName.toLowerCase().contains("roman")) {
                    try {
                        int num = Integer.parseInt(text);
                        resultLabel.setText((String) algorithmService.execute("toRoman", num));
                    } catch (NumberFormatException ex) {
                        resultLabel.setText(algorithmService.execute("fromRoman", text.toUpperCase()).toString());
                    }
                }
                return;
            }

            // 3. TEMPERATURE (Math Intercept)
            if (categoryName.equalsIgnoreCase("Temperature")) {
                double val = Double.parseDouble(text);
                String from = fromBox.getValue() != null ? fromBox.getValue() : "c";
                String to = toBox.getValue() != null ? toBox.getValue() : "c";

                // Convert to Celsius first
                double c = val;
                if (from.toLowerCase().startsWith("f"))
                    c = (val - 32) * 5 / 9;
                else if (from.toLowerCase().startsWith("k"))
                    c = val - 273.15;

                // Convert Celsius to Target
                double res = c;
                if (to.toLowerCase().startsWith("f"))
                    res = c * 9 / 5 + 32;
                else if (to.toLowerCase().startsWith("k"))
                    res = c + 273.15;

                resultLabel.setText(String.format("%.2f", res));
                return;
            }

            // 4. NUMBER BASE (Computing Intercept)
            if (categoryName.equalsIgnoreCase("NumberBase") || categoryName.equalsIgnoreCase("Number Base")) {
                int radixFrom = getRadix(fromBox.getValue());
                int radixTo = getRadix(toBox.getValue());
                long decimal = Long.parseLong(text, radixFrom);
                resultLabel.setText(Long.toString(decimal, radixTo).toUpperCase());
                return;
            }

            // 5. STANDARD FACTOR RATIOS (Length, Weight, etc.)
            double value = Double.parseDouble(text);
            double result = ConversionEngine.convert(value, fromBox.getValue(), toBox.getValue(), currentCategory);
            resultLabel.setText(String.format("%.4f", result));

        } catch (Exception e) {
            resultLabel.setText("..."); // Fails softly while user is typing
        }
    }

    private int getRadix(String base) {
        if (base == null)
            return 10;
        base = base.toLowerCase();
        if (base.contains("bin"))
            return 2;
        if (base.contains("hex"))
            return 16;
        if (base.contains("oct"))
            return 8;
        return 10;
    }

    @FXML
    private void goback() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/category.fxml"));
            VBox view = loader.load();

            CategoryController controller = loader.getController();
            controller.setCategory(parentGroup);

            Stage stage = (Stage) inputField.getScene().getWindow();
            Scene scene = new Scene(view, 1920, 1080);
            scene.getStylesheets().add(getClass().getResource("/css/style.css").toExternalForm());

            stage.setScene(scene);
            stage.setFullScreen(true);
            FXAnimation.fadeIn(view);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}