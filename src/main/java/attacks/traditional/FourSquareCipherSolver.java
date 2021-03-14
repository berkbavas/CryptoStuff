package attacks.traditional;

import crypto.traditional.FourSquareCipher;
import util.TextFitnessCalculator;

import java.security.SecureRandom;

public class FourSquareCipherSolver {
    static SecureRandom secureRandom = new SecureRandom();

    public static String[] findKey(String ciphertext) {
        String[] key = FourSquareCipher.generateKey();
        String text = FourSquareCipher.decrypt(ciphertext, key);
        double bestScore = TextFitnessCalculator.calculate(text);
        double initial = ciphertext.length() / 15.0;
        double step = initial / 100.0;

        for (double t = initial; t >= 0; t -= step) {
            for (int i = 0; i < 1000; i++) {
                String[] candidateKey = modifyKey(key);
                String decrypted = FourSquareCipher.decrypt(ciphertext, candidateKey);
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

    public static String[] modifyKey(String[] key) {
        String[] modifiedKey = new String[2];
        modifiedKey[0] = PlayfairCipherSolver.modifyKey(key[0]);
        modifiedKey[1] = PlayfairCipherSolver.modifyKey(key[1]);

        return modifiedKey;
    }

}