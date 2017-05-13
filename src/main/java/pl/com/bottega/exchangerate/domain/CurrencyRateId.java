package pl.com.bottega.exchangerate.domain;

import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.UUID;

@Embeddable
public class CurrencyRateId implements Serializable {

	private UUID id;

	private CurrencyRateId(UUID id) {
		this.id = id;
	}

	private CurrencyRateId() {
	}

	public static CurrencyRateId generate() {
		return new CurrencyRateId(UUID.randomUUID());
	}

}
