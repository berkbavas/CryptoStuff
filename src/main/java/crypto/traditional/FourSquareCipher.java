package crypto.traditional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class FourSquareCipher {
    private final static String ALPHABET = "ABCDEFGHIKLMNOPQRSTUVWXYZ";
    private String[] keys = new String[2];

    public FourSquareCipher() {
        for (int i = 0; i < keys.length; i++) {
            ArrayList<Character> key = new ArrayList<>(Arrays.asList('A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'K',
                    'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'));

            Collections.shuffle(key);
            StringBuilder sb = new StringBuilder();
            for (char ch : key) {
                sb.append(ch);
            }
            keys[i] = sb.toString();
        }
    }

    public FourSquareCipher(String[] keywords) {
        if (keywords.length != 2)
            throw new IllegalArgumentException(String.format("There must be two keywords but provided %d.", keywords.length));

        for (String keyword : keywords) {
            if (keyword.isEmpty())
                throw new IllegalArgumentException("Keyword cannot be empty.");

            if (!keyword.matches("[A-IK-Z]+")) {
                throw new IllegalArgumentException("Keyword contains a character out of the range [A-IK-Z]. Keyword is " + keyword);
            }
        }

        for (int i = 0; i < keys.length; i++) {
            ArrayList<Character> key = new ArrayList<>();

            for (char ch : keywords[i].toCharArray()) {
                if (key.contains(ch))
                    continue;

                key.add(ch);
            }

            for (char ch = 'A'; ch <= 'Z'; ch++) {
                if (ch == 'J')
                    continue;

                if (key.contains(ch))
                    continue;

                key.add(ch);
            }

            StringBuilder sb = new StringBuilder();
            for (char ch : key) {
                sb.append(ch);
            }
            keys[i] = sb.toString();
        }

    }

    public String encrypt(String plaintext) {
        if (plaintext.length() == 0)
            return "";

        if (plaintext.length() % 2 != 0)
            throw new IllegalArgumentException("Length of the plaintext must be even.");

        if (!plaintext.matches("[A-IK-Z]+"))
            throw new IllegalArgumentException("Plaintext contains a character out of the range [A-IK-Z].");

        StringBuilder ciphertext = new StringBuilder();
        int aIndex, bIndex, aRow, aColumn, bRow, bColumn;
        for (int i = 0; i < plaintext.length(); i += 2) {
            aIndex = ALPHABET.indexOf(plaintext.charAt(i));
            bIndex = ALPHABET.indexOf(plaintext.charAt(i + 1));
            aRow = aIndex / 5;
            bRow = bIndex / 5;
            aColumn = aIndex % 5;
            bColumn = bIndex % 5;
            ciphertext.append(keys[0].charAt(5 * aRow + bColumn));
            ciphertext.append(keys[1].charAt(5 * bRow + aColumn));
        }

        return ciphertext.toString();
    }

    public String decrypt(String ciphertext) {
        if (ciphertext.length() == 0)
            return "";

        if (ciphertext.length() % 2 != 0)
            throw new IllegalArgumentException("Length of the ciphertext must be even.");

        if (!ciphertext.matches("[A-IK-Z]+"))
            throw new IllegalArgumentException("Ciphertext contains a character out of the range [A-IK-Z].");

        StringBuilder plaintext = new StringBuilder();

        int aIndex, bIndex, aRow, aColumn, bRow, bColumn;
        for (int i = 0; i < ciphertext.length(); i += 2) {
            aIndex = keys[0].indexOf(ciphertext.charAt(i));
            bIndex = keys[1].indexOf(ciphertext.charAt(i + 1));
            aRow = aIndex / 5;
            aColumn = aIndex % 5;
            bRow = bIndex / 5;
            bColumn = bIndex % 5;
            plaintext.append(ALPHABET.charAt(5 * aRow + bColumn));
            plaintext.append(ALPHABET.charAt(5 * bRow + aColumn));
        }

        return plaintext.toString();
    }

    public String toString() {
        return keys[0] + ":" + keys[1];
    }

}
