package util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class TextFitnessCalculator {
    private static Logic logic = new Logic();

    private TextFitnessCalculator() {
    }

    public static double calculate(String text) {
        return logic.calculate(text);
    }

    private static class Logic {
        private URL path = Logic.class.getResource("../quadgram_scores.txt");
        private Map<String, Double> words = new HashMap<>();
        private double defaultScore = 0;

        private Logic() {
            try {

                if (path == null) {
                    throw new FileNotFoundException("../quadgram_scores.txt is not found.");
                }

                List<String> lines = Files.readAllLines(new File(path.getPath()).toPath());
                double totalScore = 0;
                for (String line : lines) {
                    String[] arr = line.split(" ");
                    words.put(arr[0], Double.valueOf(arr[1]));
                    totalScore += Double.valueOf(arr[1]);
                }

                for (Map.Entry<String, Double> entry : words.entrySet()) {
                    entry.setValue(Math.log(entry.getValue() / totalScore));
                }

                defaultScore = Math.log(1 / totalScore);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        private double calculate(String text) {
            double score = 0;
            for (int i = 0; i < text.length() - 3; i++) {
                score += words.getOrDefault(text.substring(i, i + 4), defaultScore);
            }
            return score;
        }
    }
}
