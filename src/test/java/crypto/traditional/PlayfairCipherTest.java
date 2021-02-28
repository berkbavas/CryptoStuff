package crypto.traditional;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PlayfairCipherTest {


    @Test
    void encryptThenDecrypt() {
        final String plaintext = "ABCDEFGHIKLMNOPQRSTUVWXYABCDEFGHIKLMNOPQRSTUVWXY";

        for (int i = 0; i < 1000; i++) {
            String key = PlayfairCipher.generateKey();
            String ciphertext = PlayfairCipher.encrypt(plaintext, key);
            assertEquals(plaintext, PlayfairCipher.decrypt(ciphertext, key));
        }
    }

    @Test
    void encrypt() {
        String key = PlayfairCipher.generateKey("TESTING");
        String plaintext = "HELXOWORLD";
        assertEquals("PAKYPVPUMC", PlayfairCipher.encrypt(plaintext, key));
    }

    @Test
    void decrypt() {
        String key = PlayfairCipher.generateKey("TESTING");
        System.out.println(key);
        String ciphertext = "PAKYPVPUMC";
        assertEquals("HELXOWORLD", PlayfairCipher.decrypt(ciphertext, key));
    }
}