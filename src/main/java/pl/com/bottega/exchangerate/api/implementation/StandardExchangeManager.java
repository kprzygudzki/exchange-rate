package pl.com.bottega.exchangerate.api.implementation;

import pl.com.bottega.exchangerate.api.ExchangeDto;
import pl.com.bottega.exchangerate.api.ExchangeDtoBuilder;
import pl.com.bottega.exchangerate.api.ExchangeManager;
import pl.com.bottega.exchangerate.domain.Exchange;
import pl.com.bottega.exchangerate.domain.ExchangeRate;
import pl.com.bottega.exchangerate.domain.ExchangeRepository;
import pl.com.bottega.exchangerate.domain.commands.CreateExchangeRateCommand;
import pl.com.bottega.exchangerate.domain.commands.CalculateExchangeCommand;

import javax.transaction.Transactional;
import java.time.LocalDate;

@Transactional
public class StandardExchangeManager implements ExchangeManager {

	private ExchangeRepository repository;

	public StandardExchangeManager(ExchangeRepository repository) {
		this.repository = repository;
	}

	@Override
	public void addExchangeRate(CreateExchangeRateCommand command) {
		repository.removeFor(command.getCurrency(), command.getDate());
		ExchangeRate rate = ExchangeRate.from(command);
		repository.put(rate);
	}

	@Override
	public ExchangeDto calculateExchange(CalculateExchangeCommand command) {
		ExchangeRate from = getExchangeRate(command.getFrom(), command.getDate());
		ExchangeRate to = getExchangeRate(command.getTo(), command.getDate());
		Exchange exchange = Exchange.of(from, to, command.getAmount(), command.getDate());
		return createExchangeDto(exchange);
	}

	private ExchangeRate getExchangeRate(String currency, LocalDate date) {
		ExchangeRate fromRate = ExchangeRate.getDefault();
		if (!currency.equals(ExchangeRate.getDefaultCurrency()))
			fromRate = repository.getFor(currency, date);
		return fromRate;
	}

	private ExchangeDto createExchangeDto(Exchange exchange) {
		ExchangeDtoBuilder builder = new ExchangeDtoBuilder();
		exchange.export(builder);
		return builder.build();
	}

}
