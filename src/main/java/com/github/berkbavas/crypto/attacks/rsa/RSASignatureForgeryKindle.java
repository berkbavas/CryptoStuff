package com.github.berkbavas.crypto.attacks.rsa;

import java.math.BigInteger;
import java.util.Arrays;

/**
 * We assume that the verifier computes s^3 in modulo N and parses the resulting
 * byte array as 00 01 FF FF ... FF 00 HASH GARBAGE. Then the verifier compares
 * whether the hash of m is equal to HASH and he simply ignores GARBAGE.
 * <p>
 * The attack is performed by finding a number whose cube is 00 01 FF FF ... FF 00 HASH GARBAGE.
 * Such number is the signature that we are looking for.
 * We shall first calculate the cubic root, of 00 01 FF FF ... FF 00 HASH 00 ... 00, which may not be a integer.
 * Then we shall then take the integer part, say S, of the the cubic root of 00 01 FF FF ... FF 00 HASH 00... 00
 * and increment it until we find S^3 = 00 01 FF FF ... FF 00 HASH GARBAGE',
 * where GARBAGE' is an array of some arbitrary bytes.
 */

public final class RSASignatureForgeryKindle {

    private RSASignatureForgeryKindle() {
    }

    public static byte[] forgeSignature(byte[] hash, int modulusByteLength) {
        byte[] arr = new byte[modulusByteLength];
        arr[0] = 0x00;
        arr[1] = 0x01;
        Arrays.fill(arr, 2, 10, (byte) 0xff);
        Arrays.fill(arr, 10, 11, (byte) 0x00);
        System.arraycopy(hash, 0, arr, 11, hash.length);
        BigInteger num = new BigInteger(arr);
        BigInteger signature = Util.findRoot(num, 3, 0.01);
        BigInteger signatureCube = signature.pow(3);
        byte[] signatureCubeBytes = signatureCube.toByteArray();

        while (arr[hash.length + 10] != signatureCubeBytes[hash.length + 9]) {
            signature = signature.add(BigInteger.ONE);
            signatureCube = signature.pow(3);
            signatureCubeBytes = signatureCube.toByteArray();
        }

        byte[] signatureBytes = new byte[modulusByteLength];
        byte[] temp = signature.toByteArray();
        System.arraycopy(temp, 0, signatureBytes, (modulusByteLength - temp.length), temp.length);

        return signatureBytes;
    }


}
