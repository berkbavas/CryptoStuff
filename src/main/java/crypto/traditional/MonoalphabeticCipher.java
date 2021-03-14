package crypto.traditional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public final class MonoalphabeticCipher {

    private MonoalphabeticCipher() {
    }

    /**
     * @param plaintext must be in [A-Z]+.
     * @param key       must be a permutation of letters in [A-Z].
     * @return encryption of {@code plaintext} under the {@code key}.
     */
    public static String encrypt(String plaintext, String key) {
        StringBuilder ciphertext = new StringBuilder();

        for (int i = 0; i < plaintext.length(); i++) {
            int value = plaintext.charAt(i) - 'A';
            ciphertext.append(key.charAt(value));
        }

        return ciphertext.toString();
    }

    /**
     * @param ciphertext must be in [A-Z]+.
     * @param key        must be a permutation of letters in [A-Z].
     * @return decryption of {@code ciphertext} under the {@code key}.
     */
    public static String decrypt(String ciphertext, String key) {
        StringBuilder plaintext = new StringBuilder();

        for (int i = 0; i < ciphertext.length(); i++) {
            char c = ciphertext.charAt(i);
            int value = key.indexOf(c);
            plaintext.append((char) (value + 'A'));
        }

        return plaintext.toString();
    }

    public static String generateKey() {
        ArrayList<Character> key = new ArrayList<>(Arrays.asList('A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K',
                'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'));

        Collections.shuffle(key);

        StringBuilder sb = new StringBuilder();

        for (Character ch : key) {
            sb.append(ch);
        }

        return sb.toString();
    }


}
