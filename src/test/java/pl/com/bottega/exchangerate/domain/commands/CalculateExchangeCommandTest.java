package pl.com.bottega.exchangerate.domain.commands;

import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static pl.com.bottega.exchangerate.domain.commands.Validatable.*;
import static pl.com.bottega.exchangerate.domain.commands.Validatable.ValidationErrors;

public class CalculateExchangeCommandTest {

	private CalculateExchangeCommand command;
	private ValidationErrors errors;

	@Before
	public void setUp() throws Exception {
		command = new CalculateExchangeCommand();
		command.setAmount(BigDecimal.valueOf(5));
		command.setDate(LocalDate.now());
		command.setFrom("ABC");
		command.setTo("XYZ");
		errors = new ValidationErrors();
	}

	@Test
	public void shouldValidateCorrectCommand() {
		command.validate(errors);
		assertThat(errors.getErrors()).isEmpty();
	}

	@Test
	public void shouldInvalidateNullDate() {
		command.setDate(null);
		command.validate(errors);
		assertThat(errors.getErrors().keySet()).contains("date");
		assertThat(errors.getErrors().get("date")).contains(IS_REQUIRED);
	}

	@Test
	public void shouldInvalidateNullFrom() {
		command.setFrom(null);
		command.validate(errors);
		assertThat(errors.getErrors().keySet()).contains("from");
		assertThat(errors.getErrors().get("from")).contains(IS_REQUIRED);
	}

	@Test
	public void shouldInvalidateEmptyFrom() {
		command.setFrom("   ");
		command.validate(errors);
		assertThat(errors.getErrors().keySet()).contains("from");
		assertThat(errors.getErrors().get("from")).contains(IS_REQUIRED);
	}

	@Test
	public void shouldInvalidateNullTo() {
		command.setTo(null);
		command.validate(errors);
		assertThat(errors.getErrors().keySet()).contains("to");
		assertThat(errors.getErrors().get("to")).contains(IS_REQUIRED);
	}

	@Test
	public void shouldInvalidateEmptyTo() {
		command.setTo("   ");
		command.validate(errors);
		assertThat(errors.getErrors().keySet()).contains("to");
		assertThat(errors.getErrors().get("to")).contains(IS_REQUIRED);
	}

	@Test
	public void shouldInvalidateRepeatedCurrency() {
		String currency = "ABC";
		command.setFrom(currency);
		command.setTo(currency);
		command.validate(errors);
		assertThat(errors.getErrors().keySet()).contains("from").contains("to");
		assertThat(errors.getErrors().get("from")).contains("must be different than to");
		assertThat(errors.getErrors().get("to")).contains("must be different than from");
	}

	@Test
	public void shouldInvalidateNullAmount() {
		command.setAmount(null);
		command.validate(errors);
		assertThat(errors.getErrors().keySet()).contains("amount");
		assertThat(errors.getErrors().get("amount")).contains(IS_REQUIRED);
	}

	@Test
	public void shouldInvalidateZeroAmount() {
		command.setAmount(BigDecimal.ZERO);
		command.validate(errors);
		assertThat(errors.getErrors().keySet()).contains("amount");
		assertThat(errors.getErrors().get("amount")).contains(GREATER_THAN_ZERO_REQUIRED);
	}

	@Test
	public void shouldInvalidateNegativeAmount() {
		command.setAmount(BigDecimal.TEN.negate());
		command.validate(errors);
		assertThat(errors.getErrors().keySet()).contains("amount");
		assertThat(errors.getErrors().get("amount")).contains(GREATER_THAN_ZERO_REQUIRED);
	}

}