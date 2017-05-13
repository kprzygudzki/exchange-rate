package pl.com.bottega.exchangerate.api;

import pl.com.bottega.exchangerate.model.ExchangeExporter;

import java.math.BigDecimal;
import java.time.LocalDate;

public class ExchangeDtoBuilder implements ExchangeExporter {

	private ExchangeDto dto = new ExchangeDto();

	public ExchangeDto build() {
		ExchangeDto result = dto;
		dto = new ExchangeDto();
		return result;
	}

	@Override
	public void addFrom(String from) {
		dto.setFrom(from);
	}

	@Override
	public void addTo(String to) {
		dto.setTo(to);
	}

	@Override
	public void addAmount(BigDecimal amount) {
		dto.setAmount(amount);
	}

	@Override
	public void addCalculatedAmount(BigDecimal calculatedAmount) {
		dto.setCalculatedAmount(calculatedAmount);
	}

	@Override
	public void addDate(LocalDate date) {
		dto.setDate(date);
	}

}
