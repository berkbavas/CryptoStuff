package com.github.berkbavas.crypto.ciphers.traditional;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
        String ciphertext = "PAKYPVPUMC";
        assertEquals("HELXOWORLD", PlayfairCipher.decrypt(ciphertext, key));
    }

    @Test
    void sanitize() {
        String text = "In computing, plain text is a loose term for data (e.g. file contents) that represent only characters of readable material but not its graphical representation nor other objects (floating-point numbers, images, etc.).";
        String sanitized = "INCOMPUTINGPLAINTEXTISALOOSETERMFORDATAEGFILECONTENTSTHATREPRESENTONLYCHARACTERSOFREADABLEMATERIALBUTNOTITSGRAPHICALREPRESENTATIONNOROTHEROBECTSFLOATINGPOINTNUMBERSIMAGESETC";
        assertEquals(0, PlayfairCipher.sanitize(text).compareTo(sanitized));
    }

    @Test
    void keyValidation() {
        String key = PlayfairCipher.generateKey();
        assertTrue(PlayfairCipher.isKeyValid(key));
    }
}