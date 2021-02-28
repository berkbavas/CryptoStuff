package crypto.traditional;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MonoalphabeticCipherTest {

    @Test
    void encryptThenDecrypt() {
        final String plaintext = "ABCDEFGHIJKLMNOPQRSTUVWXYZABCDEFGHIJKLMNOPQRSTUVWXYZABCDEFGHIJKLMNOPQRSTUVWXYZ";

        for (int i = 0; i < 1000; i++) {
            String key = MonoalphabeticCipher.generateKey();
            String ciphertext = MonoalphabeticCipher.encrypt(plaintext, key);
            assertEquals(plaintext, MonoalphabeticCipher.decrypt(ciphertext, key));
        }

    }


    @Test
    void encrypt() {
        String key = "AZERTYUIOPQSDFGHJKLMWXCVBN";
        String plaintext = "HELLOWORLDMONOALPHABETICCIPHERTEST";
        assertEquals("ITSSGCGKSRDGFGASHIAZTMOEEOHITKMTLM", MonoalphabeticCipher.encrypt(plaintext, key));
    }


    @Test
    void decrypt() {
        String key = "AZERTYUIOPQSDFGHJKLMWXCVBN";
        String ciphertext = "ITSSGCGKSRDGFGASHIAZTMOEEOHITKMTLM";
        assertEquals("HELLOWORLDMONOALPHABETICCIPHERTEST", MonoalphabeticCipher.decrypt(ciphertext, key));
    }

}