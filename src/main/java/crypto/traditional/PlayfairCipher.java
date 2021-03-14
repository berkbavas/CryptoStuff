package crypto.traditional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public final class PlayfairCipher {

    private PlayfairCipher() {
    }

    public static String sanitize(String text) {
        text = text.toUpperCase();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);
            if (('A' <= c && c <= 'I') || ('K' <= c && c <= 'Z')) {
                sb.append(c);
            }

        }

        return sb.toString();
    }

    public static boolean isKeyValid(String key) {
        if (key.length() != 25)
            return false;

        boolean[] freq = new boolean[25];

        for (int i = 0; i < 25; i++) {
            char c = key.charAt(i);

            if (c == 'J')
                return false;

            if (c < 'A' || 'Z' < c)
                return false;

            freq[c <= 'I' ? c - 'A' : c - 'A' - 1] = true;

        }

        for (boolean b : freq) {
            if (!b)
                return false;
        }

        return true;
    }


    /**
     * @param plaintext must be in [A-IK-Z]+, length of {@code plaintext} must be even
     *                  and plaintext must not contain two identical consecutive letters.
     * @param key       must be a permutation of letters in [A-IK-Z].
     * @return encryption of {@code plaintext} under the {@code key}.
     */
    public static String encrypt(String plaintext, String key) {
        StringBuilder ciphertext = new StringBuilder();

        for (int i = 0; i < plaintext.length(); i = i + 2) {
            char a = plaintext.charAt(i);
            char b = plaintext.charAt(i + 1);

            int av = key.indexOf(a);
            int bv = key.indexOf(b);

            int ar = av / 5;
            int ac = av % 5;
            int br = bv / 5;
            int bc = bv % 5;

            if (ar == br) {
                ciphertext.append(key.charAt(5 * ar + Math.floorMod(ac + 1, 5)));
                ciphertext.append(key.charAt(5 * br + Math.floorMod(bc + 1, 5)));
            } else if (ac == bc) {
                ciphertext.append(key.charAt(5 * Math.floorMod(ar + 1, 5) + ac));
                ciphertext.append(key.charAt(5 * Math.floorMod(br + 1, 5) + bc));
            } else {
                ciphertext.append(key.charAt(5 * ar + bc));
                ciphertext.append(key.charAt(5 * br + ac));
            }

        }

        return ciphertext.toString();
    }

    /**
     * @param ciphertext must be in [A-IK-Z]+, length of {@code ciphertext} must be even
     *                   and ciphertext must not contain two identical consecutive letters.
     * @param key        must be a permutation of letters in [A-IK-Z].
     * @return decryption of {@code ciphertext} under the {@code key}.
     */
    public static String decrypt(String ciphertext, String key) {
        StringBuilder plaintext = new StringBuilder();

        for (int i = 0; i < ciphertext.length(); i = i + 2) {
            char a = ciphertext.charAt(i);
            char b = ciphertext.charAt(i + 1);

            int av = key.indexOf(a);
            int bv = key.indexOf(b);

            int ar = av / 5;
            int ac = av % 5;
            int br = bv / 5;
            int bc = bv % 5;

            if (ar == br) {
                plaintext.append(key.charAt(5 * ar + Math.floorMod(ac - 1, 5)));
                plaintext.append(key.charAt(5 * br + Math.floorMod(bc - 1, 5)));
            } else if (ac == bc) {
                plaintext.append(key.charAt(5 * Math.floorMod(ar - 1, 5) + ac));
                plaintext.append(key.charAt(5 * Math.floorMod(br - 1, 5) + bc));
            } else {
                plaintext.append(key.charAt(5 * ar + bc));
                plaintext.append(key.charAt(5 * br + ac));
            }
        }

        return plaintext.toString();
    }

    /**
     * @param keyword must be in [A-Z]+.
     * @return playfair cipher key generated using {@code keyword}.
     */
    public static String generateKey(String keyword) {
        ArrayList<Character> key = new ArrayList<>();

        for (int i = 0; i < keyword.length(); i++) {
            char c = keyword.charAt(i);

            if (c == 'J')
                continue;

            if (key.contains(c))
                continue;

            key.add(c);
        }

        for (int i = 0; i < 26; i++) {
            char c = (char) (i + 'A');

            if (c == 'J')
                continue;

            if (key.contains(c))
                continue;

            key.add(c);
        }

        StringBuilder sb = new StringBuilder();
        for (char ch : key) {
            sb.append(ch);
        }

        return sb.toString();
    }

    public static String generateKey() {
        ArrayList<Character> key = new ArrayList<>(Arrays.asList('A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'K',
                'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'));

        Collections.shuffle(key);

        StringBuilder sb = new StringBuilder();

        for (Character ch : key) {
            sb.append(ch);
        }

        return sb.toString();
    }

}
