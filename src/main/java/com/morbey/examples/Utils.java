package com.morbey.examples;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class Utils {

    static BigInteger[] factor(BigInteger n) {
        List<BigInteger> factors = new ArrayList<>();
        if (n.equals(BigInteger.valueOf(0))) {
            return factors.toArray(factors.toArray(new BigInteger[0]));
        }
        BigInteger divisor = BigInteger.valueOf(2);

        // Remove fatores de 2
        while (n.mod(divisor).equals(BigInteger.ZERO)) {
            factors.add(divisor);
            n = n.divide(divisor);
        }

        // Fatores ímpares
        divisor = BigInteger.valueOf(3);
        while (n.compareTo(BigInteger.ONE) > 0) {
            while (n.mod(divisor).equals(BigInteger.ZERO)) {
                factors.add(divisor);
                n = n.divide(divisor);
            }
            divisor = divisor.add(BigInteger.valueOf(2)); // Próximo número ímpar
        }

        return factors.toArray(new BigInteger[0]);
    }
}
