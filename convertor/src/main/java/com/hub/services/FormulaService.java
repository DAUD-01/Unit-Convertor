package com.hub.services;

import com.hub.core.FormulaEngine;
import com.hub.core.AlgorithmEngine;
import com.hub.core.ConversionEngine;

public class FormulaService {

    private final FormulaEngine engine = new FormulaEngine();

    public double calculate(String type, double... inputs) {

        switch (type.toLowerCase()) {

            case "bmi":
                return engine.bmi(inputs[0], inputs[1]);

            case "calories":
                return engine.calories(inputs[0], inputs[1], inputs[2]);

            case "simpleinterest":
                return engine.simpleInterest(inputs[0], inputs[1], inputs[2]);

            case "compoundinterest":
                return engine.compoundInterest(inputs[0], inputs[1], inputs[2], inputs[3]);

            case "roi":
                return engine.roi(inputs[0], inputs[1]);

            case "tax":
                return engine.tax(inputs[0], inputs[1]);
            case "temperature":
                return engine.convertTemperature(inputs[0], fromUnit, toUnit);
            case "numberbase":
                return engine.convertBase((String) inputs[0], (String) inputs[1], (String) inputs[2]);

            default:
                throw new IllegalArgumentException("Unknown formula type: " + type);
        }
    }
}