package attacks.traditional;

import crypto.traditional.PlayfairCipher;
import util.TextFitnessCalculator;

import java.security.SecureRandom;

public final class PlayfairCipherSolver {
    static SecureRandom secureRandom = new SecureRandom();

    private PlayfairCipherSolver() {
    }

    public static String findKey(String ciphertext) {
        String key = PlayfairCipher.generateKey();
        String text = PlayfairCipher.decrypt(ciphertext, key);
        double bestScore = TextFitnessCalculator.calculate(text);
        double initial = ciphertext.length() / 15.0;
        double step = initial / 100.0;

        for (double t = initial; t >= 0; t -= step) {
            for (int i = 0; i < 1000; i++) {
                String candidateKey = modifyKey(key);
                String decrypted = PlayfairCipher.decrypt(ciphertext, candidateKey);
                double score = TextFitnessCalculator.calculate(decrypted);
                double dF = score - bestScore;
                if (dF > 0) {
                    key = candidateKey;
                    bestScore = score;
                } else if (t > 0) {
                    if (Math.exp(dF / t) > secureRandom.nextDouble()) {
                        key = candidateKey;
                        bestScore = score;
                    }
                }

            }
        }

        return key;
    }

    public static String modifyKey(String key) {
        switch (random(50)) {
            case 0:
                return swapColumns(key);
            case 1:
                return swapRows(key);
            case 2:
                return reverse(key);
            case 3:
                return upDown(key);
            case 4:
                return leftRight(key);
            default:
                return swapLetters(key);
        }
    }

    public static String upDown(String key) {
        char[] chars = key.toCharArray();
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 5; j++) {
                char temp = chars[5 * i + j];
                chars[5 * i + j] = chars[5 * (4 - i) + j];
                chars[5 * (4 - i) + j] = temp;
            }
        return new String(chars);
    }

    public static String leftRight(String key) {
        char[] chars = key.toCharArray();
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 5; j++) {
                char temp = chars[5 * j + i];
                chars[5 * j + i] = chars[5 * j + 4 - i];
                chars[5 * j + 4 - i] = temp;
            }

        return new String(chars);
    }

    private static String swapLetters(String key) {
        char[] chars = key.toCharArray();
        int r1 = random(chars.length);
        int r2 = random(chars.length);
        char temp = chars[r1];
        chars[r1] = chars[r2];
        chars[r2] = temp;

        return new String(chars);
    }

    public static String swapRows(String key) {
        char[] chars = key.toCharArray();
        int r1 = random(5);
        int r2 = random(5);
        for (int j = 0; j < 5; j++) {
            char temp = chars[5 * r1 + j];
            chars[5 * r1 + j] = chars[5 * r2 + j];
            chars[5 * r2 + j] = temp;
        }

        return new String(chars);
    }

    public static String swapColumns(String key) {
        char[] chars = key.toCharArray();
        int c1 = random(5);
        int c2 = random(5);
        for (int i = 0; i < 5; i++) {
            char temp = chars[5 * i + c1];
            chars[5 * i + c1] = chars[5 * i + c2];
            chars[5 * i + c2] = temp;
        }

        return new String(chars);
    }

    public static String reverse(String key) {
        return new StringBuilder(key).reverse().toString();
    }

    public static int random(int bound) {
        return secureRandom.nextInt(bound);
    }
}
