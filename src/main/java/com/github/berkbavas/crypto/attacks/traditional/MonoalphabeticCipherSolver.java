package com.github.berkbavas.crypto.attacks.traditional;

import com.github.berkbavas.crypto.ciphers.traditional.MonoalphabeticCipher;
import com.github.berkbavas.crypto.util.TextFitnessCalculator;

public final class MonoalphabeticCipherSolver {

    private MonoalphabeticCipherSolver() {
    }

    public static String findKey(String ciphertext) {
        double bestScore = Double.NEGATIVE_INFINITY;
        int invariance = 0;
        String key = MonoalphabeticCipher.generateKey();
        while (invariance < 10000) {
            String candidateKey = modifyKey(key);
            String decrypted = MonoalphabeticCipher.decrypt(ciphertext, candidateKey);
            double score = TextFitnessCalculator.calculate(decrypted);
            if (score > bestScore) {
                key = candidateKey;
                bestScore = score;
                invariance = 0;
            } else {
                invariance++;
            }
        }

        return key;
    }

    private static String modifyKey(String key) {
        if (25 * Math.random() == 0)
            return MonoalphabeticCipher.generateKey();
        else
            return swapLetters(key);
    }

    private static String swapLetters(String key) {
        char[] chars = key.toCharArray();
        int r1 = (int) (chars.length * Math.random());
        int r2 = (int) (chars.length * Math.random());
        char temp = chars[r1];
        chars[r1] = chars[r2];
        chars[r2] = temp;

        return new String(chars);
    }

}
