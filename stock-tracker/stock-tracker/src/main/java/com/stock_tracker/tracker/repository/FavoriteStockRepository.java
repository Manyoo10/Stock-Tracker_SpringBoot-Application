package com.stock_tracker.tracker.repository;


import com.stock_tracker.tracker.entity.FavoriteStock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FavoriteStockRepository  extends JpaRepository<FavoriteStock, Long> {

    boolean existsBySymbol(String symbol);

}
