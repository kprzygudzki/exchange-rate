package pl.com.bottega.exchangerate.domain;

import org.junit.Before;
import org.junit.Test;
import pl.com.bottega.exchangerate.api.ExchangeDto;
import pl.com.bottega.exchangerate.api.ExchangeDtoBuilder;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

public class ExchangeTest {

	private ExchangeRateBuilder fromExchangeRateBuilder;
	private ExchangeRateBuilder toExchangeRateBuilder;
	private BigDecimal amount;

	@Before
	public void setUp() throws Exception {
		fromExchangeRateBuilder = ExchangeRateBuilder.get()
				.setCurrency("ABC")
				.setDate(LocalDate.now())
				.setRate(BigDecimal.valueOf(4));
		toExchangeRateBuilder = ExchangeRateBuilder.get()
				.setCurrency("XYZ")
				.setDate(LocalDate.now())
				.setRate(BigDecimal.valueOf(5));
		amount = BigDecimal.TEN;
	}

	@Test
	public void shouldCalculateAmount() {
		//given
		ExchangeRate fromExchangeRate = fromExchangeRateBuilder.build();
		ExchangeRate toExchangeRate = toExchangeRateBuilder.build();
		//when
		Exchange exchange = Exchange.of(fromExchangeRate, toExchangeRate, amount);
		//then
		ExchangeDto exchangeDto = createExchangeDto(exchange);
		assertThat(exchangeDto.getCalculatedAmount()).isEqualByComparingTo(BigDecimal.valueOf(8));
	}

	@Test
	public void shouldCalculateAmountWithDefaultFrom() {
		//given
		ExchangeRate toExchangeRate = toExchangeRateBuilder.build();
		//when
		Exchange exchange = Exchange.of(ExchangeRate.getDefault(), toExchangeRate, amount);
		//then
		ExchangeDto exchangeDto = createExchangeDto(exchange);
		assertThat(exchangeDto.getCalculatedAmount()).isEqualByComparingTo(BigDecimal.valueOf(2));
	}

	@Test
	public void shouldAssignDateIfToDateIsNull() {
		//given
		LocalDate date = LocalDate.parse("2017-07-01");
		ExchangeRate fromExchangeRate = fromExchangeRateBuilder.setDate(date).build();
		ExchangeRate toExchangeRate = toExchangeRateBuilder.setDate(null).build();
		//when
		Exchange exchange = Exchange.of(fromExchangeRate, toExchangeRate, amount);
		//then
		ExchangeDto exchangeDto = createExchangeDto(exchange);
		assertThat(exchangeDto.getDate()).isEqualTo(date);
	}

	@Test
	public void shouldAssignDateIfFromDateIsNull() {
		//given
		LocalDate date = LocalDate.parse("2017-07-01");
		ExchangeRate fromExchangeRate = fromExchangeRateBuilder.setDate(null).build();
		ExchangeRate toExchangeRate = toExchangeRateBuilder.setDate(date).build();
		//when
		Exchange exchange = Exchange.of(fromExchangeRate, toExchangeRate, amount);
		//then
		ExchangeDto exchangeDto = createExchangeDto(exchange);
		assertThat(exchangeDto.getDate()).isEqualTo(date);
	}

	@Test
	public void shouldAssignNullDateIfBothDatesAreNull() {
		//given
		ExchangeRate fromExchangeRate = ExchangeRate.getDefault();
		ExchangeRate toExchangeRate = ExchangeRate.getDefault();
		//when
		Exchange exchange = Exchange.of(fromExchangeRate, toExchangeRate, BigDecimal.valueOf(5));
		//then
		ExchangeDto exchangeDto = createExchangeDto(exchange);
		assertThat(exchangeDto.getDate()).isNull();
	}

	private ExchangeDto createExchangeDto(Exchange exchange) {
		ExchangeDtoBuilder builder = new ExchangeDtoBuilder();
		exchange.export(builder);
		return builder.build();
	}

	private static class ExchangeRateBuilder {

		private String currency;
		private LocalDate date;
		private BigDecimal rate;

		private static ExchangeRateBuilder get() {
			return new ExchangeRateBuilder();
		}

		private ExchangeRate build() {
			return new ExchangeRate(date, currency, rate);
		}

		private ExchangeRateBuilder setCurrency(String currency) {
			this.currency = currency;
			return this;
		}

		private ExchangeRateBuilder setDate(LocalDate date) {
			this.date = date;
			return this;
		}

		private ExchangeRateBuilder setRate(BigDecimal rate) {
			this.rate = rate;
			return this;
		}

	}

}