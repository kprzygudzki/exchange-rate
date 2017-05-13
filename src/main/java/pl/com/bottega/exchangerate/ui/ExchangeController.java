package pl.com.bottega.exchangerate.ui;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import pl.com.bottega.exchangerate.api.ExchangeDto;
import pl.com.bottega.exchangerate.api.ExchangeManager;
import pl.com.bottega.exchangerate.domain.commands.CreateExchangeRateCommand;
import pl.com.bottega.exchangerate.domain.commands.CalculateExchangeCommand;

@RestController
public class ExchangeController {

	private ExchangeManager exchangeManager;

	public ExchangeController(ExchangeManager exchangeManager) {
		this.exchangeManager = exchangeManager;
	}

	@PutMapping("/exchange-rate")
	void addExchangeRate(@RequestBody CreateExchangeRateCommand command) {
		exchangeManager.addExchangeRate(command);
	}

	@GetMapping("/calculation")
	ExchangeDto calculateExchange(CalculateExchangeCommand query) {
		return exchangeManager.calculateExchange(query);
	}

}
