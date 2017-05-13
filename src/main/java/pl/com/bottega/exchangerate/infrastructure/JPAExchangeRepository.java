package pl.com.bottega.exchangerate.infrastructure;

import pl.com.bottega.exchangerate.domain.ExchangeRate;
import pl.com.bottega.exchangerate.domain.ExchangeRateRepository;
import pl.com.bottega.exchangerate.domain.NoRateException;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.time.LocalDate;
import java.util.List;

public class JPAExchangeRepository implements ExchangeRateRepository {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public void put(ExchangeRate rate) {
		entityManager.persist(rate);
	}

	@Override
	public ExchangeRate getFor(String currency, LocalDate date) {
		List<ExchangeRate> rates = queryExchangeRates(currency, date);
		if (rates.isEmpty())
			throw new NoRateException();
		return rates.get(0);
	}

	@Override
	public void removeFor(String currency, LocalDate date) {
		List<ExchangeRate> exchangeRates = queryExchangeRates(currency, date);
		for (ExchangeRate rate : exchangeRates)
			entityManager.remove(rate);
	}

	private List<ExchangeRate> queryExchangeRates(String currency, LocalDate date) {
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<ExchangeRate> query = builder.createQuery(ExchangeRate.class);
		Root<ExchangeRate> exchange = query.from(ExchangeRate.class);
		query.where(builder.and(
				builder.equal(exchange.get("currency"), currency),
				builder.equal(exchange.get("date"), date)));
		return entityManager.createQuery(query).getResultList();
	}

}
