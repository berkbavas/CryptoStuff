package crypto.traditional;

public final class VigenereCipher {

    private VigenereCipher() {
    }

    /**
     * @param plaintext must be in [A-Z]+.
     * @param key       must be in [A-Z]+.
     * @return encryption of {@code plaintext} under the {@code key}.
     */
    public static String encrypt(String plaintext, String key) {
        StringBuilder ciphertext = new StringBuilder();

        if (!check(key))
            throw new IllegalArgumentException("Key must be in [A-Z]+.");

        for (int i = 0; i < plaintext.length(); i++) {
            int charValue = plaintext.charAt(i) - 'A';

            if (!check(charValue))
                throw new IllegalArgumentException("Plaintext must be in [A-Z]+.");

            int keyValue = key.charAt(i % key.length()) - 'A';
            int newCharValue = Math.floorMod(charValue + keyValue, 26);
            ciphertext.append((char) (newCharValue + 'A'));
        }

        return ciphertext.toString();
    }

    /**
     * @param ciphertext must be in [A-Z]+.
     * @param key        must be in [A-Z]+.
     * @return decryption of {@code plaintext} under the {@code key}.
     */
    public static String decrypt(String ciphertext, String key) {
        StringBuilder plaintext = new StringBuilder();

        if (!check(key))
            throw new IllegalArgumentException("Key must be in [A-Z]+.");

        for (int i = 0; i < ciphertext.length(); i++) {
            int charValue = ciphertext.charAt(i) - 'A';

            if (!check(charValue))
                throw new IllegalArgumentException("Ciphertext must be in [A-Z]+.");

            int keyValue = key.charAt(i % key.length()) - 'A';
            int newValue = Math.floorMod(charValue - keyValue, 26);
            plaintext.append((char) (newValue + 'A'));
        }

        return plaintext.toString();
    }

    public static String generateKey(int length) {
        StringBuilder key = new StringBuilder();
        for (int i = 0; i < length; i++) {
            key.append((char) (65 + 26 * Math.random()));
        }

        return key.toString();
    }

    private static boolean check(int value) {
        return 0 <= value && value <= 25;
    }

    private static boolean check(String text) {
        for (int i = 0; i < text.length(); i++) {
            int value = text.charAt(i) - 'A';
            if (!check(value))
                return false;
        }

        return true;
    }

}
