package pl.com.bottega.exchangerate.model;

import java.math.BigDecimal;
import java.time.LocalDate;

public interface ExchangeExporter {

	void addFrom(String from);

	void addTo(String to);

	void addAmount(BigDecimal amount);

	void addCalculatedAmount(BigDecimal calculatedAmount);

	void addDate(LocalDate date);

}
