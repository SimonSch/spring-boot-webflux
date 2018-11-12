package com.schroffner.LiveUpdates.repositories;

import com.schroffner.LiveUpdates.entities.Stock;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.data.mongodb.repository.Tailable;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface StockRepository extends ReactiveMongoRepository<Stock, String> {

    @Tailable
    Flux<Stock> findWithTailableCursorBy();

    Mono<Stock> findStockByName(String name);

}
