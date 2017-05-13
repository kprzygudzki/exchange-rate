package pl.com.bottega.exchangerate.domain;

import pl.com.bottega.exchangerate.model.ExchangeExporter;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;

public class Exchange {

	private String from;
	private String to;
	private BigDecimal amount;
	private BigDecimal calculatedAmount;
	private LocalDate date;

	private Exchange(String from, String to, BigDecimal amount, BigDecimal calculatedAmount, LocalDate date) {
		this.from = from;
		this.to = to;
		this.amount = amount;
		this.calculatedAmount = calculatedAmount;
		this.date = date;
	}

	public static Exchange of(ExchangeRate from, ExchangeRate to, BigDecimal amount) {
		String fromCurrency = from.getCurrency();
		String toCurrency = to.getCurrency();
		LocalDate localDate = assignDate(from, to);
		BigDecimal calculatedAmount = calculateAmount(from, to, amount);
		return new Exchange(fromCurrency, toCurrency, amount, calculatedAmount, localDate);
	}

	private static LocalDate assignDate(ExchangeRate from, ExchangeRate to) {
		return (from.getDate() != null) ? from.getDate() : to.getDate();
	}

	private static BigDecimal calculateAmount(ExchangeRate from, ExchangeRate to, BigDecimal amount) {
		return amount.multiply(from.getRate()).divide(to.getRate(), 2, RoundingMode.FLOOR);
	}

	public void export(ExchangeExporter exporter) {
		exporter.addFrom(from);
		exporter.addTo(to);
		exporter.addAmount(amount);
		exporter.addCalculatedAmount(calculatedAmount);
		exporter.addDate(date);
	}

}
