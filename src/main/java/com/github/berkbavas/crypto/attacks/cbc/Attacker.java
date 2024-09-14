package com.github.berkbavas.crypto.attacks.cbc;

import java.util.Arrays;

public class Attacker {
    private CBCPaddingOracle oracle;

    public Attacker(CBCPaddingOracle oracle) {
        this.oracle = oracle;
    }

    public byte[] recover(byte[] ciphertext) {
        if (ciphertext.length < 32) {
            throw new IllegalArgumentException(String.format("Size of the ciphertext must be greater than or equal to %d.", 32));
        }

        if (ciphertext.length % 16 != 0) {
            throw new IllegalArgumentException(String.format("Size of the ciphertext must be a multiple of %d.", 16));
        }

        byte[] plaintext = new byte[ciphertext.length - 16];
        int blockCount = ciphertext.length / 16;
        for (int i = 1; i < blockCount; i++) {
            byte[] previousBlock = new byte[16];
            byte[] currentBlock = new byte[16];
            System.arraycopy(ciphertext, 16 * (i - 1), previousBlock, 0, 16);
            System.arraycopy(ciphertext, 16 * i, currentBlock, 0, 16);
            byte[] recoveredBlock = recoverBlock(previousBlock, currentBlock);
            System.arraycopy(recoveredBlock, 0, plaintext, 16 * (i - 1), 16);
        }

        return Util.unpad(plaintext);
    }

    private byte[] recoverBlock(byte[] previousBlock, byte[] currentBlock) {
        byte[] plaintextBlock = new byte[16];
        if (oracle.query(previousBlock, currentBlock)) {
            int index = 15;
            for (int i = 0; i < 15; i++) {
                ++previousBlock[i];
                if (!oracle.query(previousBlock, currentBlock)) {
                    --previousBlock[i];
                    index = i;
                    break;
                }
                --previousBlock[i];
            }
            Arrays.fill(plaintextBlock, index, 16, (byte) (16 - index));
            byte[] newPreviousBlock = new byte[16];
            --index;
            for (int i = index; i >= 0; i--) {
                System.arraycopy(previousBlock, 0, newPreviousBlock, 0, 16);
                for (int j = 15; j > i; j--) {
                    newPreviousBlock[j] = (byte) (plaintextBlock[j] ^ previousBlock[j] ^ (16 - i));
                }
                ++newPreviousBlock[i];
                while (!oracle.query(newPreviousBlock, currentBlock)) {
                    ++newPreviousBlock[i];
                }
                plaintextBlock[i] = (byte) (newPreviousBlock[i] ^ previousBlock[i] ^ (16 - i));
            }

        } else {
            byte temp = previousBlock[15];
            do {
                previousBlock[15]++;
            } while (!oracle.query(previousBlock, currentBlock));
            plaintextBlock = recoverBlock(previousBlock, currentBlock);
            plaintextBlock[15] = (byte) (plaintextBlock[15] ^ previousBlock[15] ^ temp);

        }
        return plaintextBlock;
    }

    public CBCPaddingOracle getOracle() {
        return oracle;
    }

    public void setOracle(CBCPaddingOracle oracle) {
        this.oracle = oracle;
    }


}