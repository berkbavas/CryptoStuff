package com.github.berkbavas.crypto.ciphers.traditional;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class MonoalphabeticCipherTest {

    @Test
    void encryptThenDecrypt() {
        final String plaintext = "ABCDEFGHIJKLMNOPQRSTUVWXYZABCDEFGHIJKLMNOPQRSTUVWXYZABCDEFGHIJKLMNOPQRSTUVWXYZ";

        for (int i = 0; i < 1000; i++) {
            String key = MonoalphabeticCipher.generateKey();
            String ciphertext = MonoalphabeticCipher.encrypt(plaintext, key);
            assertEquals(plaintext, MonoalphabeticCipher.decrypt(ciphertext, key));
        }

    }


    @Test
    void encrypt() {
        String key = "AZERTYUIOPQSDFGHJKLMWXCVBN";
        String plaintext = "HELLOWORLDMONOALPHABETICCIPHERTEST";
        assertEquals("ITSSGCGKSRDGFGASHIAZTMOEEOHITKMTLM", MonoalphabeticCipher.encrypt(plaintext, key));
    }


    @Test
    void decrypt() {
        String key = "AZERTYUIOPQSDFGHJKLMWXCVBN";
        String ciphertext = "ITSSGCGKSRDGFGASHIAZTMOEEOHITKMTLM";
        assertEquals("HELLOWORLDMONOALPHABETICCIPHERTEST", MonoalphabeticCipher.decrypt(ciphertext, key));
    }

    @Test
    void sanitize() {
        String text = "Many other computer programs are also capable of processing or creating plain text, such as countless programs in DOS, Windows, classic Mac OS, and Unix and its kin; as well as web browsers (a few browsers such as Lynx and the Line Mode Browser produce only plain text for display) and other e-text readers. Object.";
        String sanitized = "MANYOTHERCOMPUTERPROGRAMSAREALSOCAPABLEOFPROCESSINGORCREATINGPLAINTEXTSUCHASCOUNTLESSPROGRAMSINDOSWINDOWSCLASSICMACOSANDUNIXANDITSKINASWELLASWEBBROWSERSAFEWBROWSERSSUCHASLYNXANDTHELINEMODEBROWSERPRODUCEONLYPLAINTEXTFORDISPLAYANDOTHERETEXTREADERSOBJECT";
        assertEquals(0, MonoalphabeticCipher.sanitize(text).compareTo(sanitized));
    }

    @Test
    void keyValidation() {
        String key = MonoalphabeticCipher.generateKey();
        assertTrue(MonoalphabeticCipher.isKeyValid(key));
    }

}