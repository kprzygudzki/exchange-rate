package pl.com.bottega.exchangerate.domain.commands;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public interface Validatable {

	String IS_REQUIRED = "is required";
	String GREATER_THAN_ZERO_REQUIRED = "must be > than 0.0";

	void validate(ValidationErrors errors);

	class ValidationErrors {

		private Map<String, Set<String>> errors = new HashMap<>();

		public void add(String fieldName, String errorMessage) {
			Set<String> fieldErrors = errors.getOrDefault(fieldName, new HashSet<>());
			fieldErrors.add(errorMessage);
			errors.putIfAbsent(fieldName, fieldErrors);
		}

		public boolean isValid() {
			return errors.isEmpty();
		}

		public Map<String, Set<String>> getErrors() {
			return new HashMap<>(errors);
		}

	}

	default boolean isEmpty(String s) {
		return s == null || s.trim().length() == 0;
	}

}
