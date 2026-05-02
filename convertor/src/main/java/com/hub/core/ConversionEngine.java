package com.hub.core;

import com.hub.models.Category;

public class ConversionEngine {
    public static double convert(double value, String fromUnit, String toUnit, Category category) {
        if (category == null || category.units == null) {
            throw new IllegalArgumentException("Invalid Category data");
        }

        Double fromFactor = category.units.get(fromUnit);
        Double toFactor = category.units.get(toUnit);

        if (fromFactor == null || toFactor == null) {
            throw new IllegalArgumentException("Invalid Unit");
        }

        return value * (toFactor / fromFactor);

    }
}
