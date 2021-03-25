package util;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class TextFitnessCalculator {
    private static final URL path = TextFitnessCalculator.class.getClassLoader().getResource("quadgram_scores.txt");
    private static final Map<String, Double> words = new HashMap<>();
    private static double defaultScore = 0;

    static {
        if (path == null) {
            throw new RuntimeException("\"quadgram_scores.txt\" is not found.");
        }

        try {
            List<String> lines = Files.readAllLines(new File(path.getPath()).toPath());
            double totalScore = 0;

            for (String line : lines) {
                String[] arr = line.split(" ");
                words.put(arr[0], Double.valueOf(arr[1]));
                totalScore += Double.parseDouble(arr[1]);
            }

            for (Map.Entry<String, Double> entry : words.entrySet()) {
                entry.setValue(Math.log(entry.getValue() / totalScore));
            }

            defaultScore = Math.log(1 / totalScore);

        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    private TextFitnessCalculator() {
    }

    public static double calculate(String text) {
        double score = 0;
        for (int i = 0; i < text.length() - 3; i++) {
            score += words.getOrDefault(text.substring(i, i + 4), defaultScore);
        }
        return score;
    }
}
