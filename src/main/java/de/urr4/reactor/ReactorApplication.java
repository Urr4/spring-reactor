package de.urr4.reactor;

import de.urr4.reactor.publisher.Publisher;
import de.urr4.reactor.receiver.Receiver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import reactor.Environment;
import reactor.bus.EventBus;
import reactor.bus.selector.Selectors;
import sun.awt.AppContext;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

@SpringBootApplication
public class ReactorApplication implements CommandLineRunner{

	private static final int NUMBER_OF_QUOTES = 10;

	@Bean
	Environment environment(){
		return Environment.initializeIfEmpty()
				 		  .assignErrorJournal();
	}

	@Bean
	EventBus eventBus(Environment environment){
		return EventBus.create(environment, Environment.THREAD_POOL);
	}

	@Bean
	CountDownLatch countDownLatch(){
		return new CountDownLatch(NUMBER_OF_QUOTES);
	}

	@Autowired
	private EventBus eventBus;

	@Autowired
	private Publisher publisher;

	@Autowired
	private Receiver receiver;

	public static void main(String[] args) throws InterruptedException {
		ConfigurableApplicationContext app = SpringApplication.run(ReactorApplication.class, args);

		/**
		 * Wait unti {@link CountDownLatch} has reached 0 or for 1 second
		 */
		app.getBean(CountDownLatch.class).await(1, TimeUnit.SECONDS);

		/**
		 * Waits until everything is done and shuts down
		 */
		app.getBean(Environment.class).shutdown();
	}

	@Override
	public void run(String... strings) throws Exception {

		/**
		 * Sets the {@link Receiver} as a subscriber for "quote"-Events
		 */
		eventBus.on(Selectors.$("quotes"), receiver);

		/**
		 * Command the publisher to publish 10 Quotes
		 */
		publisher.publishQuotes(NUMBER_OF_QUOTES);
	}
}
