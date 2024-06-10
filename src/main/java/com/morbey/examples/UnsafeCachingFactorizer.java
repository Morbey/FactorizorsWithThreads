package com.morbey.examples;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicReference;

public class UnsafeCachingFactorizer {
    private final AtomicReference<BigInteger> lastNumberRef = new AtomicReference<BigInteger>();
    private final AtomicReference<BigInteger[]> lastFactorsRef = new AtomicReference<BigInteger[]>();

    public void service(int number) {
        BigInteger requestedNumber = extractFromRequest(number);
        BigInteger lastNumber = lastNumberRef.get();

        if (requestedNumber.equals(lastNumber)) {
            //System.out.println("Found cached number factor: [ " + requestedNumber + " | " + lastNumber + " | " + Arrays.toString(lastFactorsRef.get()) + "]");
        } else {
            BigInteger[] factors = Utils.factor(requestedNumber);
            //System.out.println("New number factor: [ " + requestedNumber + " | " + lastNumber + " | " + Arrays.toString(encodeIntoResponse(factors)) + "]");
            lastNumberRef.set(requestedNumber);
            lastFactorsRef.set(factors);

        }
    }

    BigInteger[] encodeIntoResponse(BigInteger[] factors) {
        return factors;
    }

    BigInteger extractFromRequest(int number) {
        return BigInteger.valueOf(number);
    }
}
