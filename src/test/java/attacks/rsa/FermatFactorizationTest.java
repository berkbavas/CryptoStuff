package attacks.rsa;

import attacks.Helper;
import org.junit.jupiter.api.Test;

import java.math.BigInteger;

import static org.junit.jupiter.api.Assertions.assertEquals;

class FermatFactorizationTest {

    @Test
    void factorize() {
        int[] bitLengths = {32, 64, 128, 256, 512, 1024, 2048};
        System.out.println("-------------------- FermatFactorization.java ------------------");

        for (int bitLength : bitLengths) {
            System.out.println("Generating two " + bitLength + "-bit close prime numbers...");
            BigInteger[] primes = Helper.generateClosePrimeNumbers(bitLength);
            System.out.println("Prime numbers have been generated.");
            System.out.println("Difference is " + primes[0].subtract(primes[1]).abs() + ".");
            BigInteger N = primes[0].multiply(primes[1]);

            System.out.println("Fermat Factorization is called.");

            BigInteger[] factors = FermatFactorization.factorize(N);

            System.out.println("Fermat Factorization is done.\n");

            assertEquals(primes[0], factors[0]);
            assertEquals(primes[1], factors[1]);
        }
    }


}