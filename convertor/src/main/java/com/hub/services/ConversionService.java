package com.hub.services;

import com.hub.core.ConversionEngine;
import com.hub.models.Category;

public class ConversionService {

    public double convert(double value, String from, String to, Category category) {
        return ConversionEngine.convert(value, to, from, category);
    }
}
