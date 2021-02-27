package crypto.traditional;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CeasarCipherTest {


    @Test
    void encryptThenDecrypt() {
        String plaintext = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

        for (int i = 0; i < 26; i++) {
            CeasarCipher cipher = new CeasarCipher(i);
            String ciphertext = cipher.encrypt(plaintext);
            assertEquals(plaintext, cipher.decrypt(ciphertext));
        }

        assertThrows(IllegalArgumentException.class, () -> new CeasarCipher(122));
        assertThrows(IllegalArgumentException.class, () -> new CeasarCipher(' '));

    }


    @Test
    void encrypt() {
        assertEquals("DAHHKSKNHZ", new CeasarCipher(22).encrypt("HELLOWORLD"));
        assertEquals("", new CeasarCipher().encrypt(""));
        assertThrows(IllegalArgumentException.class, () -> new CeasarCipher().encrypt("Hello World!"));
    }

    @Test
    void decrypt() {
        assertEquals("HELLOWORLD", new CeasarCipher(22).decrypt("DAHHKSKNHZ"));
        assertEquals("", new CeasarCipher().decrypt(""));
        assertThrows(IllegalArgumentException.class, () -> new CeasarCipher().decrypt("Hello World!"));
    }


}