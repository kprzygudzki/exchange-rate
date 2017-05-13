package pl.com.bottega.exchangerate.infrastructure;

import org.springframework.context.annotation.Bean;
import pl.com.bottega.exchangerate.api.ExchangeManager;
import pl.com.bottega.exchangerate.api.implementation.StandardExchangeManager;
import pl.com.bottega.exchangerate.domain.ExchangeRepository;

@org.springframework.context.annotation.Configuration
public class Configuration {

	@Bean
	ExchangeManager exchangeManager(ExchangeRepository repository) {
		return new StandardExchangeManager(repository);
	}

	@Bean
	ExchangeRepository exchangeRepository() {
		return new JPAExchangeRepository();
	}

}
