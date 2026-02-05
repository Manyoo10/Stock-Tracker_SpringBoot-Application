package com.stock_tracker.tracker.exception;

public class FavoriteAlreadyExistsException extends RuntimeException{

    public FavoriteAlreadyExistsException(String symbol) {
        super("Sybol already saved as favorite" + symbol);
    }
}
