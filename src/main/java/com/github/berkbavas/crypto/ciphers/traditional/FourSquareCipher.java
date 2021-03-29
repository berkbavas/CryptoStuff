package com.github.berkbavas.crypto.ciphers.traditional;

public final class FourSquareCipher {
    private final static String ALPHABET = "ABCDEFGHIKLMNOPQRSTUVWXYZ";

    private FourSquareCipher() {
    }

    public static String sanitize(String text) {
        return PlayfairCipher.sanitize(text);
    }

    public static boolean isKeyValid(String[] key) {
        if (key.length != 2)
            return false;

        return PlayfairCipher.isKeyValid(key[0]) && PlayfairCipher.isKeyValid(key[1]);
    }

    /**
     * @param plaintext must be in [A-IK-Z]+, length of {@code plaintext} must be even
     *                  and plaintext must not contain two identical consecutive letters.
     * @param key       {@code key.length} must be 2, {@code key[0]} and {@code key[1]}}
     *                  must be permutation of letters in [A-IK-Z].
     * @return encryption of {@code plaintext} under the {@code key}.
     */
    public static String encrypt(String plaintext, String[] key) {
        StringBuilder ciphertext = new StringBuilder();

        for (int i = 0; i < plaintext.length(); i += 2) {
            char a = plaintext.charAt(i);
            char b = plaintext.charAt(i + 1);

            int ai = ALPHABET.indexOf(a);
            int bi = ALPHABET.indexOf(b);

            int ar = ai / 5;
            int ac = ai % 5;
            int br = bi / 5;
            int bc = bi % 5;

            ciphertext.append(key[0].charAt(5 * ar + bc));
            ciphertext.append(key[1].charAt(5 * br + ac));
        }

        return ciphertext.toString();
    }


    /**
     * @param ciphertext must be in [A-IK-Z]+, length of {@code plaintext} must be even
     *                   and plaintext must not contain two identical consecutive letters.
     * @param key        {@code key.length} must be 2, {@code key[0]} and {@code key[1]}}
     *                   must be permutation of letters in [A-IK-Z].
     * @return decryption of {@code ciphertext} under the {@code key}.
     */
    public static String decrypt(String ciphertext, String[] key) {
        StringBuilder plaintext = new StringBuilder();

        for (int i = 0; i < ciphertext.length(); i += 2) {
            char a = ciphertext.charAt(i);
            char b = ciphertext.charAt(i + 1);

            int ai = key[0].indexOf(a);
            int bi = key[1].indexOf(b);

            int ar = ai / 5;
            int ac = ai % 5;
            int br = bi / 5;
            int bc = bi % 5;

            plaintext.append(ALPHABET.charAt(5 * ar + bc));
            plaintext.append(ALPHABET.charAt(5 * br + ac));
        }

        return plaintext.toString();
    }

    /**
     * @param keywords to generate four-square key.
     * @return four-square cipher key generated using {@code keywords}.
     */
    public static String[] generateKey(String[] keywords) {
        String[] key = new String[2];

        key[0] = PlayfairCipher.generateKey(keywords[0]);
        key[1] = PlayfairCipher.generateKey(keywords[1]);

        return key;
    }

    public static String[] generateKey() {
        String[] key = new String[2];

        key[0] = PlayfairCipher.generateKey();
        key[1] = PlayfairCipher.generateKey();

        return key;
    }

}
