package com.morbey.examples;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicReference;

public class CachedFactorizer {

    private final AtomicReference<BigInteger> lastNumber = new AtomicReference<>();
    private final AtomicReference<BigInteger[]> lastFactors = new AtomicReference<>();

    public void service(int num) {

        BigInteger[] factors;
        BigInteger number = extractFromRequest(num);
        synchronized (this) {
            if (number.equals(lastNumber)) {
                //System.out.println("Found cached number factor: [ " + number + " | " + lastNumber + " | " + Arrays.toString(encodeIntoResponse(lastFactors.get())) + "]");
                return;
            }
            factors = Utils.factor(number);
            //System.out.println("New number factor: [ " + number + " | " + lastNumber + " | " + Arrays.toString(encodeIntoResponse(factors)) + "]");
            lastFactors.set(factors);
            lastNumber.set(number);
        }
    }

    BigInteger[] encodeIntoResponse(BigInteger[] factors) {
        return factors;
    }

    BigInteger extractFromRequest(int number) {
        return BigInteger.valueOf(number);
    }
}
