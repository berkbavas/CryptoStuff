package crypto.traditional;

import java.security.SecureRandom;

public final class VigenereCipher {

    private VigenereCipher() {

    }

    /**
     * @param plaintext must be in [A-Z]+.
     * @param key       must be in [A-Z].
     * @return encryption of {@code plaintext} under the {@code key}.
     */
    public static String encrypt(String plaintext, String key) {
        StringBuilder ciphertext = new StringBuilder();

        for (int i = 0; i < plaintext.length(); i++) {
            int charValue = plaintext.charAt(i) - 'A';
            int keyValue = key.charAt(i % key.length()) - 'A';
            int newCharValue = Math.floorMod(charValue + keyValue, 26);
            ciphertext.append((char) (newCharValue + 'A'));
        }

        return ciphertext.toString();
    }

    /**
     * @param ciphertext must be in [A-Z]+.
     * @param key        must be in [A-Z].
     * @return decryption of {@code plaintext} under the {@code key}.
     */
    public static String decrypt(String ciphertext, String key) {
        StringBuilder plaintext = new StringBuilder();

        for (int i = 0; i < ciphertext.length(); i++) {
            int charValue = ciphertext.charAt(i) - 'A';
            int keyValue = key.charAt(i % key.length()) - 'A';
            int newValue = Math.floorMod(charValue - keyValue, 26);
            plaintext.append((char) (newValue + 'A'));
        }

        return plaintext.toString();
    }

    public static String generateKey(int length) {
        SecureRandom random = new SecureRandom();
        StringBuilder key = new StringBuilder();
        for (int i = 0; i < length; i++) {
            key.append((char) (65 + random.nextInt(26)));
        }

        return key.toString();
    }

}
