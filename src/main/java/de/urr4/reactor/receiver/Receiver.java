package de.urr4.reactor.receiver;

import de.urr4.reactor.entities.Quote;
import de.urr4.reactor.repositories.QuoteResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import reactor.bus.Event;
import reactor.fn.Consumer;

import java.util.concurrent.CountDownLatch;

/**
 * Created by sschubert on 05.04.2017.
 */
@Service
public class Receiver implements Consumer<Event<Integer>>{

    @Autowired
    private CountDownLatch latch;

    /**
     * RestTemplate for fetching Quotes using GET
     */
    RestTemplate restTemplate = new RestTemplate();

    /**
     * Receiver Method, cann be subscribed to the {@link reactor.bus.EventBus}
     * @param integerEvent
     */
    @Override
    public void accept(Event<Integer> integerEvent) {

        /**
         * Reacts to a {@link QuoteResource} by printing it
         */
        QuoteResource quoteResource = restTemplate.getForObject("http://gturnquist-quoters.cfapps.io/api/random", QuoteResource.class);
        System.out.println("Quote "+integerEvent.getData()+": "+ quoteResource.getValue().getQuote());
        latch.countDown();
    }
}
