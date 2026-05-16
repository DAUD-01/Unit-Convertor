package com.hub.core;

public class FormulaEngine {

    // Health Category

    // BMI = weight / heightsquare
    public double bmi(double weight, double height) {
        return weight / (height * height);
    }

    // Calories (simple MET formula placeholder)
    public double calories(double weight, double duration, double met) {
        return weight * duration * met;
    }

    // FINANCE

    // Simple Interest = (P × R × T) / 100
    public double simpleInterest(double p, double r, double t) {
        return (p * r * t) / 100;
    }

    // Compound Interest = P(1 + R/100)^T
    public double compoundInterest(double p, double r, double t, double n) {
        return p * Math.pow((1 + r / 100), t);
    }

    // ROI = (Final - Initial) / Initial × 100
    public double roi(double initial, double fin) {
        return ((fin - initial) / initial) * 100;
    }

    // Tax = amount × tax%
    public double tax(double amount, double taxPercent) {
        return amount * (taxPercent / 100);
    }

    // Add inside FormulaEngine.java
    public double bodyFat(double gender, double height, double waist, double neck) {
        return (waist - neck) / (height > 0 ? height : 1) * 100; // Simplified
    }

    public double waterIntake(double weight, double activity_level) {
        return (weight * 0.03) + (activity_level * 0.5); // Liters
    }
}