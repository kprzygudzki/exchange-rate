package pl.com.bottega.exchangerate.infrastructure;

import org.springframework.context.annotation.Bean;
import pl.com.bottega.exchangerate.api.ExchangeManager;
import pl.com.bottega.exchangerate.api.implementation.StandardExchangeManager;
import pl.com.bottega.exchangerate.domain.ExchangeRateRepository;

@org.springframework.context.annotation.Configuration
public class Configuration {

	@Bean
	ExchangeManager exchangeManager(ExchangeRateRepository repository) {
		return new StandardExchangeManager(repository);
	}

	@Bean
	ExchangeRateRepository exchangeRepository() {
		return new JPAExchangeRepository();
	}

}
