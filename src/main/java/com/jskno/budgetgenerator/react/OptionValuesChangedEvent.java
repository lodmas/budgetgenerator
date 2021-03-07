package com.jskno.budgetgenerator.react;

import com.jskno.budgetgenerator.model.database.OptionValueSetEnum;
import org.springframework.context.ApplicationEvent;

public class OptionValuesChangedEvent extends ApplicationEvent {

    private final OptionValueSetEnum optionValueSetEnum;

    public OptionValuesChangedEvent(OptionValueSetEnum optionValueSetEnum, Object source) {
        super(source);
        this.optionValueSetEnum = optionValueSetEnum;
    }

    public OptionValueSetEnum getOptionValueSetEnum() {
        return optionValueSetEnum;
    }
}
