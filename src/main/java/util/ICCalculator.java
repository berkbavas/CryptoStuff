package util;

import java.util.HashMap;

/**
 * Index of coincidence calculator
 */

public final class ICCalculator {

    private ICCalculator() {
    }

    public static double calculate(String text, int period) {
        int size = text.length() / period;
        char[][] sequences = new char[period][size];
        char[] chars = text.toCharArray();

        for (int i = 0; i < period; i++) {
            for (int j = 0; j < size; j++) {
                sequences[i][j] = chars[period * j + i];
            }
        }

        double totalScore = 0;

        for (int i = 0; i < period; i++) {
            totalScore += calculate(sequences[i]);
        }

        return totalScore / period;
    }

    private static double calculate(char[] text) {
        HashMap<Character, Integer> frequencies = new HashMap<>();

        for (int i = 0; i < text.length; i++) {
            Integer value = frequencies.getOrDefault(text[i], 0);
            frequencies.put(text[i], ++value);
        }

        double score = 0;
        for (Integer value : frequencies.values()) {
            score += value * (value - 1);
        }

        return score / (text.length * (text.length - 1));
    }


}
