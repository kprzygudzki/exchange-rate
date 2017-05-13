package pl.com.bottega.exchangerate.domain.commands;

import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static pl.com.bottega.exchangerate.domain.commands.Validatable.*;

public class CreateExchangeRateCommandTest {

	private CreateExchangeRateCommand command;
	private ValidationErrors errors;

	@Before
	public void setUp() throws Exception {
		command = new CreateExchangeRateCommand();
		command.setCurrency("ABC");
		command.setDate(LocalDate.now());
		command.setRate(BigDecimal.TEN);
		errors = new ValidationErrors();
	}

	@Test
	public void shouldValidateCorrectCommand() {
		command.validate(errors);
		assertThat(errors.isValid()).isTrue();
		assertThat(errors.getErrors()).isEmpty();
	}

	@Test
	public void shouldInvalidateNullCurrency() {
		command.setCurrency(null);
		command.validate(errors);
		assertThat(errors.isValid()).isFalse();
		assertThat(errors.getErrors().keySet()).contains("currency");
		assertThat(errors.getErrors().get("currency")).contains(IS_REQUIRED);
	}

	@Test
	public void shouldInvalidateEmptyCurrency() {
		command.setCurrency("   ");
		command.validate(errors);
		assertThat(errors.isValid()).isFalse();
		assertThat(errors.getErrors().keySet()).contains("currency");
		assertThat(errors.getErrors().get("currency")).contains(IS_REQUIRED);
	}

	@Test
	public void shouldInvalidateFromThatIsNotThreeCharsLong() {
		command.setCurrency("ABCDE");
		command.validate(errors);
		assertThat(errors.isValid()).isFalse();
		assertThat(errors.getErrors().keySet()).contains("currency");
		assertThat(errors.getErrors().get("currency")).contains("has invalid format");
	}

	@Test
	public void shouldInvalidateNullDate() {
		command.setDate(null);
		command.validate(errors);
		assertThat(errors.isValid()).isFalse();
		assertThat(errors.getErrors().keySet()).contains("date");
		assertThat(errors.getErrors().get("date")).contains(IS_REQUIRED);
	}

	@Test
	public void shouldInvalidateNullRate() {
		command.setRate(null);
		command.validate(errors);
		assertThat(errors.isValid()).isFalse();
		assertThat(errors.getErrors().keySet()).contains("rate");
		assertThat(errors.getErrors().get("rate")).contains(IS_REQUIRED);
	}

	@Test
	public void shouldInvalidateZeroRate() {
		command.setRate(BigDecimal.ZERO);
		command.validate(errors);
		assertThat(errors.isValid()).isFalse();
		assertThat(errors.getErrors().keySet()).contains("rate");
		assertThat(errors.getErrors().get("rate")).contains(GREATER_THAN_ZERO_REQUIRED);
	}

	@Test
	public void shouldInvalidateNegativeRate() {
		command.setRate(BigDecimal.TEN.negate());
		command.validate(errors);
		assertThat(errors.isValid()).isFalse();
		assertThat(errors.getErrors().keySet()).contains("rate");
		assertThat(errors.getErrors().get("rate")).contains(GREATER_THAN_ZERO_REQUIRED);
	}

}