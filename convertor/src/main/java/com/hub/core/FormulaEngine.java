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

    // Missing BodyFat Engine Logic
    public double bodyFat(double gender, double height, double waist, double neck) {
        // Standard US Navy Circumference Method formula simplification proxy
        if (gender == 1) { // Male
            return 86.010 * Math.log10(waist - neck) - 70.041 * Math.log10(height) + 36.76;
        } else { // Female
            return 163.205 * Math.log10(waist + 4.0 - neck) - 97.684 * Math.log10(height) - 78.387;
        }
    }

    // Missing Discount Calculation logic
    public double discount(double originalPrice, double discountPercentage) {
        return originalPrice * (1 - (discountPercentage / 100));
    }

    public double waterIntake(double weight, double activity_level) {
        return (weight * 0.03) + (activity_level * 0.5); // Liters
    }
}