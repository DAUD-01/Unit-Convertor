package com.hub.core;

import java.time.LocalDate;
import java.time.Period;

public class AlgorithmEngine {

    // AGE CALCULATOR
    public int calculateAge(String birthdate) {
        LocalDate birth = LocalDate.parse(birthdate);
        return Period.between(birth, LocalDate.now()).getYears();
    }

    // ROMAN NUMERALS (simple version)
    public String toRoman(int number) {
        int[] values = { 1000, 900, 500, 400, 100, 90, 50, 40, 10, 9, 5, 4, 1 };
        String[] symbols = { "M", "CM", "D", "CD", "C", "XC", "L", "XL", "X", "IX", "V", "IV", "I" };

        StringBuilder result = new StringBuilder();

        for (int i = 0; i < values.length; i++) {
            while (number >= values[i]) {
                number -= values[i];
                result.append(symbols[i]);
            }
        }

        return result.toString();
    }

    // ROMAN TO NUMBER
    public int fromRoman(String roman) {
        java.util.Map<Character, Integer> map = java.util.Map.of(
                'I', 1, 'V', 5, 'X', 10,
                'L', 50, 'C', 100, 'D', 500, 'M', 1000);

        int result = 0;

        for (int i = 0; i < roman.length(); i++) {
            int current = map.get(roman.charAt(i));

            if (i + 1 < roman.length()) {
                int next = map.get(roman.charAt(i + 1));
                if (current < next) {
                    result -= current;
                } else {
                    result += current;
                }
            } else {
                result += current;
            }
        }

        return result;
    }
}