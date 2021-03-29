package com.github.berkbavas.crypto.attacks;

import java.math.BigInteger;
import java.security.SecureRandom;

public final class Helper {
    private static final BigInteger TWO = new BigInteger("2");
    private static SecureRandom random = new SecureRandom();


    public static BigInteger[] generateClosePrimeNumbers(int bitLength) {
        BigInteger p = BigInteger.probablePrime(bitLength, random);
        BigInteger q = p.add(TWO);
        while (!q.isProbablePrime(40)) {
            q = q.add(TWO);
        }

        return new BigInteger[]{p, q};
    }
}
