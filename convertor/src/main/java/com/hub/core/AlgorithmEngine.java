package com.hub.core;

public class AlgorithmEngine {

    // NUMBER TO ROMAN
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
        java.util.Map<Character, Integer> map = java.util.Map.of( // key value pairs storing roman with their respective values
                'I', 1,
                'V', 5,
                'X', 10,
                'L', 50,
                'C', 100,
                'D', 500,
                'M', 1000
                );

        int result = 0;

        for (int i = 0; i < roman.length(); i++) { // Iterate throught all char of roman
            int current = map.get(roman.charAt(i)); // get current value

            if (i + 1 < roman.length()) { // ensures i+1 does exceed the length of roman string
                int next = map.get(roman.charAt(i + 1)); // next character of current roman
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