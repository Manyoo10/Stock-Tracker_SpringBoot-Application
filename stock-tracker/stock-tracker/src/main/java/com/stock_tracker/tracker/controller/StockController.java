package com.stock_tracker.tracker.controller;


import com.stock_tracker.tracker.dto.DailyStockResponse;
import com.stock_tracker.tracker.dto.FavoriteStockRequest;
import com.stock_tracker.tracker.dto.StockOverviewResponse;
import com.stock_tracker.tracker.dto.StockResponse;
import com.stock_tracker.tracker.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/stocks")
public class StockController {

    private final StockService stockService;

    @Autowired
    public StockController(final StockService stockService) {
        this.stockService = stockService;
    }

    @GetMapping("/{stockSymbol}")
    public StockResponse getStock(@PathVariable String stockSymbol) {
        return stockService.getStockForSymbol(stockSymbol.toUpperCase());
    }

    @GetMapping("/{stockSymbol}/overview")
    public StockOverviewResponse getStockOverView(@PathVariable String stockSymbol) {
        return stockService.getStockOverviewForSymbol(stockSymbol.toUpperCase());
    }

    @GetMapping("/{symbol}/history")
    public List<DailyStockResponse> getStockHistory(
            @PathVariable String symbol,
            @RequestParam(defaultValue = "30") int days
    ){
        return stockService.getHistory(symbol.toUpperCase(), days);
    }

    @PostMapping("/favorites")
    public ResponseEntity<FavoriteStock> saveFavoriteStock(@RequestBody FavoriteStockRequest request) {
        final FavoriteStock saved = stockService.addFavorite(request.getSymbol());
        return ResponseEntity.ok(saved);
    }


}
