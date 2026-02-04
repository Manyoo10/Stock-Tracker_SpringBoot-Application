package com.stock_tracker.tracker.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.boot.Metadata;

import java.util.Map;

public record StockHistoryResponse(
        @JsonProperty("Meta Data") Metadata metaData,
        @JsonProperty("Time Series (Daily)")Map<String, DailyPrice> timeSeries
        ) {
    public record MetaData(
            @JsonProperty("2. Symbol") String symbol
    ){}

    public record DailyPrice(
            @JsonProperty("1. open") String open,
            @JsonProperty("2. high") String high,
            @JsonProperty("3. low") String low,
            @JsonProperty("4. class") String close,
            @JsonProperty("5. volume") String volume
    ){}
}


