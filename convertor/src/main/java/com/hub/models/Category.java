package com.hub.models;

import java.util.Map;

public class Category {
    public String type;
    public String base;
    public Map<String, Double> units;

    public Category() {
    }

    public Category(String type, String base, Map<String, Double> units) {
        this.type = type;
        this.base = base;
        this.units = units;
    }
}