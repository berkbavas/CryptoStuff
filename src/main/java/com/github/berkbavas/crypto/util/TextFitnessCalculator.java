package com.github.berkbavas.crypto.util;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class TextFitnessCalculator {
    private static final URL PATH = TextFitnessCalculator.class.getResource("/quadgram_scores.txt");
    private static final Map<String, Double> WORDS = new HashMap<>();
    private static final double DEFAULT_SCORE;

    static {
        if (PATH == null) {
            throw new RuntimeException("'quadgram_scores.txt' is not found.");
        }

        try {
            List<String> lines = Files.readAllLines(new File(PATH.getPath()).toPath());
            double totalScore = 0;

            for (String line : lines) {
                String[] arr = line.split(" ");
                WORDS.put(arr[0], Double.valueOf(arr[1]));
                totalScore += Double.parseDouble(arr[1]);
            }

            for (Map.Entry<String, Double> entry : WORDS.entrySet()) {
                entry.setValue(Math.log(entry.getValue() / totalScore));
            }

            DEFAULT_SCORE = Math.log(1 / totalScore);

        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    private TextFitnessCalculator() {
    }

    public static double calculate(String text) {
        double score = 0;
        for (int i = 0; i < text.length() - 3; i++) {
            score += WORDS.getOrDefault(text.substring(i, i + 4), DEFAULT_SCORE);
        }
        return score;
    }

    public static double getDefaultScore() {
        return DEFAULT_SCORE;
    }
}
