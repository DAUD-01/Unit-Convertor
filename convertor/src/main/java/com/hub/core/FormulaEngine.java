package com.hub.core;

public class FormulaEngine {

    // Health Category
    // BMI = weight / height²
    public double bmi(double weight, double height) {
        return weight / (height * height);
    }

    // Calories goal met
    public double calories(double weight, double duration, double met) {
        return weight * duration * met;
    }

    // =========================
    // 🔹 FINANCE
    // =========================

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
}