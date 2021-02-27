package crypto.traditional;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class FourSquareCipherTest {

    @Test
    void encryptThenDecrypt() {
        final String plaintext = "ABCDEFGHIKLMNOPQRSTUVWXYABCDEFGHIKLMNOPQRSTUVWXY";

        for (int i = 0; i < 1000; i++) {
            FourSquareCipher cipher = new FourSquareCipher();
            String ciphertext = cipher.encrypt(plaintext);
            assertEquals(plaintext, cipher.decrypt(ciphertext));
            String[] keys = cipher.toString().split(":");

            {
                FourSquareCipher copyCipher = new FourSquareCipher(keys);
                assertEquals(ciphertext, copyCipher.encrypt(plaintext));
                assertEquals(plaintext, copyCipher.decrypt(ciphertext));
            }
        }

        assertThrows(IllegalArgumentException.class, () -> new FourSquareCipher(new String[]{}));
        assertThrows(IllegalArgumentException.class, () -> new FourSquareCipher(new String[]{"ASD", "AVC", "XYZ"}));
        assertThrows(IllegalArgumentException.class, () -> new FourSquareCipher(new String[]{"", ""}));
        assertThrows(IllegalArgumentException.class, () -> new FourSquareCipher(new String[]{"A", ""}));
        assertThrows(IllegalArgumentException.class, () -> new FourSquareCipher(new String[]{"", "A"}));
        assertThrows(IllegalArgumentException.class,
                () -> new FourSquareCipher(new String[]{"ABCDEFGHJKLMNOPQRSTUVWXYZ", "ABCDEFGHIKLMNOPQRSTUVWXYZ"}));
        assertThrows(IllegalArgumentException.class,
                () -> new FourSquareCipher(new String[]{"ABCDEFGHIKLMNOPQRSTUVWXYZ", "ABCDEFGHJKLMNOPQRSTUVWXYZ"}));

    }

    @Test
    void encrypt() {
        assertEquals("DUKVHYHPLF",
                new FourSquareCipher(new String[]{"TESTING", "FOURSQUARE"}).encrypt("HELXOWORLD"));
        assertEquals("EYMQLXLNNR",
                new FourSquareCipher(new String[]{"FGHKASDDDSAASF", "RTYDKMBLDPSDV"}).encrypt("HELXOWORLD"));
        assertEquals("", new FourSquareCipher().encrypt(""));

        assertThrows(IllegalArgumentException.class, () -> new FourSquareCipher().encrypt("ABC"));
        assertThrows(IllegalArgumentException.class, () -> new FourSquareCipher().encrypt("ABCJ"));
        assertThrows(IllegalArgumentException.class, () -> new FourSquareCipher().encrypt("Hello World!"));
    }

    @Test
    void decrypt() {

        assertEquals("HELXOWORLD",
                new FourSquareCipher(new String[]{"TESTING", "FOURSQUARE"}).decrypt("DUKVHYHPLF"));
        assertEquals("HELXOWORLD",
                new FourSquareCipher(new String[]{"FGHKASDDDSAASF", "RTYDKMBLDPSDV"}).decrypt("EYMQLXLNNR"));
        assertEquals("", new FourSquareCipher().decrypt(""));

        assertThrows(IllegalArgumentException.class, () -> new FourSquareCipher().decrypt("AAA"));
        assertThrows(IllegalArgumentException.class, () -> new FourSquareCipher().decrypt("AAAJ"));
        assertThrows(IllegalArgumentException.class, () -> new FourSquareCipher().decrypt("Hello World!"));
    }
}