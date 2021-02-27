package crypto.traditional;

import java.security.SecureRandom;

public class VigenereCipher {
    private char[] key;

    public VigenereCipher(int keyLength) {
        if (keyLength <= 0) {
            throw new IllegalArgumentException("Length of the key must be positive.");
        }

        key = new char[keyLength];

        SecureRandom sr = new SecureRandom();

        for (int i = 0; i < key.length; i++) {
            key[i] = (char) (65 + sr.nextInt(26));
        }
    }

    public VigenereCipher(String key) {
        if (key.isEmpty())
            throw new IllegalArgumentException("Key cannot be empty.");

        if (!key.matches("[A-Z]+")) {
            throw new IllegalArgumentException("Key contains a character out of the range [A-Z].");
        }

        this.key = key.toCharArray();
    }

    public String encrypt(String plaintext) {
        if(plaintext.isEmpty())
            return "";

        if (!plaintext.matches("[A-Z]+")) {
            throw new IllegalArgumentException("Plaintext contains a character out of the range [A-Z].");
        }

        StringBuilder ciphertext = new StringBuilder();

        for (int i = 0; i < plaintext.length(); i++) {
            int value = plaintext.charAt(i) - 'A';
            int keyValue = key[i % key.length] - 'A';
            int newValue = Math.floorMod(value + keyValue, 26);
            ciphertext.append((char) (newValue + 'A'));
        }

        return ciphertext.toString();
    }

    public String decrypt(String ciphertext) {
        if(ciphertext.isEmpty())
            return "";

        if (!ciphertext.matches("[A-Z]+")) {
            throw new IllegalArgumentException("Ciphertext contains a character out of the range [A-Z].");
        }

        StringBuilder plaintext = new StringBuilder();

        for (int i = 0; i < ciphertext.length(); i++) {
            int value = ciphertext.charAt(i) - 'A';
            int keyValue = key[i % key.length] - 'A';
            int newValue = Math.floorMod(value - keyValue, 26);
            plaintext.append((char) (newValue + 'A'));
        }

        return plaintext.toString();
    }

    public String toString() {
        return new String(key);
    }
}
