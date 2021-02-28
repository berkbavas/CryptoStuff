package crypto.traditional;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class FourSquareCipherTest {

    @Test
    void encryptThenDecrypt() {
        final String plaintext = "ABCDEFGHIKLMNOPQRSTUVWXYABCDEFGHIKLMNOPQRSTUVWXY";

        for (int i = 0; i < 1000; i++) {
            String[] key = FourSquareCipher.generateKey();
            String ciphertext = FourSquareCipher.encrypt(plaintext, key);
            assertEquals(plaintext, FourSquareCipher.decrypt(ciphertext, key));
        }
    }

    @Test
    void encrypt() {
        {
            String[] keywords = new String[]{"TESTING", "FOURSQUARE"};
            String[] key = FourSquareCipher.generateKey(keywords);
            String plaintext = "HELXOWORLD";
            assertEquals("DUKVHYHPLF", FourSquareCipher.encrypt(plaintext, key));
        }

        {
            String[] keywords = new String[]{"FGHKASDDDSAASF", "RTYDKMBLDPSDV"};
            String[] key = FourSquareCipher.generateKey(keywords);
            String plaintext = "HELXOWORLD";
            assertEquals("EYMQLXLNNR", FourSquareCipher.encrypt(plaintext, key));
        }
    }

    @Test
    void decrypt() {
        {
            String[] keywords = new String[]{"TESTING", "FOURSQUARE"};
            String[] key = FourSquareCipher.generateKey(keywords);
            String ciphertext = "DUKVHYHPLF";
            assertEquals("HELXOWORLD", FourSquareCipher.decrypt(ciphertext, key));
        }

        {
            String[] keywords = new String[]{"FGHKASDDDSAASF", "RTYDKMBLDPSDV"};
            String[] key = FourSquareCipher.generateKey(keywords);
            String ciphertext = "EYMQLXLNNR";
            assertEquals("HELXOWORLD", FourSquareCipher.decrypt(ciphertext, key));
        }
    }
}