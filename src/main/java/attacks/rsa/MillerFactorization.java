package attacks.rsa;

import java.math.BigInteger;
import java.security.SecureRandom;

/**
 * Factoring RSA modulus for given e and d
 * <p>
 * See https://math.stackexchange.com/a/1839766
 */

public final class MillerFactorization {
    private static SecureRandom random = new SecureRandom();

    private MillerFactorization() {
    }

    // Finds factors of a RSA modulus N(=p*q) for given e and d
    public static BigInteger[] factorize(BigInteger N, BigInteger e, BigInteger d) {
        BigInteger N_0 = N.subtract(BigInteger.ONE);
        BigInteger TWO = new BigInteger("2");
        // kPhiN = k * \phi(N) = ed -1 where k is a positive integer.
        BigInteger kPhiN = d.multiply(e).subtract(BigInteger.ONE);
        // Set kPhiN = q where k * \phi(N) = 2^r * q and q is odd.
        kPhiN = getOddFactor(kPhiN);
        // choose random x \in (1, N-1)
        BigInteger x, y, p, q;
        BigInteger[] factors = new BigInteger[2];
        while (true) {
            // choose random x \in (1, N-1)
            x = getRandomInteger(BigInteger.ONE, N_0);
            // if gcd(x,N) != 1 then p = gcd(x,N) and q = N/p
            if (x.gcd(N).compareTo(BigInteger.ONE) != 0) {
                p = x.gcd(N);
                q = N.divide(p);

                if (p.compareTo(q) == -1) { // p < q
                    factors[0] = p;
                    factors[1] = q;
                } else {
                    factors[1] = p;
                    factors[0] = q;
                }

                return factors;
            }
            y = x.modPow(kPhiN, N);
            // if y != 1 (mod N) and y != -1 (mod N) then y might be a nontrivial square root of 1
            while ((y.compareTo(BigInteger.ONE) != 0) & (y.compareTo(N_0) != 0))
                // if y^2 = 1 mod N then y is a nontrivial square root of 1
                if (y.modPow(TWO, N).compareTo(BigInteger.ONE) == 0) {
                    // p = gcd(y-1, N) is a nontrivial factor of N
                    p = y.subtract(BigInteger.ONE).gcd(N);
                    q = N.divide(p);

                    if (p.compareTo(q) == -1) { // p < q
                        factors[0] = p;
                        factors[1] = q;
                    } else {
                        factors[1] = p;
                        factors[0] = q;
                    }

                    return factors;
                } else {
                    // y^2 (mod N) might be a nontrivial square root of 1
                    y = y.modPow(TWO, N);
                }
        }
        // if y = 1 (mod N) or y = -1 (mod N) then y is a trivial square root of 1
        // choose another random x \in (1, N-1)
    }


    // Returns a random BigInteger n1 and n2.
    public static BigInteger getRandomInteger(BigInteger n1, BigInteger n2) {
        if (n1.signum() != 1 || n2.signum() != 1)
            throw new IllegalArgumentException("n1 and n2 must be positive");
        BigInteger r = new BigInteger(n2.bitLength() - 1, random);
        while (r.compareTo(n1) != 1 & r.compareTo(n2) != -1) {
            r = new BigInteger(n2.bitLength() - 1, random);
        }

        return r;
    }

    // For given n, returns q such that n = 2^r*q where q is odd.
    public static BigInteger getOddFactor(BigInteger n) {
        int r = 0;
        while (!n.testBit(r)) {
            r++;
        }
        return n.shiftRight(r);
    }
}
