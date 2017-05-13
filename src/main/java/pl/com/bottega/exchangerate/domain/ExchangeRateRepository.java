package pl.com.bottega.exchangerate.domain;

import java.time.LocalDate;

public interface ExchangeRateRepository {

	void put(ExchangeRate rate);

	ExchangeRate getFor(String from, LocalDate date);

	void removeFor(String currency, LocalDate date);

}
