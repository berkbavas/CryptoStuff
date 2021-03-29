package com.github.berkbavas.crypto.attacks.rsa;

import java.math.BigInteger;

/**
 * Let N = p * q be a RSA modulus such that |p - q| is small.
 * Then we can factor N as follows:
 * 1) Find square root of N.
 * 2) Take the integer part, say q', of the square root of N.
 * 3) Increment q' until we get N % q' = 0.
 * 4) If N % q' = 0, then q (= q') and p = N / q are the factors of N.
 */

public final class WeakRSASolver {
    public final static BigInteger TWO = new BigInteger("2");

    private WeakRSASolver() {

    }

    public static BigInteger[] factorize(BigInteger n) {
        BigInteger[] factors = new BigInteger[2];

        if (n.signum() == -1) {
            factors = factorize(n.abs());
            factors[0] = factors[0].negate();
            return factors;
        }

        BigInteger squareRoot = Util.findRoot(n, 2, 0.001);

        if (squareRoot.pow(2).compareTo(n) == 0) {
            factors[0] = squareRoot;
            factors[1] = squareRoot;
            return factors;
        }

        if (squareRoot.mod(TWO).signum() == 0)
            squareRoot = squareRoot.add(BigInteger.ONE);

        while (n.mod(squareRoot).signum() != 0) {
            squareRoot = squareRoot.add(TWO);
        }

        factors[1] = squareRoot;
        factors[0] = n.divide(squareRoot);

        return factors;
    }

}
