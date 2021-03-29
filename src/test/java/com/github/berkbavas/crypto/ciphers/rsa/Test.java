package com.github.berkbavas.crypto.ciphers.rsa;

public class Test {

    public static void f(int modulusBitLength, int e) {
        RSAKeyPair keyPair = RSAKeyGenerator.generate(modulusBitLength, e);
        RSAPublicKey publicKey = keyPair.getPublicKey();
        RSAPrivateKey privateKey = keyPair.getPrivateKey();
        System.out.println(keyPair);

        byte[] m = "Hello World!".getBytes();
        byte[] c = RSAEngine.encrypt(m, publicKey);
        byte[] p = RSAEngine.decrypt(c, privateKey);
        System.out.println(new String(p));
    }

    public static void main(String[] args) {

        // Test 1: 1024 bit RSA Key with e = 3
        {
            System.out.println("-------------- Test 1: 1024-bit RSA Key with e = 3 --------------");
            f(1024, 3);
            System.out.println();
        }

        // Test 2: 2048 bit RSA Key with e = 3
        {
            System.out.println("-------------- Test 2: 2048-bit RSA Key with e = 3 --------------");
            f(2048, 3);
            System.out.println();
        }

        // Test 3: 3072 bit RSA Key with e = 65537
        {
            System.out.println("------------ Test 3: 3072-bit RSA Key with e = 65537 ------------");
            f(3072, 65537);
            System.out.println();
        }

        // Test 4: 4096 bit RSA Key with e = 65537
        {
            System.out.println("------------ Test 4: 4096-bit RSA Key with e = 65537 ------------");
            f(4096, 65537);
            System.out.println();
        }

    }

}
