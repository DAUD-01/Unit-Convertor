package com.hub.models;

import java.util.Map;

public class Category {
    private String name; // Add this field
    public String type;
    public String base;
    public Map<String, Double> units;

    public Category() {
    }

    // Add Getter and Setter
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}