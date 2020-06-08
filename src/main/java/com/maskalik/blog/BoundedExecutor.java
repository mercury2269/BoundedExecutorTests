package com.maskalik.blog;

import java.util.concurrent.Executor;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.Semaphore;


public class BoundedExecutor {
    private final Semaphore semaphore;
    private final Executor executor;

    public BoundedExecutor(int maxThreads, Executor executor) {
        this.semaphore = new Semaphore(maxThreads);
        this.executor = executor;
    }

    public void submitTask(final Runnable runnable) throws InterruptedException {
        semaphore.acquire();
        try {
            executor.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        runnable.run();
                    } finally {
                        semaphore.release();
                    }
                }
            });
        } catch (RejectedExecutionException ex) {

            semaphore.release();
        }
    }
}
