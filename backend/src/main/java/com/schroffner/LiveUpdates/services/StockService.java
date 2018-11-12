package com.schroffner.LiveUpdates.services;

import com.schroffner.LiveUpdates.entities.Stock;
import com.schroffner.LiveUpdates.repositories.StockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.repository.Tailable;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class StockService {

    private StockRepository stockRepository;

    @Autowired
    public StockService(StockRepository stockRepository) {
        this.stockRepository = stockRepository;
    }

    @Tailable
    public Flux<Stock> findAllStocksPaged() {
        return this.stockRepository.findWithTailableCursorBy();
    }

    public Mono<Stock> save(Stock stock) {
        return this.stockRepository.save(stock);
    }
}
