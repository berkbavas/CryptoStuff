package attacks.traditional;

import crypto.traditional.PlayfairCipher;
import org.junit.jupiter.api.Test;
import util.Stopwatch;
import util.TextFitnessCalculator;

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

            System.out.println(String.format("PlayfairCipherSolverTest.java: Score %.2f Decrypted Text: %s", score, text));

            if (plaintext.compareTo(text) == 0) {
                System.out.println(String.format("PlayfairCipherSolverTest.java: Attack is successful. Execution took %d ms.", sw.elapsed()));
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