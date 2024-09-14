package com.github.berkbavas.crypto.attacks.cbc;

public class Util {

    public static byte[] xor(byte[] a, byte[] b) {
        if (a.length != b.length) {
            throw new IllegalArgumentException("Input byte arrays are not of same length.");
        }

        byte[] result = new byte[a.length];

        for (int i = 0; i < a.length; i++) {
            result[i] = (byte) (a[i] ^ b[i]);
        }

        return result;
    }

    public static byte[] concat(byte[] a, byte[] b) {
        byte[] result = new byte[a.length + b.length];
        System.arraycopy(a, 0, result, 0, a.length);
        System.arraycopy(b, 0, result, a.length, b.length);

        return result;
    }

    public static byte[] unpad(byte[] data) {
        if (data.length % 16 != 0) {
            throw new IllegalArgumentException("Size of the input array must be a multiple of 16.");
        }

        byte[] lastBlock = new byte[16];
        System.arraycopy(data, data.length - 16, lastBlock, 0, 16);

        if (!isPaddingValid(lastBlock)) {
            throw new IllegalArgumentException("Padding is not valid.");
        }

        int value = lastBlock[15] & 0xff;
        byte[] unpadded = new byte[16 - value];
        System.arraycopy(lastBlock, 0, unpadded, 0, unpadded.length);

        byte[] result = new byte[data.length - 16 + unpadded.length];

        System.arraycopy(data, 0, result, 0, data.length - 16);
        System.arraycopy(unpadded, 0, result, data.length - 16, unpadded.length);

        return result;
    }

    public static boolean isPaddingValid(byte[] dataBlock) {
        byte lastByte = dataBlock[15];
        int value = lastByte & 0xff;
        if (value == 0)
            return false;
        if (value > 16)
            return false;

        for (int i = 15; i >= 16 - value; i--) {
            if (dataBlock[i] != lastByte) {
                return false;
            }
        }

        return true;
    }


}
