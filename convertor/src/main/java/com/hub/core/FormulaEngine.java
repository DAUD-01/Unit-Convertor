package com.hub.core;

public class FormulaEngine {

    public double bmi(double weight, double height) {
        return weight / (height * height);
    }

    public double calories(double weight, double duration, double met) {
        return weight * duration * met;
    }

    public double simpleInterest(double p, double r, double t) {
        return (p * r * t) / 100;
    }

    public double compoundInterest(double p, double r, double t, double n) {
        return p * Math.pow((1 + r / 100), t);
    }

    public double roi(double initial, double fin) {
        return ((fin - initial) / initial) * 100;
    }

    public double tax(double amount, double taxPercent) {
        return amount * (taxPercent / 100);
    }

    public double bodyFat(double gender, double height, double waist, double neck) {
        if (gender == 1) { // Male
            return 86.010 * Math.log10(waist - neck) - 70.041 * Math.log10(height) + 36.76;
        } else { // Female
            return 163.205 * Math.log10(waist + 4.0 - neck) - 97.684 * Math.log10(height) - 78.387;
        }
    }

    public double discount(double originalPrice, double discountPercentage) {
        return originalPrice * (1 - (discountPercentage / 100));
    }

    public double waterIntake(double weight, double activity_level) {
        return (weight * 0.03) + (activity_level * 0.5); // Liters
    }
}