package com.stock_tracker.tracker.service;


import com.stock_tracker.tracker.client.StockClient;
import com.stock_tracker.tracker.dto.*;
import com.stock_tracker.tracker.entity.FavoriteStock;
import com.stock_tracker.tracker.exception.FavoriteAlreadyExistsException;
import com.stock_tracker.tracker.repository.FavoriteStockRepository;
import jakarta.transaction.Transactional;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class StockService {

    private final StockClient stockClient;
    private final FavoriteStockRepository favoritesRepository;

    public StockService(final StockClient stockClient,
                        final FavoriteStockRepository favoritesRepository) {
        this.stockClient = stockClient;
        this.favoritesRepository = favoritesRepository;
    }


    @Cacheable(value = "stocks", key = "#stockSymbol")
    public StockResponse getStockForSymbol(final String stockSymbol) {
        final AlphaVantageResponse response = stockClient.getStockQuote(stockSymbol);
        return StockResponse.builder()
                .symbol(response.globalQuote().symbol())
                .price(Double.parseDouble(response.globalQuote().price()))
                .lastUpdated(response.globalQuote().lastTradingDay())
                .build();
    }

    public StockOverviewResponse getStockOverviewForSymbol(final String symbol) {
        return stockClient.getStockOverview(symbol);
    }

    public List<DailyStockResponse> getHistory (String symbol, int days) {
        StockHistoryResponse response = stockClient.getStockHistory(symbol);

        return response.timeSeries().entrySet().stream()
                .limit(days)
                .map(entry -> {
                    var date = entry.getKey();
                    var daily = entry.getValue();
                    return  new DailyStockResponse(
                            date,
                            Double.parseDouble(daily.open()),
                            Double.parseDouble(daily.close()),
                            Double.parseDouble(daily.high()),
                            Double.parseDouble(daily.low()),
                            Long.parseLong(daily.volume())
                    );
                } )
                .collect(Collectors.toList());
    }

    @Transactional
    public FavoriteStock addFavorite(final String symbol) {
        if(favoritesRepository.existsBySymbol(symbol)){
            throw new FavoriteAlreadyExistsException(symbol);
        }

        FavoriteStock favorite = FavoriteStock.builder()
                .symbol(symbol)
                .build();

        return favoritesRepository.save(favorite);
    }

    public List<StockResponse> getFavoritesWithLivePrices() {
        List<FavoriteStock> favorites = favoritesRepository.findAll();

        return favorites.stream()
                .map(fav -> getStockForSymbol(fav.getSymbol()))
                .collect(Collectors.toList());
    }

}

