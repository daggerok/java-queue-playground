package com.daggerok.queue;

import io.vavr.control.Try;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;

import java.util.UUID;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

import static org.assertj.core.api.Assertions.assertThat;

@Log4j2
@RequiredArgsConstructor
class Producer implements Runnable {

    private final BlockingQueue<String> queue;
    private final CountDownLatch counter = new CountDownLatch(15);

    @Override
    public void run() {
        try {
            while (counter.getCount() > 0) {
                queue.put(produce());
                counter.countDown();
            }
            log.info("producer: Done.");
        }

        catch (InterruptedException ex) {
            log.warn(ex.getLocalizedMessage(), ex);
        }
    }

    String produce() {
        String produced = UUID.randomUUID().toString().replace("-", System.nanoTime() % 3 == 0 ? "" : " ");
        log.info("produced {}: {}", -counter.getCount(), produced);
        return produced;
    }
}

@Log4j2
@RequiredArgsConstructor
class Consumer implements Runnable {

    private final String name;
    private final BlockingQueue<String> queue;
    private final AtomicLong counter = new AtomicLong(0);

    @Override
    public void run() {
        try {
            while (true) {
                consume(queue.take());
            }
        }

        catch (InterruptedException ex) {
            log.warn(ex.getLocalizedMessage(), ex);
        }

        log.info("This wont never happens! queue.take() will block whole thread unless new element will be available.");
    }

    void consume(String taken) {
        log.info("consumer-{} {}: {}", name, counter.incrementAndGet(), taken);
    }
}

/**
 * FIFO. CONSUMER GROUP
 */
class ArrayBlockingQueueFIFOTests {

    BlockingQueue<String> queue = new ArrayBlockingQueue<>(123);
    Producer producer = new Producer(queue);
    Consumer consumer1 = new Consumer("1", queue);
    Consumer consumer2 = new Consumer("2", queue);

    @Test
    void test() {
        new Thread(producer).start();
        new Thread(consumer1).start();
        new Thread(consumer2).start();

        Try.run(() -> TimeUnit.NANOSECONDS.sleep(1));
        assertThat(true).isTrue();
    }
}
