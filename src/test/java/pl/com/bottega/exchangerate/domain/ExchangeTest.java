package pl.com.bottega.exchangerate.domain;

import org.junit.Before;
import org.junit.Test;
import pl.com.bottega.exchangerate.api.ExchangeDto;
import pl.com.bottega.exchangerate.api.ExchangeDtoBuilder;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

public class ExchangeTest {

	@Before
	public void setUp() throws Exception {

	}

	@Test
	public void shouldCalculateAmount() {
		String fromCurrency = "ABC";
		LocalDate now = LocalDate.now();
		ExchangeRate fromRate =
				new ExchangeRate(now, fromCurrency, BigDecimal.valueOf(25).scaleByPowerOfTen(-1));
		String toCurrency = "XYZ";
		ExchangeRate toRate =
				new ExchangeRate(now, toCurrency, BigDecimal.ONE);
		BigDecimal amount = BigDecimal.valueOf(5);
		Exchange exchange = Exchange.of(fromRate, toRate, amount, now);
		BigDecimal expected = BigDecimal.valueOf(1250).scaleByPowerOfTen(-2);
		ExchangeDto exchangeDto = createExchangeDto(exchange);
		assertEqualExchangeDtos(exchangeDto, fromCurrency, now, toCurrency, amount, expected);
	}

	@Test
	public void shouldAssignDateFromFrom() {

	}

	private void assertEqualExchangeDtos(ExchangeDto exchangeDto, String fromCurrency, LocalDate now, String toCurrency,
										 BigDecimal amount, BigDecimal expected) {
		assertThat(exchangeDto.getFrom()).isEqualTo(fromCurrency);
		assertThat(exchangeDto.getTo()).isEqualTo(toCurrency);
		assertThat(exchangeDto.getAmount()).isEqualTo(amount);
		assertThat(exchangeDto.getCalculatedAmount()).isEqualTo(expected);
		assertThat(exchangeDto.getDate()).isEqualTo(now);
	}

	private ExchangeDto createExchangeDto(Exchange exchange) {
		ExchangeDtoBuilder builder = new ExchangeDtoBuilder();
		exchange.export(builder);
		return builder.build();
	}

}