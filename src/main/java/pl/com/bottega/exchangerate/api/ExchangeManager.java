package pl.com.bottega.exchangerate.api;

import pl.com.bottega.exchangerate.domain.commands.CalculateExchangeCommand;
import pl.com.bottega.exchangerate.domain.commands.CreateExchangeRateCommand;

public interface ExchangeManager {

	void addExchangeRate(CreateExchangeRateCommand command);

	ExchangeDto calculateExchange(CalculateExchangeCommand command);

}
