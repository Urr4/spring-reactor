package de.urr4.reactor.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Quote-Entity.
 * Created by sschubert on 05.04.2017.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Quote {

    private Long id;

    private String quote;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getQuote() {
        return quote;
    }

    public void setQuote(String quote) {
        this.quote = quote;
    }
}
