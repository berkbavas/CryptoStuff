package crypto.traditional;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PlayfairCipherTest {


    @Test
    void encryptThenDecrypt() {
        final String plaintext = "ABCDEFGHIKLMNOPQRSTUVWXY";

        for (int i = 0; i < 1000; i++) {
            PlayfairCipher cipher = new PlayfairCipher();
            String ciphertext = cipher.encrypt(plaintext);
            assertEquals(plaintext, cipher.decrypt(ciphertext));
            String key = cipher.toString();

            {
                PlayfairCipher copyCipher = new PlayfairCipher(key);
                assertEquals(ciphertext, copyCipher.encrypt(plaintext));
                assertEquals(plaintext, copyCipher.decrypt(ciphertext));
                assertEquals(key, copyCipher.toString());
            }
        }

        assertThrows(IllegalArgumentException.class, () -> new PlayfairCipher(""));
        assertThrows(IllegalArgumentException.class, () -> new PlayfairCipher("ABCDEFGHJKLMNOPQRSTUVWXYZ"));
        assertDoesNotThrow(() -> new PlayfairCipher("ABCDEFGHIKLMNOPQRSTUVWXYZABCDEFGHIKLMNOPQRSTUVWXYZ"));

    }

    @Test
    void encrypt() {
        assertEquals("PAKYPVPUMC", new PlayfairCipher("TESTING").encrypt("HELXOWORLD"));
        assertEquals("", new PlayfairCipher("TESTING").encrypt(""));

        assertThrows(IllegalArgumentException.class, () -> new PlayfairCipher().encrypt("ABC"));
        assertThrows(IllegalArgumentException.class, () -> new PlayfairCipher().encrypt("ABCJ"));
        assertThrows(IllegalArgumentException.class, () -> new PlayfairCipher().encrypt("Hello World!"));
        assertThrows(IllegalArgumentException.class, () -> new PlayfairCipher().encrypt("ABCC"));
        assertDoesNotThrow(() -> new PlayfairCipher().encrypt("CXXA"));
    }

    @Test
    void decrypt() {
        assertEquals("HELXOWORLD", new PlayfairCipher("TESTING").decrypt("PAKYPVPUMC"));
        assertEquals("", new PlayfairCipher().decrypt(""));

        assertThrows(IllegalArgumentException.class, () -> new PlayfairCipher().decrypt("AAA"));
        assertThrows(IllegalArgumentException.class, () -> new PlayfairCipher().decrypt("AAAJ"));
        assertThrows(IllegalArgumentException.class, () -> new PlayfairCipher().decrypt("Hello World!"));
        assertThrows(IllegalArgumentException.class, () -> new PlayfairCipher().decrypt("AA"));
        assertDoesNotThrow(() -> new PlayfairCipher().decrypt("CXXA"));
    }
}