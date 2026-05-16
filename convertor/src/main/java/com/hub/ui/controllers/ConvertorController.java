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

    // Accepts both the string key name and the Category config payload
    public void setCategory(String name, Category category) {
        this.categoryName = name;
        this.currentCategory = category;

        fromBox.getItems().clear();
        toBox.getItems().clear();

        String type = (category != null && category.type != null) ? category.type : "factor";

        // Adjust UI configuration based on specific system requirements
        if ("formula".equals(type) || "algorithm".equals(type)) {
            fromBox.setDisable(true);
            toBox.setDisable(true);

            if (category != null && category.inputs != null) {
                inputField.setPromptText("Enter values: " + String.join(", ", category.inputs));
            } else {
                inputField.setPromptText("Enter values separated by commas");
            }
        } else {
            fromBox.setDisable(false);
            toBox.setDisable(false);
            inputField.setPromptText("0.00");

            if (category != null && category.units != null) {
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
        if (currentCategory == null || categoryName == null)
            return;
        String text = inputField.getText().trim();
        if (text.isEmpty()) {
            resultLabel.setText("0.00");
            return;
        }

        try {
            String type = currentCategory.type != null ? currentCategory.type : "factor";

            // 1. INTERCEPT ALGORITHMS (Age, etc.) FIRST to bypass primitive numeric parsers
            if ("algorithm".equals(type)) {
                if (categoryName.equalsIgnoreCase("AgeCalculator")) {
                    Object res = algorithmService.execute("ageCalculator", text);
                    resultLabel.setText(res + " Years");
                }
                return;
            }

            // 2. INTERCEPT FORMULAS (Finance, Health) SECOND using tokenized string inputs
            if ("formula".equals(type)) {
                String[] parts = text.split(",");
                double[] inputs = new double[parts.length];
                for (int i = 0; i < parts.length; i++) {
                    inputs[i] = Double.parseDouble(parts[i].trim());
                }

                double result = formulaService.calculate(categoryName, inputs);
                resultLabel.setText(String.format("%.4f", result));
                return;
            }

            // 3. INTERCEPT CUSTOM NON-FACTOR FIELDS (Temperature & Number Base conversions)
            if (categoryName.equalsIgnoreCase("Temperature")) {
                double val = Double.parseDouble(text);
                String from = fromBox.getValue() != null ? fromBox.getValue() : "c";
                String to = toBox.getValue() != null ? toBox.getValue() : "c";

                double c = val;
                if (from.toLowerCase().startsWith("f"))
                    c = (val - 32) * 5 / 9;
                else if (from.toLowerCase().startsWith("k"))
                    c = val - 273.15;

                double res = c;
                if (to.toLowerCase().startsWith("f"))
                    res = c * 9 / 5 + 32;
                else if (to.toLowerCase().startsWith("k"))
                    res = res + 273.15;

                resultLabel.setText(String.format("%.2f", res));
                return;
            }

            if (categoryName.equalsIgnoreCase("NumberBase") || categoryName.equalsIgnoreCase("Number Base")) {
                int radixFrom = getRadix(fromBox.getValue());
                int radixTo = getRadix(toBox.getValue());
                long decimal = Long.parseLong(text, radixFrom);
                resultLabel.setText(Long.toString(decimal, radixTo).toUpperCase());
                return;
            }

            // 4. FALLBACK: Standard Factor Multiplier/Divisor calculations
            double value = Double.parseDouble(text);
            double result = ConversionEngine.convert(value, fromBox.getValue(), toBox.getValue(), currentCategory);
            resultLabel.setText(String.format("%.4f", result));

        } catch (Exception e) {
            resultLabel.setText("..."); // Soft failure validation during real-time user keystrokes
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