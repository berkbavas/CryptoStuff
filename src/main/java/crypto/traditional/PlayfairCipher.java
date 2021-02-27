package crypto.traditional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class PlayfairCipher {
    private char[][] key = new char[5][5];

    public PlayfairCipher() {
        ArrayList<Character> key = new ArrayList<>(Arrays.asList('A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'K',
                'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'));

        Collections.shuffle(key);
        for (int i = 0; i < key.size(); i++) {
            int row = i / 5;
            int column = i % 5;
            this.key[row][column] = key.get(i);
        }
    }

    public PlayfairCipher(String keyword) {
        if (keyword.isEmpty())
            throw new IllegalArgumentException("Keyword cannot be empty.");

        if (!keyword.matches("[A-IK-Z]+")) {
            throw new IllegalArgumentException("Keyword contains a character out of the range [A-IK-Z]. Keyword is " + keyword);
        }

        ArrayList<Character> key = new ArrayList<>();

        for (char c : keyword.toCharArray()) {
            if (key.contains(c))
                continue;

            key.add(c);
        }

        for (char ch = 'A'; ch <= 'Z'; ch++) {
            if (ch == 'J')
                continue;

            if (key.contains(ch))
                continue;

            key.add(ch);
        }

        for (int i = 0; i < key.size(); i++) {
            int row = i / 5;
            int column = i % 5;
            this.key[row][column] = key.get(i);
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

        for (int i = 0; i < plaintext.length(); i = i + 2) {
            char a = plaintext.charAt(i);
            char b = plaintext.charAt(i + 1);

            if (a == b) {
                throw new IllegalArgumentException(String.format("Two consecutive letters at %d and %d are the same.", i, i + 1));
            }

            ciphertext.append(encrypt(a, b));
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

        for (int i = 0; i < ciphertext.length(); i = i + 2) {
            char a = ciphertext.charAt(i);
            char b = ciphertext.charAt(i + 1);

            if (a == b) {
                throw new IllegalArgumentException(String.format("Two consecutive letters at %d and %d are the same.", i, i + 1));
            }

            plaintext.append(decrypt(a, b));
        }

        return plaintext.toString();
    }

    private char[] encrypt(char a, char b) {
        int aRow = 0;
        int aColumn = 0;
        int bRow = 0;
        int bColumn = 0;

        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                if (key[i][j] == a) {
                    aRow = i;
                    aColumn = j;
                }

                if (key[i][j] == b) {
                    bRow = i;
                    bColumn = j;
                }

            }
        }
        if (aRow == bRow) {
            return new char[]{key[aRow][(aColumn + 1) % 5], key[bRow][(bColumn + 1) % 5]};
        } else if (aColumn == bColumn) {
            return new char[]{key[(aRow + 1) % 5][aColumn], key[(bRow + 1) % 5][bColumn]};
        } else {
            return new char[]{key[aRow][bColumn], key[bRow][aColumn]};
        }
    }

    private char[] decrypt(char a, char b) {
        int aRow = 0;
        int aColumn = 0;
        int bRow = 0;
        int bColumn = 0;

        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                if (key[i][j] == a) {
                    aRow = i;
                    aColumn = j;
                }

                if (key[i][j] == b) {
                    bRow = i;
                    bColumn = j;
                }

            }
        }

        if (aRow == bRow) {
            return new char[]{key[aRow][Math.floorMod(aColumn - 1, 5)], key[bRow][Math.floorMod(bColumn - 1, 5)]};
        } else if (aColumn == bColumn) {
            return new char[]{key[Math.floorMod(aRow - 1, 5)][aColumn], key[Math.floorMod(bRow - 1, 5)][bColumn]};
        } else {
            return new char[]{key[aRow][bColumn], key[bRow][aColumn]};
        }
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                sb.append(key[i][j]);
            }
        }

        return sb.toString();
    }

}
