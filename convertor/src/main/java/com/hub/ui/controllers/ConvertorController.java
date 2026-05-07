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

    private category currentCategory;

    public void setCategory(Category category) {
        fromBox.getItems().add(unit);
        toBox.getItems().add(unit);
    }
}
