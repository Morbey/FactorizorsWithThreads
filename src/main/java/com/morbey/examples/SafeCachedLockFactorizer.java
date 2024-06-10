package com.morbey.examples;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class SafeCachedLockFactorizer {

    private AtomicReference<BigInteger> lastNumber = new AtomicReference<>();
    private AtomicReference<BigInteger[]> lastFactors = new AtomicReference<>();
    Lock lock = new ReentrantLock();

    public void service(int num) {

        BigInteger[] factors = null;
        BigInteger number = extractFromRequest(num);
        boolean isCached = false;

        try {
            lock.lock();
            if (number.equals(lastNumber.get())) {
                //System.out.println("Found cached number factor: [ " + number + " | " + lastNumber + " | " + Arrays.toString(encodeIntoResponse(lastFactors.get())) + "]");
                return;
            }
        } finally {
            lock.unlock();
        }

        factors = Utils.factor(number);

        try {
            lock.lock();
            //System.out.println("New number factor: [ " + number + " | " + lastNumber + " | " + Arrays.toString(encodeIntoResponse(factors)) + "]");
            lastFactors.set(factors);
            lastNumber.set(number);

        } finally {
            lock.unlock();
        }
    }

    BigInteger[] encodeIntoResponse(BigInteger[] factors) {
        return factors;
    }

    BigInteger extractFromRequest(int number) {
        return BigInteger.valueOf(number);
    }
}
