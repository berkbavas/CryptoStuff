package crypto.traditional;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class MonoalphabeticCipherTest {

    @Test
    void encryptThenDecrypt() {
        final String plaintext = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

        for (int i = 0; i < 1000; i++) {
            MonoalphabeticCipher cipher = new MonoalphabeticCipher();
            String ciphertext = cipher.encrypt(plaintext);
            assertEquals(plaintext, cipher.decrypt(ciphertext));
            String key = cipher.toString();

            {
                MonoalphabeticCipher copyCipher = new MonoalphabeticCipher(key);
                assertEquals(ciphertext, copyCipher.encrypt(plaintext));
                assertEquals(plaintext, copyCipher.decrypt(ciphertext));
                assertEquals(key, copyCipher.toString());
            }
        }

    }


    @Test
    void encrypt() {
        assertEquals("ITSSGCGKSRDGFGASHIAZTMOEEOHITKMTLM",
                new MonoalphabeticCipher("AZERTYUIOPQSDFGHJKLMWXCVBN").encrypt("HELLOWORLDMONOALPHABETICCIPHERTEST"));
        assertEquals("AZERTYUIOPQSDFGHJKLMWXCVBN", new MonoalphabeticCipher("AZERTYUIOPQSDFGHJKLMWXCVBN").toString());

        assertThrows(IllegalArgumentException.class, () -> new MonoalphabeticCipher().encrypt("Hello World!"));
    }


    @Test
    void decrypt() {
        assertEquals("HELLOWORLDMONOALPHABETICCIPHERTEST",
                new MonoalphabeticCipher("AZERTYUIOPQSDFGHJKLMWXCVBN").decrypt("ITSSGCGKSRDGFGASHIAZTMOEEOHITKMTLM"));
        assertEquals("AZERTYUIOPQSDFGHJKLMWXCVBN", new MonoalphabeticCipher("AZERTYUIOPQSDFGHJKLMWXCVBN").toString());

        assertThrows(IllegalArgumentException.class, () -> new MonoalphabeticCipher().decrypt("Hello World!"));

    }

}