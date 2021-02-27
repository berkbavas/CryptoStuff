package crypto.traditional;

public class CeasarCipher {
    private char key;

    public CeasarCipher() {
        key = (char) ('A' + Math.random() * 26);
    }

    public CeasarCipher(char key) {
        if (key < 'A' || 'Z' < key) {
            throw new IllegalArgumentException("Key is not in the the range [A, Z].");
        }

        this.key = key;
    }

    public CeasarCipher(int key) {
        if (key < 0 || 25 < key) {
            throw new IllegalArgumentException("Key must be in the range [0, 25].");
        }

        this.key = (char) ('A' + key);
    }

    public String encrypt(String plaintext) {
        if(plaintext.isEmpty())
            return "";

        if (!plaintext.matches("[A-Z]+")) {
            throw new IllegalArgumentException("Plaintext contains a character out of the range [A-Z].");
        }

        StringBuilder ciphertext = new StringBuilder();

        for (char ch : plaintext.toCharArray()) {
            int value = ch - 'A';
            int keyValue = key - 'A';
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

        for (char ch : ciphertext.toCharArray()) {
            int value = ch - 'A';
            int keyValue = key - 'A';
            int newValue = Math.floorMod(value - keyValue, 26);
            plaintext.append((char) (newValue + 'A'));
        }

        return plaintext.toString();
    }

    public String toString() {
        return String.valueOf(key);
    }

}
