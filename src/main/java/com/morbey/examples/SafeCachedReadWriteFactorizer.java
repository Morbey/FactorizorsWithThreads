package com.morbey.examples;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class SafeCachedReadWriteFactorizer {

    private AtomicReference<BigInteger> lastNumber = new AtomicReference<>();
    private AtomicReference<BigInteger[]> lastFactors = new AtomicReference<>();
    ReadWriteLock readWriteLock = new ReentrantReadWriteLock();
    Lock readLock = readWriteLock.readLock();
    Lock writeLock = readWriteLock.writeLock();

    public void service(int num) {

        BigInteger[] factors = null;
        BigInteger number = extractFromRequest(num);
        boolean isCached = false;

        readLock.lock();
        try {
            if (number.equals(lastNumber.get())) {
                //System.out.println("Found cached number factor: [ " + number + " | " + lastNumber + " | " + Arrays.toString(encodeIntoResponse(lastFactors.get())) + "]");
                return;
            }
        } finally {
            readLock.unlock();
        }

        factors = Utils.factor(number);

        try {
            //System.out.println("New number factor: [ " + number + " | " + lastNumber + " | " + Arrays.toString(encodeIntoResponse(factors)) + "]");
            writeLock.lock();
            lastFactors.set(factors);
            lastNumber.set(number);
            //System.out.println("New number factor: [ " + number + " | " + lastNumber + " | " + Arrays.toString(encodeIntoResponse(factors)) + "]");
        } finally {
            writeLock.unlock();
        }
    }

    BigInteger[] encodeIntoResponse(BigInteger[] factors) {
        return factors;
    }

    BigInteger extractFromRequest(int number) {
        return BigInteger.valueOf(number);
    }
}
