package com.morbey;

import com.morbey.examples.CachedFactorizer;
import com.morbey.examples.SafeCacheWithHashMap;
import com.morbey.examples.SafeCachedLockFactorizer;

import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {

    private static final int POOL_SIZE = 10;

    public static void main(String[] args) throws InterruptedException {
        long startTime1, startTime2, startTime3;
        long endTime1, endTime2, endTime3;
        long totalDuration1 = 0, totalDuration2 = 0, totalDuration3 = 0;
        int repetitions = 20;

        for (int i = 0; i < repetitions; i++) {
            startTime1 = System.nanoTime();
            testSafeCacheWithSynchronized();
            endTime1 = System.nanoTime();
            totalDuration1 += (endTime1 - startTime1);
        }
        Thread.sleep(10000);
        System.out.println(" ------------- Total safe cache with synch  : " + totalDuration1 / (1000*repetitions) + " microseconds");

        for (int i = 0; i < repetitions; i++) {
            startTime2 = System.nanoTime();
            testSafeCacheWithLocks();
            endTime2 = System.nanoTime();
            totalDuration2 += (endTime2 - startTime2);
        }
        Thread.sleep(10000);
        System.out.println(" ------------- Total safe cache with locks  : " + totalDuration2 / (1000*repetitions) + " microseconds");

        for (int i = 0; i < repetitions; i++) {
            startTime3 = System.nanoTime();
            testSafeCacheWithHashMap();
            endTime3 = System.nanoTime();
            totalDuration3 += (endTime3 - startTime3);
        }
        Thread.sleep(10000);
        System.out.println(" ------------- Total safe cache with hashmap: " + totalDuration3 / (1000*repetitions) + " microseconds");
    }

    private static void testSafeCacheWithSynchronized() {
        CachedFactorizer safeCache = new CachedFactorizer();
        final ExecutorService executor1 = Executors.newFixedThreadPool(POOL_SIZE);
        try {
            // Simula múltiplos threads chamando o método `service`
            for (int i = 1; i < 10000; i++) {
                final int num = i % 5; // Alterna entre alguns números para aumentar a chance de colisão
                executor1.submit(() -> {
                    safeCache.service(num);
                });
            }
        } finally {
            executor1.shutdown();
        }
    }

    private static void testSafeCacheWithLocks() {
        SafeCachedLockFactorizer safeCache = new SafeCachedLockFactorizer();
        final ExecutorService executor = Executors.newFixedThreadPool(POOL_SIZE);
        try {

            for (int i = 1; i < 10000; i++) {
                final int num = i % 5; // Alterna entre alguns números para aumentar a chance de colisão
                executor.submit(() -> {
                    safeCache.service(num);
                });
            }
        } finally {
            executor.shutdown();
        }
    }

    private static void testSafeCacheWithHashMap() {
        SafeCacheWithHashMap safeCache = new SafeCacheWithHashMap();
        final ExecutorService executor = Executors.newFixedThreadPool(POOL_SIZE);
        try {

            for (int i = 1; i < 10000; i++) {
                final int num = i % 5; // Alterna entre alguns números para aumentar a chance de colisão
                executor.submit(() -> {
                    safeCache.service(num);
                });
            }
        } finally {
            executor.shutdown();
        }
    }

}