package crypto.traditional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;

public class MonoalphabeticCipher {
    private final HashMap<Character, Character> key = new HashMap<>();
    private final HashMap<Character, Character> inverseKey = new HashMap<>();

    public MonoalphabeticCipher() {
        ArrayList<Character> key = new ArrayList<>(Arrays.asList('A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K',
                'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'));

        Collections.shuffle(key);

        for (char ch = 'A'; ch <= 'Z'; ch++) {
            char keyChar = key.get(ch - 'A');
            this.key.put(ch, keyChar);
            this.inverseKey.put(keyChar, ch);
        }
    }

    public MonoalphabeticCipher(String key) {
        if (key.length() != 26)
            throw new IllegalArgumentException("Key must be length of 26.");

        for (char ch = 'A'; ch <= 'Z'; ch++) {
            char keyChar = key.charAt(ch - 'A');
            if (this.inverseKey.get(keyChar) == null) {
                this.key.put(ch, keyChar);
                this.inverseKey.put(keyChar, ch);
            } else {
                throw new IllegalArgumentException(String.format("Key is not a permutation. The character '%c' " +
                        "occurs at least two times in the key.", keyChar));
            }
        }
    }

    public String encrypt(String plaintext) {
        if (!plaintext.matches("[A-Z]+")) {
            throw new IllegalArgumentException("Plaintext contains a character out of the range [A-Z].");
        }

        StringBuilder ciphertext = new StringBuilder();

        for (char ch : plaintext.toCharArray()) {
            ciphertext.append(encrypt(ch));
        }

        return ciphertext.toString();
    }

    public String decrypt(String ciphertext) {
        if (!ciphertext.matches("[A-Z]+")) {
            throw new IllegalArgumentException("Ciphertext contains a character out of range [A-Z].");
        }

        StringBuilder plaintext = new StringBuilder();

        for (char ch : ciphertext.toCharArray()) {
            plaintext.append(decrypt(ch));
        }

        return plaintext.toString();
    }

    private char encrypt(char ch) {
        return key.get(ch);
    }

    private char decrypt(char ch) {
        return inverseKey.get(ch);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();

        for (char ch = 'A'; ch <= 'Z'; ch++) {
            sb.append(key.get(ch));
        }

        return sb.toString();
    }

}
