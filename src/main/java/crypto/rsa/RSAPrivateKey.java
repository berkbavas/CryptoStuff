package crypto.rsa;

import java.math.BigInteger;

public class RSAPrivateKey {
    private final BigInteger p;
    private final BigInteger q;
    private final BigInteger phiN;
    private final BigInteger privateExponent;
    private final BigInteger inverseQ;
    private final BigInteger dP;
    private final BigInteger dQ;
    private final RSAPublicKey publicKey;

    public RSAPrivateKey(BigInteger p, BigInteger q, BigInteger publicExponent) {
        this.p = p;
        this.q = q;
        phiN = p.subtract(BigInteger.ONE).multiply(q.subtract(BigInteger.ONE));
        privateExponent = publicExponent.modInverse(phiN);
        inverseQ = q.modInverse(p);
        dP = privateExponent.mod(p.subtract(BigInteger.ONE));
        dQ = privateExponent.mod(q.subtract(BigInteger.ONE));
        publicKey = new RSAPublicKey(p.multiply(q), publicExponent);
    }

    public BigInteger getP() {
        return p;
    }

    public BigInteger getQ() {
        return q;
    }

    public BigInteger getPhiN() {
        return phiN;
    }

    public BigInteger getPrivateExponent() {
        return privateExponent;
    }

    public BigInteger getInverseQ() {
        return inverseQ;
    }

    public BigInteger getdP() {
        return dP;
    }

    public BigInteger getdQ() {
        return dQ;
    }

    public RSAPublicKey getPublicKey() {
        return publicKey;
    }

    @Override
    public String toString() {
        return publicKey.toString() + "\n" +
                "Private Exponent: " + privateExponent.toString() + "\n" +
                "Phi of N: " + phiN.toString() + "\n" +
                "p: " + p.toString() + "\n" +
                "q: " + q.toString() + "\n" +
                "dP: " + dP.toString() + "\n" +
                "dQ: " + dQ.toString();
    }

}
