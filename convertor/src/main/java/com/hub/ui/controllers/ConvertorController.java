package com.hub.ui.controllers;

import com.hub.core.ConversionEngine;
import com.hub.models.Category;
import com.hub.services.InputParserService;
import com.hub.services.ParsedInput;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class ConvertorController {

    @FXML
    private TextField inputField;

    @FXML
    private ComboBox<String> fromBox;

    @FXML
    private ComboBox<String> toBox;

    @FXML
    private Label resultLabel;

    private Category currentCategory;

    public void setCategory(Category category) {
        currentCategory = category;

        fromBox.getItems().clear();
        toBox.getItems().clear();

        for (String unit : category.units.keySet()) {
            fromBox.getItems().add(unit);
            toBox.getItems().add(unit);
        }

        fromBox.setValue(fromBox.getItems().get(0));
        toBox.setValue(toBox.getItems().get(1));
    }

    // instead of convert method, that require user to press the button
    // in order to convert it, now it is happpening automatically

    @FXML
    public void initialize() {

        System.out.println(inputField);
        System.out.println(fromBox);
        System.out.println(toBox);

        inputField.textProperty().addListener((obs, oldVal, newVal) -> {
            autoConvert();
        });

        fromBox.setOnAction(e -> autoConvert());
        toBox.setOnAction(e -> autoConvert());
    }

    private void autoConvert() {

        if (currentCategory == null)
            return;

        try {

            if (inputField.getText().isBlank()) {
                resultLabel.setText("");
                return;
            }

            double value = Double.parseDouble(inputField.getText());

            String from = fromBox.getValue();
            String to = toBox.getValue();

            double result = ConversionEngine.convert(
                    value,
                    from,
                    to,
                    currentCategory);

            resultLabel.setText(String.valueOf(result));

        } catch (Exception e) {
            resultLabel.setText("Invalid Input");
        }
    }
}