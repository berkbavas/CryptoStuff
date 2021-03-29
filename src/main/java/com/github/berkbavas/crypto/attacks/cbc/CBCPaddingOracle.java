package com.github.berkbavas.crypto.attacks.cbc;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.SecureRandom;

public class CBCPaddingOracle {
    private int queryCount = 0;
    private SecretKey key;

    public CBCPaddingOracle() {
        reset();
    }

    public boolean query(byte[] a, byte[] b) {
        return query(Util.concat(a, b));
    }

    public boolean query(byte[] queryArray) {
        try {

            byte[] plaintext = decrypt(queryArray);

        } catch (Exception e) {
            if (e instanceof BadPaddingException) {
                queryCount++;
                return false;
            }
            e.printStackTrace();
        }

        queryCount++;
        return true;
    }

    public byte[] decrypt(byte[] encrypted) throws Exception {
        if (encrypted.length < 32) {
            throw new IllegalArgumentException(String.format("Size of the input array must be greater than or equal to %d.", 32));
        }

        if (encrypted.length % 16 != 0) {
            throw new IllegalArgumentException(String.format("Size of the input array must be a multiple of %d.", 16));
        }

        // encrypted = iv + ciphertext
        byte[] iv = new byte[16];
        byte[] ciphertext = new byte[encrypted.length - iv.length];
        System.arraycopy(encrypted, 0, iv, 0, iv.length);
        System.arraycopy(encrypted, iv.length, ciphertext, 0, ciphertext.length);

        IvParameterSpec ivParameterSpec = new IvParameterSpec(iv);
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, key, ivParameterSpec);
        byte[] plaintext = cipher.doFinal(ciphertext);

        return plaintext;
    }

    public byte[] encrypt(byte[] plaintext) throws Exception {
        byte[] iv = new byte[16];
        SecureRandom sr = new SecureRandom();
        sr.nextBytes(iv);

        IvParameterSpec ivParameterSpec = new IvParameterSpec(iv);
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, key, ivParameterSpec);
        byte[] ciphertext = cipher.doFinal(plaintext);

        // result = iv + ciphertext
        byte[] result = new byte[iv.length + ciphertext.length];
        System.arraycopy(iv, 0, result, 0, iv.length);
        System.arraycopy(ciphertext, 0, result, iv.length, ciphertext.length);

        return result;
    }

    public int getQueryCount() {
        return queryCount;
    }

    public void reset() {
        byte[] bytes = new byte[16];
        SecureRandom sr = new SecureRandom();
        sr.nextBytes(bytes);
        key = new SecretKeySpec(bytes, "AES");
        queryCount = 0;
    }

    public SecretKey getKey() {
        return key;
    }
}
