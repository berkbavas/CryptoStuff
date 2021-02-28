package crypto.traditional;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class VigenereCipherTest {

    @Test
    void encryptThenDecrypt() {
        String plaintext = "ABCDEFGHIJKLMNOPQRSTUVWXYZABCDEFGHIJKLMNOPQRSTUVWXYZABCDEFGHIJKLMNOPQRSTUVWXYZ";

        for (int i = 1; i < 100; i++) {
            String key = VigenereCipher.generateKey(i);
            String ciphertext = VigenereCipher.encrypt(plaintext, key);
            assertEquals(plaintext, VigenereCipher.decrypt(ciphertext, key));
        }
    }

    @Test
    void encrypt() {
        String key = "TEXTINGXYZ";
        String plaintext = "HELLOWORLD";
        assertEquals("AIIEWJUOJC", VigenereCipher.encrypt(plaintext, key));
    }

    @Test
    void decrypt() {
        String key = "TEXTINGXYZ";
        String ciphertext = "AIIEWJUOJC";
        assertEquals("HELLOWORLD", VigenereCipher.decrypt(ciphertext, key));
    }
}