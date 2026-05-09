package com.hub.services;

import com.hub.core.ConversionEngine;
import com.hub.models.Category;

public class ConversionService {
    private final ConversionEngine engine;

    public ConversionService() {
        this.engine = new ConversionEngine();
    }

    public double convert(double value, String from, String to, Category category) {
        return engine.convert(value, to, from, category);
    }
}
