package com.hub.models;

public class ConversionResult {
    public String fromUnit;
    public String toUnit;
    public double inputValue;
    public double resultValue;

    public ConversionResult() {
    }

    public ConversionResult(String fromUnit, String toUnit, double inputValue, double resultValue) {
        this.fromUnit = fromUnit;
        this.toUnit = toUnit;
        this.inputValue = inputValue;
        this.resultValue = resultValue;
    }
}
