package com.stock_tracker.tracker.dto;


import lombok.Builder;

@Builder
public record StockResponse(
        String symbol,
        double price,
        String lastUpdated
) {
}
