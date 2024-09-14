package com.github.berkbavas.crypto.attacks;

import java.math.BigInteger;
import java.security.SecureRandom;

public final class Helper {
    private static final BigInteger TWO = new BigInteger("2");
    private static final SecureRandom RANDOM_GENERATOR = new SecureRandom();

    public static BigInteger[] generateClosePrimeNumbers(int bitLength) {
        BigInteger p = BigInteger.probablePrime(bitLength, RANDOM_GENERATOR);
        BigInteger q = p.add(TWO);
        while (!q.isProbablePrime(40)) {
            q = q.add(TWO);
        }

        return new BigInteger[]{p, q};
    }
}
