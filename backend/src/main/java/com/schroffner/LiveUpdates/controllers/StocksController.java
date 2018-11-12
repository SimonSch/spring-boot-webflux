package com.schroffner.LiveUpdates.controllers;

import com.schroffner.LiveUpdates.entities.Stock;
import com.schroffner.LiveUpdates.services.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;

@RestController(value = "api/stocks")
@CrossOrigin(value = "*")
public class StocksController {

    private StockService stockService;

    @Autowired
    public StocksController(StockService stockService) {
        this.stockService = stockService;
    }

    @GetMapping
    public Flux<Stock> findAllStocksPaged() {
        return this.stockService.findAllStocksPaged();
    }

    @PostMapping
    public Mono<Stock> save(@RequestBody Stock stock){
        return this.stockService.save(stock);
    }

}
