package attacks.rsa;

import crypto.rsa.RSAKeyGenerator;
import crypto.rsa.RSAKeyPair;
import org.junit.jupiter.api.Test;

import java.math.BigInteger;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MillerFactorizationTest {

    @Test
    void factorize() {

        int[] bitLengths = {256, 512, 1024, 2048, 4096};

        System.out.println("------------------- MillerFactorization.java -------------------");

        for (int bitLength : bitLengths) {
            System.out.println("Generating " + bitLength + "-bit RSA Key...");
            RSAKeyPair keyPair = RSAKeyGenerator.generate(bitLength, 65537);

            BigInteger N = keyPair.getPublicKey().getModulus();
            BigInteger e = keyPair.getPublicKey().getPublicExponent();
            BigInteger d = keyPair.getPrivateKey().getPrivateExponent();
            BigInteger p = keyPair.getPrivateKey().getP();
            BigInteger q = keyPair.getPrivateKey().getQ();

            BigInteger[] primes = new BigInteger[2];

            if (p.compareTo(q) == -1) {
                primes[0] = p;
                primes[1] = q;
            } else {
                primes[1] = p;
                primes[0] = q;
            }

            System.out.println("Factorizing...");

            BigInteger[] factors = MillerFactorization.factorize(N, e, d);

            System.out.println("Miller Factorization is done.\n");

            assertEquals(primes[0], factors[0]);
            assertEquals(primes[1], factors[1]);

        }
    }
}