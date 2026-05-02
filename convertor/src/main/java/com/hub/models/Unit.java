package com.hub.models;

public class Unit {

    public String name;
    public String symbol;
    public double factor;

    public Unit() {
    }

    public Unit(String name, String symbol, double factor) {
        this.name = name;
        this.symbol = symbol;
        this.factor = factor;
    }
}