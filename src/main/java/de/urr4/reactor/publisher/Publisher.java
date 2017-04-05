package de.urr4.reactor.publisher;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.bus.Event;
import reactor.bus.EventBus;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by sschubert on 05.04.2017.
 */
@Service
public class Publisher {

    /**
     * Autowired den EventBus, damit Events dorthin gepusht werden können.
     */
    @Autowired
    private EventBus eventBus;

    @Autowired
    private CountDownLatch latch;

    /**
     * Publisher Method, needs to be called somewhere
      * @param numberOfQuotes
     * @throws InterruptedException
     */
    public void publishQuotes(int numberOfQuotes) throws InterruptedException {
        long start = System.currentTimeMillis();

        AtomicInteger counter = new AtomicInteger(1);

        /**
         * Push 10 Quotes to the EventBus, to be read by "quotes"-Subscriber
         */
        for (int i = 0; i<numberOfQuotes; i++){
            eventBus.notify("quotes", Event.wrap(counter.getAndIncrement()));
        }

        /**
         * Sleep until the latch has reached 0
         */
        latch.await();

        long elapsed =System.currentTimeMillis()-start;

        System.out.println("Elapsed time: "+elapsed+"ms");
        System.out.println("Average time per quote: "+elapsed/numberOfQuotes+"ms");
    }
}
