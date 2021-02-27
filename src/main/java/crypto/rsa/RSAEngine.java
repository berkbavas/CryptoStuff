package crypto.rsa;

import java.math.BigInteger;

public final class RSAEngine {

    private RSAEngine() {
    }

    public static byte[] encrypt(byte[] messageBytes, RSAPublicKey pub) {
        if (messageBytes.length > pub.getModulusByteLength() - 11)
            throw new IllegalArgumentException("Message is too long for PKCS#1 v1.5 Padding.");
        BigInteger m = new BigInteger(PKCS1Padding.pad(messageBytes, pub.getModulusByteLength()));

        return encrypt(m, pub).toByteArray();
    }

    public static byte[] decrypt(byte[] c, RSAPrivateKey priv) {
        BigInteger message = decrypt(new BigInteger(c), priv);
        byte[] messageBytes = message.toByteArray();
        int publicKeyByteLength = priv.getPublicKey().getModulusByteLength();
        if (messageBytes.length < publicKeyByteLength) {
            byte[] temp = messageBytes;
            messageBytes = new byte[publicKeyByteLength];
            for (int i = 0; i < temp.length; i++) {
                messageBytes[publicKeyByteLength - temp.length + i] = temp[i];
            }
        }

        return PKCS1Padding.unpad(messageBytes);
    }

    private static BigInteger encrypt(BigInteger m, RSAPublicKey pub) {
        return m.modPow(pub.getPublicExponent(), pub.getModulus());
    }

    private static BigInteger decrypt(BigInteger c, RSAPrivateKey priv) {
        BigInteger p = priv.getP();
        BigInteger q = priv.getQ();
        BigInteger dP = priv.getdP();
        BigInteger dQ = priv.getdQ();
        BigInteger inverseQ = priv.getInverseQ();

        BigInteger c1 = c.modPow(dP, p);
        BigInteger c2 = c.modPow(dQ, q);
        BigInteger h = (inverseQ.multiply(c1.subtract(c2))).mod(p);

        return c2.add(h.multiply(q));
    }

}
