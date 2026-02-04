package com.stock_tracker.tracker.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record AlphaVantageResponse(
        @JsonProperty("GLOBAL QUOTE")
        GlobalQuote globalQuote
) {
    public record GlobalQuote(
            @JsonProperty("01. symbol") String symbol,
            @JsonProperty("02. price") String price,
            @JsonProperty("03. latest trading day") String lastTradingDay
    ){

    }
}
