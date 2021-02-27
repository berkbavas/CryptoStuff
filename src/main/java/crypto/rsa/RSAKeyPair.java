package crypto.rsa;

public class RSAKeyPair {
    private final RSAPublicKey publicKey;
    private final RSAPrivateKey privateKey;

    public RSAKeyPair(RSAPrivateKey privateKey) {
        this.privateKey = privateKey;
        this.publicKey = privateKey.getPublicKey();
    }

    public RSAPublicKey getPublicKey() {
        return publicKey;
    }

    public RSAPrivateKey getPrivateKey() {
        return privateKey;
    }

    @Override
    public String toString() {
        return privateKey.toString();
    }

}
