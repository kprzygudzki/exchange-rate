package pl.com.bottega.exchangerate.domain;

import pl.com.bottega.exchangerate.domain.commands.CreateExchangeRateCommand;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Entity
public class ExchangeRate {

	private static final String DEFAULT_CURRENCY = "PLN";
	private static final ExchangeRate DEFAULT =
			new ExchangeRate(null, DEFAULT_CURRENCY, BigDecimal.ONE);

	@Id
	@GeneratedValue
	private UUID id;

	private LocalDate date;
	private String currency;
	private BigDecimal rate;

	ExchangeRate(LocalDate date, String currency, BigDecimal rate) {
		this.date = date;
		this.currency = currency;
		this.rate = rate;
	}

	private ExchangeRate() {
	}

	public static ExchangeRate from(CreateExchangeRateCommand command) {
		LocalDate date = command.getDate();
		String currencyFrom = command.getCurrency();
		BigDecimal rate = command.getRate();
		return new ExchangeRate(date, currencyFrom, rate);
	}

	public void change(CreateExchangeRateCommand command) {
		date = command.getDate();
		currency = command.getCurrency();
		rate = command.getRate();
	}

	String getCurrency() {
		return currency;
	}

	LocalDate getDate() {
		return date;
	}

	BigDecimal getRate() {
		return rate;
	}

	public static String getDefaultCurrency() {
		return DEFAULT_CURRENCY;
	}

	public static ExchangeRate getDefault() {
		return DEFAULT;
	}
}