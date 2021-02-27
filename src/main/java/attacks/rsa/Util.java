package attacks.rsa;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;

public final class Util {
    private static final BigDecimal TWO = new BigDecimal("2");

    private Util() {
    }

    // Finds x such that |x^n - m| < precision and returns the closest integer to x
    public static BigInteger findRoot(BigInteger m, int n, double precision) {
        if (n <= 0) {
            throw new IllegalArgumentException("n must be a positive integer.");
        }

        if (m.signum() == 0) {
            return BigInteger.ZERO;
        } else if (m.signum() == -1) {
            if (n % 2 == 0) {
                throw new IllegalArgumentException("m must be a positive integer if n is even.");
            } else {
                BigInteger x = findRoot(m.negate(), n, precision);
                return x.negate();
            }

        }

        int upperBoundBitLength = (int) (Math.ceil(m.bitLength() * (1.0 / n)) + 1);
        int lowerBoundBitLength = (int) Math.max(0, Math.floor(m.bitLength() * (1.0 / n)) - 1);

        BigDecimal y = new BigDecimal(m);
        BigDecimal upper = new BigDecimal(BigInteger.ONE.shiftLeft(upperBoundBitLength));
        ;
        BigDecimal lower = new BigDecimal(BigInteger.ONE.shiftLeft(lowerBoundBitLength));
        BigDecimal mid = upper.subtract(lower).divide(TWO);
        BigDecimal result = mid.pow(n).subtract(y);
        while (result.abs().doubleValue() > precision) {
            mid = lower.add(upper.subtract(lower).divide(TWO));
            result = mid.pow(n).subtract(y);
            if (result.signum() == -1) {
                lower = mid;
            }
            if (result.signum() == 1) {
                upper = mid;
            }
        }

        return mid.setScale(0, RoundingMode.HALF_UP).toBigInteger();
    }

}
