package com.jskno.budgetgenerator.model.ui.converters;

import javafx.util.converter.BigDecimalStringConverter;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Currency;
import java.util.Locale;

@Component
public class BigDecimalPercentStringConverter extends BigDecimalStringConverter {

	private DecimalFormat percentFormat;

	public BigDecimalPercentStringConverter() {
		percentFormat = new DecimalFormat(" #,##0.00 '%'");
	}

	@Override
	public String toString(BigDecimal value) {
		return value == null ? "" : percentFormat.format(value);
	}

}
