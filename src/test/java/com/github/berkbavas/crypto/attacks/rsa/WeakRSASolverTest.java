package com.github.berkbavas.crypto.attacks.rsa;

import com.github.berkbavas.crypto.attacks.Helper;
import org.junit.jupiter.api.Test;

import java.math.BigInteger;

import static org.junit.jupiter.api.Assertions.assertEquals;

class WeakRSASolverTest {

    @Test
    void factorize() {
        int[] bitLengths = {32, 64, 128, 256, 512, 1024, 2048};
        System.out.println("WeakRSASolverTest.java: Test is started.");

        for (int bitLength : bitLengths) {
            System.out.println("WeakRSASolverTest.java: Generating two " + bitLength + "-bit close prime numbers...");
            BigInteger[] primes = Helper.generateClosePrimeNumbers(bitLength);
            System.out.println("WeakRSASolverTest.java: Prime numbers have been generated.");
            System.out.println("WeakRSASolverTest.java: Difference is " + primes[0].subtract(primes[1]).abs() + ".");
            BigInteger N = primes[0].multiply(primes[1]);

            System.out.println("WeakRSASolverTest.java: Weak RSA Solver is called.");

            BigInteger[] factors = WeakRSASolver.factorize(N);

            System.out.println("WeakRSASolverTest.java: Weak RSA Solver is done.");

            assertEquals(primes[0], factors[0]);
            assertEquals(primes[1], factors[1]);
        }
    }
}