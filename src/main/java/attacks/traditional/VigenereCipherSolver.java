package attacks.traditional;

import crypto.traditional.VigenereCipher;
import util.ICCalculator;
import util.TextFitnessCalculator;

import java.util.Arrays;

public final class VigenereCipherSolver {

    private VigenereCipherSolver() {
    }

    public static String findKey(String ciphertext, int upperBound) {
        double bestScore = Double.NEGATIVE_INFINITY;
        String key = null;
        for (int i = 1; i <= upperBound; i++) {
            String candidateKey = findCandidateKey(ciphertext, i);
            String decrypted = VigenereCipher.decrypt(ciphertext, candidateKey);
            double score = TextFitnessCalculator.calculate(decrypted);
            if (score > bestScore) {
                bestScore = score;
                key = candidateKey;
            }
        }

        return key;
    }

    private static String findCandidateKey(String ciphertext, int keyLength) {
        byte[] candidateKey = new byte[keyLength];
        Arrays.fill(candidateKey, (byte) 'A');

        for (int i = 0; i < 2 * keyLength; i++) {
            double bestScore = Double.NEGATIVE_INFINITY;
            int index = i % keyLength;
            byte subject = candidateKey[index];
            for (byte j = 65; j <= 90; j++) {
                candidateKey[index] = j;
                String decrypted = VigenereCipher.decrypt(ciphertext, new String(candidateKey));
                double score = TextFitnessCalculator.calculate(decrypted);
                if (score > bestScore) {
                    bestScore = score;
                    subject = j;
                }

            }
            candidateKey[index] = subject;
        }

        return new String(candidateKey);
    }

    public static double[] calculateICs(String text, int upperBound) {
        double[] ics = new double[upperBound];

        for (int i = 1; i < upperBound; i++) {
            ics[i] = ICCalculator.calculate(text, i);
        }

        return ics;
    }
}
