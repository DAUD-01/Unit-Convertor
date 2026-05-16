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

    public void setCategory(String name, Category category) {
        this.categoryName = name;
        this.currentCategory = category;

        fromBox.getItems().clear();
        toBox.getItems().clear();

        if (category == null)
            return;

        String type = category.type != null ? category.type : "factor";

        // Keep special handling ONLY for Temperature and NumberBase
        if (name.equalsIgnoreCase("Temperature") || name.equalsIgnoreCase("NumberBase")
                || name.equalsIgnoreCase("Number Base")) {
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
            return;
        }

        if ("formula".equals(type) || "algorithm".equals(type)) {
            fromBox.setDisable(true);
            toBox.setDisable(true);

            if (category.inputs != null) {
                inputField.setPromptText("Enter values: " + String.join(", ", category.inputs));
            } else {
                inputField.setPromptText("Enter roman or number: ");
            }
        } else {
            // This blocks runs for standard conversions AND our new Time unit!
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
        if (currentCategory == null || categoryName == null)
            return;
        String text = inputField.getText().trim();
        if (text.isEmpty()) {
            resultLabel.setText("0.00");
            return;
        }

        try {
            String type = currentCategory.type != null ? currentCategory.type : "factor";

            if ("algorithm".equals(type) || categoryName.equalsIgnoreCase("RomanNumerals")) {
                if (categoryName.equalsIgnoreCase("RomanNumerals")) {
                    try {
                        // Try parsing as an integer to convert Number -> Roman
                        int num = Integer.parseInt(text);
                        Object res = algorithmService.execute("toroman", num);
                        resultLabel.setText(res.toString());
                    } catch (NumberFormatException nfe) {
                        // If it's not a number, assume the user is typing Roman characters -> Convert
                        // Roman -> Number
                        Object res = algorithmService.execute("fromroman", text.toUpperCase());
                        resultLabel.setText(res.toString());
                    }
                }
                return;
            }

            // 2. SYSTEM FORMULAS
            if ("formula".equals(type)) {
                String[] parts = text.split(",");
                double[] inputs = new double[parts.length];
                for (int i = 0; i < parts.length; i++) {
                    inputs[i] = Double.parseDouble(parts[i].trim());
                }

                double result = formulaService.calculate(categoryName, inputs);
                resultLabel.setText(String.format("%.2f", result));
                return;
            }

            // 3. SPECIAL INTERCEPT SYSTEM (Temperature Module)
            if (categoryName.equalsIgnoreCase("Temperature")) {
                double val = Double.parseDouble(text);
                String from = fromBox.getValue();
                String to = toBox.getValue();

                if (from == null || to == null)
                    return;

                from = from.toLowerCase();
                to = to.toLowerCase();

                double c = val;
                if (from.startsWith("f"))
                    c = (val - 32) * 5 / 9;
                else if (from.startsWith("k"))
                    c = val - 273.15;

                double res = c;
                if (to.startsWith("f"))
                    res = c * 9 / 5 + 32;
                else if (to.startsWith("k"))
                    res = c + 273.15;

                resultLabel.setText(String.format("%.2f", res));
                return;
            }

            // 4. NUMBER BASE INTERCEPT
            if (categoryName.equalsIgnoreCase("NumberBase") || categoryName.equalsIgnoreCase("Number Base")) {
                int radixFrom = getRadix(fromBox.getValue());
                int radixTo = getRadix(toBox.getValue());
                long decimal = Long.parseLong(text, radixFrom);
                resultLabel.setText(Long.toString(decimal, radixTo).toUpperCase());
                return;
            }

            // 5. STANDARD CONVERSION FALLBACK (Time runs instantly here!)
            double value = Double.parseDouble(text);
            double result = ConversionEngine.convert(value, fromBox.getValue(), toBox.getValue(), currentCategory);
            resultLabel.setText(String.format("%.4f", result));

        } catch (Exception e) {
            resultLabel.setText("...");
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

            if (inputField != null && inputField.getScene() != null) {
                Scene currentScene = inputField.getScene();
                currentScene.setFill(javafx.scene.paint.Color.web("#101d2d"));
                currentScene.setRoot(view);

                // FIX: Force full-screen window persistence properties during the transition
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
}