package com.hub.services;

public class InputParserService {
    public static ParsedInput parse(String input) {
        if (input == null || input.isEmpty()) {
            throw new IllegalArgumentException("Input is empty");
        }

        input = input.trim().toLowerCase();

        // if user enter 100 m
        if (input.contains(" ")) {
            String[] parts = input.split("\\s+");

            if (parts.length != 2) {
                throw new IllegalArgumentException("Invalid Input format");
            }

            double value = Double.parseDouble(parts[0]);
            String unit = parts[1];

            return new ParsedInput(value, unit);
        }

        // if user enter 100m, then it will run loop through 1 0 0 and stop it as soon
        // as m is encountered
        int i = 0;
        while (i < input.length() && (Character.isDigit(input.charAt(i)) || input.charAt(i) == '.')) {
            i++;
        }

        if (i == 0 || i == input.length()) {
            throw new IllegalArgumentException("Invalid Input Format");
        }
        double value = Double.parseDouble(input.substring(0, i));
        String unit = input.substring(i);

        return new ParsedInput(value, unit);
    }

    private static double parseDouble(String str) {
        try {
            return Double.parseDouble(str);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid Number: " + str);
        }
    }
}
