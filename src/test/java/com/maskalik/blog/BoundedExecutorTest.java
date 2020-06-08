package com.maskalik.blog;

import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Slf4j
public class BoundedExecutorTest {
    private BoundedExecutor sut;

    @Before
    public void setUp() throws Exception {
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(10, 50, 30L, TimeUnit.SECONDS, new LinkedBlockingDeque<Runnable>());
        sut = new BoundedExecutor(20, threadPoolExecutor);
    }

    @Test
    public void testBoundedExecutor() throws InterruptedException {
        for (int i = 0; i < 30; i++) {
            Runnable task = createTestTask();
            sut.submitTask(task);
        }

        Thread.sleep(1000);
    }

    private Runnable createTestTask() {
        return new Runnable() {
            @Override
            public void run() {
                try {
                    log.info("hello");
                    Thread.sleep(200);
                } catch (InterruptedException ignored) {
                }
            }
        };
    }
}
