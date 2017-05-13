package pl.com.bottega.exchangerate.domain.commands;

import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDate;

public class CalculateExchangeCommand implements Validatable {

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate date;
	private String from;
	private String to;
	private BigDecimal amount;

	public CalculateExchangeCommand() {
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	@Override
	public void validate(ValidationErrors errors) {
		validateDate(errors);
		validateFrom(errors);
		validateTo(errors);
		validateAmount(errors);
	}

	private void validateDate(ValidationErrors errors) {
		if (date == null)
			errors.add("date", IS_REQUIRED);
	}

	private void validateFrom(ValidationErrors errors) {
		if (isEmpty(this.from))
			errors.add("from", IS_REQUIRED);
		else if (this.from.equals(this.to))
			errors.add("from", String.format("must be different than %s", "to"));
	}

	private void validateTo(ValidationErrors errors) {
		if (isEmpty(this.to))
			errors.add("to", IS_REQUIRED);
		else if (this.to.equals(this.from))
			errors.add("to", String.format("must be different than %s", "from"));
	}

	private void validateAmount(ValidationErrors errors) {
		if (amount == null)
			errors.add("amount", IS_REQUIRED);
		else if (!(amount.compareTo(BigDecimal.ZERO) > 0))
			errors.add("rate", GREATER_THAN_ZERO_REQUIRED);
	}

}
