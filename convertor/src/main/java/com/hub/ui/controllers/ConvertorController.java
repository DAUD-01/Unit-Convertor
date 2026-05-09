package com.hub.ui.controllers;

import com.hub.core.ConversionEngine;
import com.hub.models.Category;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class ConvertorController {
    @FXML
    private TextField inpuField;

    @FXML
    private ComboBox<String> fromBox;

    @FXML
    private ComboBox<String> toBox;

    @FXML
    private Label resultlabel;

    private Category currentCategory;

    public void setCategory(Category category) {

        currentCategory = category;

        for (String unit : category.units.keySet()) {
            fromBox.getItems().add(unit);
            toBox.getItems().add(unit);
        }

        fromBox.setValue(fromBox.getItems().get(0));
        toBox.setValue(toBox.getItems().get(1));
    }

    @FXML
    public void convert() {
        try {
            double value = Double.parseDouble(inpuField.getText());

            String from = fromBox.getValue();
            String to = toBox.getValue();

            double result = ConversionEngine.convert(value, from, to, currentCategory);

            resultlabel.setText("Result: " + result);

        } catch (Exception e) {
            resultlabel.setText("Invalid Inpuit");
        }
    }

}
