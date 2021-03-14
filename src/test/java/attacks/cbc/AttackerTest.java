package attacks.cbc;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

class AttackerTest {

    @Test
    void test() {
        CBCPaddingOracle oracle = new CBCPaddingOracle();
        String plaintext = "This is a test string for CBC Padding Oracle attack.";
        byte[] ciphertext = null;
        try {
            ciphertext = oracle.encrypt(plaintext.getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }
        Attacker attacker = new Attacker(oracle);
        byte[] recovered = attacker.recover(ciphertext);
        assertArrayEquals(plaintext.getBytes(), recovered);
    }

}