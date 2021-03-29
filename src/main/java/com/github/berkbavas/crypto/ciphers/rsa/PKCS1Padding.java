package com.github.berkbavas.crypto.ciphers.rsa;

import java.security.SecureRandom;

public final class PKCS1Padding {

    private PKCS1Padding() {
    }

    // PKCS#1 v1.5 Padding
    public static byte[] pad(byte[] data, int length) {
        byte[] paddedData = new byte[length];
        byte[] temp = new byte[1];
        SecureRandom sr = new SecureRandom();
        paddedData[0] = 0;
        paddedData[1] = 2;
        for (int i = 2; i < length - data.length - 1; i++) {
            sr.nextBytes(temp);
            while (temp[0] == 0) {
                sr.nextBytes(temp);
            }
            paddedData[i] = temp[0];
        }
        paddedData[length - data.length - 1] = 0;
        for (int i = 0; i < data.length; i++) {
            paddedData[length - data.length + i] = data[i];
        }

        return paddedData;
    }

    // PKCS#1 v1.5 Padding
    public static byte[] unpad(byte[] paddedData) {
        if (paddedData[0] != 0 || paddedData[1] != 2)
            throw new IllegalArgumentException("PKCS#1 v1.5 padding is invalid.");
        int index = 0;
        for (int i = 2; i < paddedData.length; i++) {
            if (paddedData[i] == 0) {
                index = i + 1;
                break;
            }
        }

        byte[] result = new byte[paddedData.length - index];

        for (int i = 0; i < result.length; i++) {
            result[i] = paddedData[index + i];
        }

        return result;
    }

}
