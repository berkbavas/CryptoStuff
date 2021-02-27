package crypto.traditional;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class VigenereCipherTest {

    @Test
    void encryptThenDecrypt() {
        String plaintext = "ABCDEFGHIJKLMNOPQRSTUVWXYZABCDEFGHIJKLMNOPQRSTUVWXYZABCDEFGHIJKLMNOPQRSTUVWXYZ";

        for (int i = 1; i < 100; i++) {
            VigenereCipher cipher = new VigenereCipher(i);
            String ciphertext = cipher.encrypt(plaintext);
            assertEquals(plaintext, cipher.decrypt(ciphertext));
        }

        assertThrows(IllegalArgumentException.class, () -> new VigenereCipher(-3));
        assertThrows(IllegalArgumentException.class, () -> new VigenereCipher(" "));
    }

    @Test
    void encrypt() {
        assertEquals("AIIEWJUOJC", new VigenereCipher("TEXTINGXYZ").encrypt("HELLOWORLD"));
        assertEquals("", new VigenereCipher(10).encrypt(""));
        assertThrows(IllegalArgumentException.class, () -> new VigenereCipher(10).encrypt("Hello World!"));
    }

    @Test
    void decrypt() {
        assertEquals("HELLOWORLD", new VigenereCipher("TEXTINGXYZ").decrypt("AIIEWJUOJC"));
        assertEquals("", new VigenereCipher(10).decrypt(""));
        assertThrows(IllegalArgumentException.class, () -> new VigenereCipher(10).decrypt("Hello World!"));
    }
}