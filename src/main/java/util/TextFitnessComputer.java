package util;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TextFitnessComputer {
    private static TextFitnessComputer instance = new TextFitnessComputer();
    private Map<String, Double> words = new HashMap<>();
    private int gramLength = 4;
    private double defaultScore = 0;
    private String path = TextFitnessComputer.class.getResource("../quadgram_scores.txt").getPath();

    private TextFitnessComputer() {
        try {
            List<String> list = Files.readAllLines(new File(path).toPath());
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
        text = text.toUpperCase().trim();
        double score = 0;
        for (int i = 0; i < text.length() - (gramLength - 1); i++) {
            score += words.getOrDefault(text.substring(i, i + gramLength), defaultScore);
        }

        return score;
    }

}
