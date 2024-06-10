package com.morbey.examples;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicInteger;

public class AtomicCounter {

    private static final AtomicInteger counter = new AtomicInteger();

    public static void main(String[] args) {

        class Incrementer implements Runnable {

            @Override
            public void run() {
                for (int i = 0; i < 1000; i++) {
                    counter.getAndIncrement();
                }
            }
        }

        class Decrementer implements Runnable {
            @Override
            public void run() {
                for (int i = 0; i < 1000; i++) {
                    counter.getAndDecrement();
                }
            }
        }

        ExecutorService executorService = Executors.newFixedThreadPool(8);
        List<Future<?>> futures = new ArrayList<>();

        try {
            for (int i = 0; i < 4; i++) {
                futures.add(executorService.submit(new Incrementer()));
            }
            for (int i = 0; i < 4; i++) {
                futures.add(executorService.submit(new Decrementer()));
            }

            futures.forEach(
                    future -> {
                        try {
                            future.get();
                        } catch (InterruptedException | ExecutionException e) {
                            System.out.println(e.getMessage());
                        }
                    }
            );

            System.out.println("counter = " + counter);
        }
        finally {
            executorService.shutdown();
        }
    }

}
