package util;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TextFitnessComputer {
    private final static String PATH = TextFitnessComputer.class.getResource("../quadgram_scores.txt").getPath();
    private static TextFitnessComputer instance = new TextFitnessComputer();
    private Map<String, Double> words = new HashMap<>();
    private double defaultScore = 0;

    private TextFitnessComputer() {
        try {
            List<String> list = Files.readAllLines(new File(PATH).toPath());
            double totalScore = 0;
            for (String line : list) {
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

    public static TextFitnessComputer getInstance() {
        return instance;
    }

    public double computeScore(String text) {
        double score = 0;
        for (int i = 0; i < text.length() - 3; i++) {
            score += words.getOrDefault(text.substring(i, i + 4), defaultScore);
        }
        return score;
    }

}
