package attacks.rsa;

import org.junit.jupiter.api.Test;

import java.math.BigInteger;
import java.security.SecureRandom;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class UtilTest {
    private static SecureRandom random = new SecureRandom();

    @Test
    void findRootWhenInputIsSmall() {

        for (int i = 1; i < 100; i++) {
            assertEquals(new BigInteger("0"), Util.findRoot(new BigInteger("0"), i, 0.1));
            assertEquals(new BigInteger("1"), Util.findRoot(new BigInteger("1"), i, 0.1));
        }

        for (int i = 2; i < 1000; i++) {
            BigInteger m = new BigInteger(String.valueOf(i * i));
            assertEquals(new BigInteger(String.valueOf(i)), Util.findRoot(m, 2, 0.1));
        }


        {
            BigInteger m = new BigInteger("2");
            assertEquals(new BigInteger("2"), Util.findRoot(m, 1, 0.1));
            assertEquals(new BigInteger("1"), Util.findRoot(m, 2, 0.1));
        }


        {
            BigInteger m = new BigInteger("10");
            assertEquals(new BigInteger("3"), Util.findRoot(m, 2, 0.1));
            assertEquals(new BigInteger("2"), Util.findRoot(m, 3, 0.1));
            assertEquals(new BigInteger("2"), Util.findRoot(m, 4, 0.1));
        }


        {
            BigInteger m = new BigInteger("10000");
            assertEquals(new BigInteger("100"), Util.findRoot(m, 2, 0.1));
            assertEquals(new BigInteger("100"), Util.findRoot(m, 2, 0.01));
            assertEquals(new BigInteger("100"), Util.findRoot(m, 2, 0.001));
            assertEquals(new BigInteger("100"), Util.findRoot(m, 2, 0.001));
            assertEquals(new BigInteger("100"), Util.findRoot(m, 2, 0.0001));
            assertEquals(new BigInteger("100"), Util.findRoot(m, 2, 0.00001));
            assertEquals(new BigInteger("100"), Util.findRoot(m, 2, 0.000001));
            assertEquals(new BigInteger("100"), Util.findRoot(m, 2, 0.0000001));
        }


        {
            BigInteger m = new BigInteger("256");
            assertEquals(new BigInteger("16"), Util.findRoot(m, 2, 0.1));
            assertEquals(new BigInteger("4"), Util.findRoot(m, 4, 0.1));
            assertEquals(new BigInteger("2"), Util.findRoot(m, 8, 0.1));
        }


        {
            BigInteger m = new BigInteger("-729");
            assertEquals(new BigInteger("-9"), Util.findRoot(m, 3, 0.1));
        }


        {
            BigInteger m = new BigInteger("-27");
            assertEquals(new BigInteger("-3"), Util.findRoot(m, 3, 0.1));
        }

    }

    @Test
    void findRootWhenInputIsLarge() {

        int[] bitLengths = {32, 64, 128, 256, 512, 768, 1024, 2048, 3072, 4096};

        for (int i = 0; i < bitLengths.length; i++) {
            BigInteger x = new BigInteger(bitLengths[i], random).abs();
            BigInteger m = x.multiply(x);
            assertEquals(x, Util.findRoot(m, 2, 0.1));
        }

        for (int i = 0; i < bitLengths.length; i++) {
            BigInteger x = new BigInteger(bitLengths[i], random).abs();
            BigInteger m = x.multiply(x).add(BigInteger.ONE);
            assertEquals(x, Util.findRoot(m, 2, 0.1));

        }

        for (int i = 0; i < bitLengths.length; i++) {

            BigInteger x = new BigInteger(bitLengths[i], random).abs();
            BigInteger m = x.multiply(x).subtract(BigInteger.ONE);
            assertEquals(x, Util.findRoot(m, 2, 0.1));
        }


        {
            BigInteger x = new BigInteger(128, random).abs();
            BigInteger m = x.multiply(x);
            assertEquals(x, Util.findRoot(m, 2, 0.1));
            assertEquals(x, Util.findRoot(m, 2, 0.01));
            assertEquals(x, Util.findRoot(m, 2, 0.001));
            assertEquals(x, Util.findRoot(m, 2, 0.0001));
            assertEquals(x, Util.findRoot(m, 2, 0.00001));
        }

        {
            BigInteger x = new BigInteger(256, random).abs();
            BigInteger m = x;

            for (int i = 2; i < 10; i++) {
                m = m.multiply(x);
                assertEquals(x, Util.findRoot(m, i, 0.1));
            }
        }

    }


    @Test
    void findRootShouldThrownExceptionWhenArgumentsAreIllegal() {

        assertThrows(IllegalArgumentException.class, () -> {
            Util.findRoot(new BigInteger("1"), 0, 0.01);
        });

        assertThrows(IllegalArgumentException.class, () -> {
            Util.findRoot(new BigInteger("-1"), 2, 0.01);
        });

        assertThrows(IllegalArgumentException.class, () -> {
            Util.findRoot(new BigInteger("-1"), 4, 0.01);
        });


        assertThrows(IllegalArgumentException.class, () -> {
            Util.findRoot(new BigInteger("1"), -1, 0.01);
        });

        assertThrows(IllegalArgumentException.class, () -> {
            Util.findRoot(new BigInteger("-1"), -1, 0.01);
        });

    }

}