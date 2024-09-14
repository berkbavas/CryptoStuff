package com.github.berkbavas.crypto.attacks.traditional;

import com.github.berkbavas.crypto.ciphers.traditional.PlayfairCipher;
import com.github.berkbavas.crypto.util.Stopwatch;
import com.github.berkbavas.crypto.util.TextFitnessCalculator;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

class PlayfairCipherSolverTest {

    @Test
    void testFindKey() {
        System.out.println("PlayfairCipherSolverTest.java: Test is started.");
        String plaintext = "BEFORECOMPUTERSPLAINTEXTMEANTTEXTINTHELANGUAGEOFTHECOMMUNICATINGPARTIESSINCECOMPUTERSTHEDEFINITIONHASBEENEXPANDEDITNOWINCLUDESNOTONLYTHEELECTRONICREPRESENTATIONOFTEXTSUCHASEMAILSANDWORDPROCESSORDOCUMENTSBUTALSOTHECOMPUTERREPRESENTATIONOFSPEECHMUSICPICTURESVIDEOSATMANDCREDITCARDTRANSACTIONSSENSORDATAANDSOFORTHTHATISBASICALLYANYINFORMATIONWHICHTHECOMMUNICATINGPARTIESMIGHTWISHTOHIDEFROMOTHERSTHEPLAINTEXTISTHENORMALREPRESENTATIONOFTHEDATABEFOREANYACTIONHASBEENTAKENTOHIDEITTHE";
        String key = PlayfairCipher.generateKey();
        String ciphertext = PlayfairCipher.encrypt(plaintext, key);
        long timeout = 60 * 1000; // 1 min
        boolean success = false;
        Stopwatch sw = new Stopwatch();

        while (true) {
            String foundKey = PlayfairCipherSolver.findKey(ciphertext);
            String text = PlayfairCipher.decrypt(ciphertext, foundKey);
            double score = TextFitnessCalculator.calculate(text);

            System.out.printf("PlayfairCipherSolverTest.java: Score %.2f Decrypted Text: %s%n", score, text);

            if (plaintext.compareTo(text) == 0) {
                System.out.printf("PlayfairCipherSolverTest.java: Attack is successful. Execution took %d ms.%n", sw.elapsed());
                success = true;
                break;
            }

            if (sw.elapsed() > timeout) {
                System.out.println("PlayfairCipherSolverTest.java: Time is up. Attack has failed.");
                break;
            }

        }

        assertTrue(success);
    }
}