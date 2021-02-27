package crypto.rsa;

import java.math.BigInteger;

public class RSAPublicKey {
    private final BigInteger modulus;
    private final BigInteger publicExponent;

    public RSAPublicKey(BigInteger modulus, BigInteger publicExponent) {
        this.modulus = modulus;
        this.publicExponent = publicExponent;
    }

    public BigInteger getModulus() {
        return modulus;
    }

    public BigInteger getPublicExponent() {
        return publicExponent;
    }

    public int getModulusByteLength() {
        return (int) Math.ceil(modulus.bitLength() / 8.0);
    }

    @Override
    public String toString() {
        return "Modulus: " + modulus.toString() + "\n" +
                "Public Exponent: " + publicExponent.toString();
    }

}
