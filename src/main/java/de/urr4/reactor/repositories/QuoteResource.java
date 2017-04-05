package de.urr4.reactor.repositories;

import de.urr4.reactor.entities.Quote;

/**
 * Wrapper for {@link Quote} to be able to fetch it using {@link org.springframework.web.client.RestTemplate}
 * Created by sschubert on 05.04.2017.
 */
public class QuoteResource {

    private String type;
    private Quote value;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Quote getValue() {
        return value;
    }

    public void setValue(Quote value) {
        this.value = value;
    }
}
