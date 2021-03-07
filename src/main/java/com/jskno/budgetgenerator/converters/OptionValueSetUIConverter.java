package com.jskno.budgetgenerator.converters;

import com.jskno.budgetgenerator.interfaces.ConfigurationService;
import com.jskno.budgetgenerator.model.database.OptionValue;
import com.jskno.budgetgenerator.model.ui.OptionValueSetUI;
import javafx.util.StringConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OptionValueSetUIConverter extends StringConverter<OptionValueSetUI> {

    @Autowired
    private ConfigurationService configurationService;

    @Override
    public String toString(OptionValueSetUI optionValue) {
        return optionValue.getLabel();
    }

    @Override
    public OptionValueSetUI fromString(String value) {
        return configurationService.getOptionValueSetUI()
                .stream()
                .filter(optionValueSetUI -> optionValueSetUI.getValue().equals(value))
                .findFirst()
                .get();
    }
}
