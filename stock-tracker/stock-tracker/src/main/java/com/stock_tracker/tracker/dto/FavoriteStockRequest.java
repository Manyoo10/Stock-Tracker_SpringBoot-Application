package com.stock_tracker.tracker.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class FavoriteStockRequest {
    private String symbol;
}
