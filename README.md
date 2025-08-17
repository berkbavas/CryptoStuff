# CryptoStuff

This project implements various cryptographic algorithms and attacks in **Java 8**.

## Features

- **Algorithms**
  - Asymmetric Cipher:
    - RSA with PKCS1 v1.5 Padding
  - Traditional Ciphers:
    - Four Square
    - Monoalphabetic
    - Playfair
    - Vigenere

- **Attacks**
  - CBC Padding Oracle Attack
  - RSA Attacks:
    - Fermat Factorization
    - Miller Factorization
    - Kindle Signature Forgery
    - Signature Without Padding
    - Close Primes
  - Attacks on Traditional Ciphers:
    - Four Square
    - Monoalphabetic
    - Playfair
    - Vigenere

## Getting Started

Compile the project:

```sh
mvn compile
```

Run tests:

```sh
mvn test
```

## Tests

Unit tests are provided for all implemented algorithms and attacks. See the `test/` directory for details.

## License

This project is licensed under the [MIT License](LICENSE).

## Acknowledgements

Attacks on traditional ciphers are based on solutions from [Practical Cryptography](http://practicalcryptography.com/cryptanalysis/).
