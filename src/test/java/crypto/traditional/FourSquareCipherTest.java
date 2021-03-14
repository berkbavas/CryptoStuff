package crypto.traditional;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

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

    @Test
    void sanitize() {
        String text = "In computing, plain text is a loose term for data (e.g. file contents) that represent only characters of readable material but not its graphical representation nor other objects (floating-point numbers, images, etc.).";
        String sanitized = "INCOMPUTINGPLAINTEXTISALOOSETERMFORDATAEGFILECONTENTSTHATREPRESENTONLYCHARACTERSOFREADABLEMATERIALBUTNOTITSGRAPHICALREPRESENTATIONNOROTHEROBECTSFLOATINGPOINTNUMBERSIMAGESETC";
        assertEquals(0, FourSquareCipher.sanitize(text).compareTo(sanitized));
    }

    @Test
    void keyValidation() {
        String[] key = FourSquareCipher.generateKey();
        assertTrue(FourSquareCipher.isKeyValid(key));
    }
}