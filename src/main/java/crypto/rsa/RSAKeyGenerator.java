package crypto.rsa;

import java.math.BigInteger;
import java.security.SecureRandom;

public final class RSAKeyGenerator {

    private RSAKeyGenerator() {
    }

    public static RSAKeyPair generate(int modulusBitLength, int publicExponent) {
        if (modulusBitLength < 256) {
            throw new IllegalArgumentException("Modulus bit length must be greater than 255 bits.");
        }

        if (modulusBitLength % 16 != 0) {
            throw new IllegalArgumentException("Modulus bit length must be a multiple of 16.");
        }

        if ((publicExponent != 3) && (publicExponent != 65537)) {
            throw new IllegalArgumentException("Public exponent must be 3 or 65537.");
        }

        BigInteger e = BigInteger.valueOf(publicExponent);
        BigInteger[] primes = generatePrimes(modulusBitLength / 2, e);
        RSAPrivateKey privateKey = new RSAPrivateKey(primes[0], primes[1], e);

        return new RSAKeyPair(privateKey);
    }

    private static BigInteger[] generatePrimes(int bitLength, BigInteger e) {
        BigInteger p = null;
        BigInteger q = null;
        int modulusBitLength = 2 * bitLength;
        BigInteger safeMargin = new BigInteger("2").pow(bitLength - 100);
        SecureRandom sr = new SecureRandom();
        byte[] randomBytes = new byte[bitLength / 8 + 1];
        boolean isPrime = false;

        while (!isPrime) {
            sr.nextBytes(randomBytes);
            randomBytes[0] = 0;
            while (randomBytes[1] >= 0) {
                sr.nextBytes(randomBytes);
                randomBytes[0] = 0;
            }
            p = new BigInteger(randomBytes);
            if (!p.subtract(BigInteger.ONE).gcd(e).equals(BigInteger.ONE)) {
                continue;
            }

            if (p.isProbablePrime(40)) {
                isPrime = true;
            }

        }

        isPrime = false;

        while (!isPrime) {
            sr.nextBytes(randomBytes);
            randomBytes[0] = 0;
            while (randomBytes[1] >= 0) {
                sr.nextBytes(randomBytes);
                randomBytes[0] = 0;
            }

            q = new BigInteger(randomBytes);

            if (q.multiply(p).bitLength() != modulusBitLength) {
                continue;
            }

            if (p.subtract(q).abs().compareTo(safeMargin) != 1) {
                continue;
            }

            if (!q.subtract(BigInteger.ONE).gcd(e).equals(BigInteger.ONE)) {
                continue;
            }

            if (q.isProbablePrime(40)) {
                isPrime = true;
            }

        }

        BigInteger[] primes = new BigInteger[2];
        primes[0] = p;
        primes[1] = q;

        return primes;
    }

}
