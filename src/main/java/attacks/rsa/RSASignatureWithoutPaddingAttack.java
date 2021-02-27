package attacks.rsa;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * An elementary attack for RSA signature scheme that does not include any padding
 * mechanism.
 * <p>
 * To sign a message m, the signer first computes hash H(m) of m then
 * calculates signature s \equiv H(m)^d mod N, where d is the private exponent
 * and N is the RSA modulus.
 * <p>
 * We assume that the hash function H is SHA256 and m is chosen such that H(m) is odd.
 * We further assume N > 2^768.
 * The forged signature s is the cubic root of H(m) modulo 2^256.
 * The cubic root of an odd integer x in modulo 2^256 is x^invThree mod 2^256,
 * where invThree is the multiplicative inverse of 3 in mod 2^255,
 * so that x^(3* invThree) \equiv x mod 2^256 by Euler's theorem.
 * So for an odd integer H(m) we have s^3 \equiv H(m)^(3 * invThree) mod 2^256 \equiv H(m) mod 2^256.
 * <p>
 * We also assume that the verification of the signature is simply done by
 * checking whether the last 32 bytes of s^3 mod N is equal to H(m), that is,
 * the verifier checks whether s^3 in modulo 2^256 is equivalent to H(m) in
 * modulo 2^256.
 */

public final class RSASignatureWithoutPaddingAttack {
    private static final BigInteger POW255 = BigInteger.ONE.shiftLeft(255);
    private static final BigInteger POW256 = BigInteger.ONE.shiftLeft(256);
    private static final BigInteger THREE = new BigInteger("3");
    private static final BigInteger INV_THREE = THREE.modInverse(POW255);

    private RSASignatureWithoutPaddingAttack() {
    }

    /**
     * Forging a signature is simply performed by calculating the cubic root of the
     * H(m) in modulo 2^256. To calculate cubic root of an integer H(m) in modulo
     * 2^256, H(m) and 2^256 must be relatively prime.
     * Hence H(m) must be odd, in other words, its last bit must be 1.
     */
    public static BigInteger forgeSignature(String m) throws NoSuchAlgorithmException {
        byte[] bytes = m.getBytes(StandardCharsets.UTF_8);
        byte[] hash = MessageDigest.getInstance("SHA-256").digest(bytes);

        if (isOdd(hash))
            return forgeSignature(new BigInteger(hash));
        else
            throw new IllegalArgumentException("H(m) must be even.");
    }

    public static BigInteger forgeSignature(byte[] m) throws NoSuchAlgorithmException {
        byte[] hash = MessageDigest.getInstance("SHA-256").digest(m);

        if (isOdd(hash))
            return forgeSignature(new BigInteger(hash));
        else
            throw new IllegalArgumentException("H(m) must be even.");
    }

    /**
     * Forged signature is s \equiv H(m)^invThree mod 2^256, where invThere is the
     * multiplicative inverse of 3 in modulo 2^255 and H(m) is odd.
     * Since s^3 \equiv H(m)^(invThree * 3) mod 2^256 \equiv H(m) mod 2^256,
     * it follows that s^3 mod 2^256 \equiv H(m) mod 2^256 \equiv H(m) mod N
     * provided that s^3 \leq N.
     * Note that s^3 \leq N if 2^(3*256) = 2^768 \leq N,
     * that is to say N must be greater than 768 bits.
     */
    public static boolean verify(String m, BigInteger signature)
            throws NoSuchAlgorithmException {
        byte[] bytes = m.getBytes(StandardCharsets.UTF_8);
        byte[] hash = MessageDigest.getInstance("SHA-256").digest(bytes);
        int result = new BigInteger(hash).mod(POW256).compareTo(signature.modPow(THREE, POW256));
        return result == 0;
    }

    public static boolean verify(byte[] m, BigInteger signature)
            throws NoSuchAlgorithmException {
        byte[] hash = MessageDigest.getInstance("SHA-256").digest(m);
        int result = new BigInteger(hash).mod(POW256).compareTo(signature.modPow(THREE, POW256));
        return result == 0;
    }

    private static BigInteger forgeSignature(BigInteger m) {
        if (!m.testBit(0)) {
            throw new IllegalArgumentException("m must be odd.");
        }

        return calculateCubicRoot(m);
    }

    /**
     * Calculates the cubic root of given odd integer modulo 2^256.
     * To find the cubic root of an odd integer x modulo 2^256,
     * we need to calculate x^invThree modulo 2^256,
     * where invThere is the multiplicative inverse of 3 in modulo 2^255.
     * Then by Euler's theorem we obtain x^(3 * invThree) \equiv 1 mod 2^256 provided that gcd(x, 2^256) = 1.
     */
    private static BigInteger calculateCubicRoot(BigInteger input) {

        return input.modPow(INV_THREE, POW256);
    }

    /**
     * Checks whether the last bit of input is 1.
     */
    private static boolean isOdd(byte[] input) {

        return (input[input.length - 1] & 0x01) == 1;
    }
}
