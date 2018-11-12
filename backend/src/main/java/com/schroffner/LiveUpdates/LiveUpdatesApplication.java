package com.schroffner.LiveUpdates;

import com.schroffner.LiveUpdates.entities.Stock;
import com.schroffner.LiveUpdates.repositories.StockRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.data.mongodb.core.CollectionOptions;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;
import reactor.core.publisher.Mono;

@SpringBootApplication
@EnableReactiveMongoRepositories
public class LiveUpdatesApplication {

	public static void main(String[] args) throws InterruptedException {
		ConfigurableApplicationContext application =  SpringApplication.run(LiveUpdatesApplication.class, args);

		final MongoOperations mongoOperations = application.getBean(MongoOperations.class);
		final StockRepository stockRepository = application.getBean(StockRepository.class);

		if(mongoOperations.collectionExists("stock")) {
			mongoOperations.dropCollection("stock");
		}

		// Capped collections need to be created manually
		mongoOperations.createCollection("stock", CollectionOptions.empty().capped().size(9999999L).maxDocuments(100L));

		final Mono<Stock> dowJones = stockRepository.save(new Stock(null, "Dow", 200.00));
		final Mono<Stock> dax = stockRepository.save(new Stock(null, "Dax", 300.00));

		dowJones.subscribe();
		Thread.sleep(200);

		stockRepository.findWithTailableCursorBy().flatMap(stock -> {
			System.out.println(stock.getName());
			return Mono.just(stock);
		}).subscribe();



	}
}
