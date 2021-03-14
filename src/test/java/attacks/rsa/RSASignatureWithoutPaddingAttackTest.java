package attacks.rsa;

import org.junit.jupiter.api.Test;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class RSASignatureWithoutPaddingAttackTest {
    private static SecureRandom random = new SecureRandom();

    private static String hex(byte[] bytes) {
        StringBuilder result = new StringBuilder();
        for (byte b : bytes) {
            result.append(String.format("%02x", b));
        }
        return result.toString();
    }

    private static boolean isOdd(byte[] input) {
        return (input[input.length - 1] & 0x01) == 1;
    }

    @Test
    void forgeSignature() throws NoSuchAlgorithmException {
        String m = "Hello World!";
        BigInteger signature = RSASignatureWithoutPaddingAttack.forgeSignature(m);
        assertTrue(RSASignatureWithoutPaddingAttack.verify(m, signature));
    }

    @Test
    void forgeRandomByteSignatures() {
        for (int i = 0; i < 100; i++) {
            try {
                byte[] m = new byte[1024];
                random.nextBytes(m);
                byte[] hash = MessageDigest.getInstance("SHA-256").digest(m);
                if (isOdd(hash)) {
                    BigInteger signature = RSASignatureWithoutPaddingAttack.forgeSignature(m);
                    assertTrue(RSASignatureWithoutPaddingAttack.verify(m, signature));
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Test
    void forgeRandomStringSignatures() {
        for (int i = 0; i < 100; i++) {
            try {
                byte[] randomBytes = new byte[1024];
                random.nextBytes(randomBytes);
                String message = hex(randomBytes);
                byte[] hash = MessageDigest.getInstance("SHA-256").digest(message.getBytes(StandardCharsets.UTF_8));
                if (isOdd(hash)) {
                    BigInteger signature = RSASignatureWithoutPaddingAttack.forgeSignature(message);
                    assertTrue(RSASignatureWithoutPaddingAttack.verify(message, signature));
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Test
    void forgeRandomByteSignaturesShouldThrowException() {
        for (int i = 0; i < 100; i++) {
            try {
                byte[] randomBytes = new byte[1024];
                random.nextBytes(randomBytes);
                byte[] hash = MessageDigest.getInstance("SHA-256").digest(randomBytes);

                if (isOdd(hash))
                    continue;

                assertThrows(IllegalArgumentException.class, () -> {
                    RSASignatureWithoutPaddingAttack.forgeSignature(randomBytes);
                });

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Test
    void forgeRandomStringSignaturesShouldThrowException() {
        for (int i = 0; i < 100; i++) {
            try {
                byte[] randomBytes = new byte[1024];
                random.nextBytes(randomBytes);
                String message = hex(randomBytes);
                byte[] hash = MessageDigest.getInstance("SHA-256").digest(message.getBytes(StandardCharsets.UTF_8));
                if (isOdd(hash))
                    continue;

                assertThrows(IllegalArgumentException.class, () -> {
                    RSASignatureWithoutPaddingAttack.forgeSignature(message);
                });

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}