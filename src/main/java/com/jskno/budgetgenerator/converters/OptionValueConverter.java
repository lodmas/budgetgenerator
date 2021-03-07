package com.jskno.budgetgenerator.converters;

import com.jskno.budgetgenerator.interfaces.ConfigurationService;
import com.jskno.budgetgenerator.model.database.OptionValue;
import com.jskno.budgetgenerator.services.ConfigurationServiceImpl;
import javafx.util.StringConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OptionValueConverter extends StringConverter<OptionValue> {

    @Autowired
    private ConfigurationService configurationService;
//    private ConfigurationService configurationService = new ConfigurationServiceImpl();

    @Override
    public String toString(OptionValue optionValue) {
        return optionValue == null ? null : optionValue.getLabel();
    }

    @Override
    public OptionValue fromString(String value) {
        return configurationService.getOptionValues()
                .stream()
                .filter(optionValue -> optionValue.getValue().equals(value))
                .findFirst()
                .get();
    }
}
