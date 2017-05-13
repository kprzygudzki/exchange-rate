package pl.com.bottega.exchangerate.domain.commands;

import java.math.BigDecimal;
import java.time.LocalDate;

public class CreateExchangeRateCommand implements Validatable {

	public static final String INVALID_FORMAT = "has invalid format";

	private LocalDate date;
	private String currency;
	private BigDecimal rate;

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public BigDecimal getRate() {
		return rate;
	}

	public void setRate(BigDecimal rate) {
		this.rate = rate;
	}

	@Override
	public void validate(ValidationErrors errors) {
		validateCurrency(errors);
		validateDate(errors);
		validateRate(errors);
	}

	private void validateCurrency(ValidationErrors errors) {
		if (isEmpty(currency))
			errors.add("currency", IS_REQUIRED);
		else if (currency.length() != 3)
			errors.add("currency", INVALID_FORMAT);
	}

	private void validateDate(ValidationErrors errors) {
		if (date == null)
			errors.add("date", IS_REQUIRED);
	}

	private void validateRate(ValidationErrors errors) {
		if (rate == null)
			errors.add("rate", IS_REQUIRED);
		else if (!(rate.compareTo(BigDecimal.ZERO) > 0))
			errors.add("rate", GREATER_THAN_ZERO_REQUIRED);
	}

}
