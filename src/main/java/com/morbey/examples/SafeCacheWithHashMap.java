package com.morbey.examples;

import java.math.BigInteger;
import java.util.concurrent.ConcurrentHashMap;

public class SafeCacheWithHashMap {

    private final ConcurrentHashMap<BigInteger, BigInteger[]> cache = new ConcurrentHashMap<>();

    public void service(int number) {

        BigInteger requestedNumber = extractFromRequest(number);
        BigInteger[] factor;

        // check if number is in cache
        if (!cache.containsKey(requestedNumber)) {
            // if no, save new cache value
            Utils.factor(requestedNumber);
            factor = Utils.factor(requestedNumber);
            cache.put(requestedNumber, factor);
            //System.out.println("New number factor to cache: [ " + requestedNumber + " | " + Arrays.toString(encodeIntoResponse(factor)) + "]");
        }
        else {
            // return stored value on map with the factor
            //System.out.println("Found cached number factor: [ " + requestedNumber + " | " + Arrays.toString(encodeIntoResponse(cache.get(requestedNumber))) + "]");
        }
    }

    BigInteger[] encodeIntoResponse(BigInteger[] factors) {
        return factors;
    }

    BigInteger extractFromRequest(int number) {
        return BigInteger.valueOf(number);
    }
}
