package com.github.berkbavas.crypto.attacks.rsa;

import java.math.BigInteger;

public final class FermatFactorization {

    private FermatFactorization() {

    }

    public static BigInteger[] factorize(BigInteger n) {
        BigInteger[] factors = new BigInteger[2];

        if (n.signum() == -1) {
            throw new IllegalArgumentException("n must be a positive integer.");
        }

        BigInteger a = Util.findRoot(n, 2, 0.01);

        if (a.pow(2).compareTo(n) == 0) {
            factors[0] = a;
            factors[1] = a;
            return factors;
        }

        BigInteger b2 = a.pow(2).subtract(n);

        while (!isSquare(b2)) {
            a = a.add(BigInteger.ONE);
            b2 = a.pow(2).subtract(n);
        }


        BigInteger f1 = a.subtract(Util.findRoot(b2, 2, 0.01));
        BigInteger f2 = n.divide(f1);

        if (f1.compareTo(f2) == -1) // f1 < f2
        {
            factors[0] = f1;
            factors[1] = f2;
        } else {
            factors[0] = f2;
            factors[1] = f1;
        }

        return factors;
    }

    private static boolean isSquare(BigInteger x) {
        if (x.signum() == -1) {
            return false;
        }

        BigInteger root = Util.findRoot(x, 2, 0.01);

        return root.pow(2).compareTo(x) == 0;
    }
}
