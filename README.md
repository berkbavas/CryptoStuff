# Cryptographic Algorithms and Attacks

This repository consists of some cryptographic algorithms and attacks.
The algorithms are written in ``Java 8``.

You may use ``mvn compile`` and ``mvn test`` commands in order to run the tests.

## Content

- Algorithms
    - Asymmetric Cipher
        - RSA with PKCS1 v1.5 Padding
    - Traditional Ciphers
        - Four Square
        - Monoalphabetic
        - Playfair
        - Vigenere


- Attacks
    - CBC Padding Oracle Attack
    - RSA
        - Fermat Factorization
        - Miller Factorization
        - Kindle Signature Forgery Attack
        - An Attack for RSA Signature Without Any Padding Scheme
        - Close Primes
    - Attacks on Traditional Ciphers
        - Four Square
        - Monoalphabetic
        - Playfair
        - Vigenere
    
  ## Acknowledgment
- I follow the ideas for implementing the attacks on traditional ciphers described here: http://practicalcryptography.com/cryptanalysis/
- 