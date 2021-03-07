package com.jskno.budgetgenerator.model.ui.converters;

import javafx.util.converter.BigDecimalStringConverter;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;

@Component
public class BigDecimalCurrencyStringConverter extends BigDecimalStringConverter {

	private DecimalFormat currencyFormat;

	public BigDecimalCurrencyStringConverter() {
		currencyFormat = (DecimalFormat) NumberFormat.getInstance();
		DecimalFormatSymbols dfs = new DecimalFormatSymbols();
		dfs.setGroupingSeparator(',');
		dfs.setMonetaryDecimalSeparator('.');
		currencyFormat.setDecimalFormatSymbols(dfs);
		currencyFormat.setMaximumFractionDigits(2);
		currencyFormat.setMinimumFractionDigits(2);
		currencyFormat.setPositiveSuffix(" €");
	}

	//	@Override
//	public BigDecimal fromString(final String value) {
//		return value.isEmpty() || !isNumber(value) ? null
//				: super.fromString(value);
//	}

//	public boolean isNumber(String value) {
//		int size = value.length();
//		for (int i = 0; i < size; i++) {
//			if (!Character.isDigit(value.charAt(i))) {
//				return false;
//			}
//		}
//		return size > 0;
//	}

	@Override
	public String toString(BigDecimal value) {
		return value == null ? "" : currencyFormat.format(value);
	}

//	@Override
//	public BigDecimal fromString(String var1) {
//		if (var1 == null) {
//			return null;
//		} else {
//			var1 = var1.trim();
//			try {
//				return var1.length() < 1 ? null :
//						new BigDecimal(currencyFormat.parse(var1).doubleValue());
//			} catch (ParseException e) {
//				e.printStackTrace();
//				throw new IllegalArgumentException();
//			}
//		}
//	}

}
