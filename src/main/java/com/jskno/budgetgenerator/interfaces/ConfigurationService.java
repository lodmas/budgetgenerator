package com.jskno.budgetgenerator.interfaces;

import com.jskno.budgetgenerator.model.database.OptionValue;
import com.jskno.budgetgenerator.model.database.OptionValueSet;
import com.jskno.budgetgenerator.model.database.OptionValueSetEnum;
import com.jskno.budgetgenerator.model.ui.OptionValueSetUI;
import com.jskno.budgetgenerator.model.ui.OptionValueUI;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface ConfigurationService {

    List<OptionValue> getConfiguration(OptionValueSet optionValueSet);

    List<OptionValue> getConfiguration(OptionValueSetEnum optionValueSet);

    List<OptionValue> getOptionValues();

    List<OptionValueSet> getOptionValueSet();

    OptionValueSet getOptionValueSet(OptionValueSetEnum optionValueSetEnum);

    List<OptionValueSetUI> getOptionValueSetUI();

    List<OptionValueUI> getOptionValuesUI(OptionValueSet optionValueSet);

    List<OptionValueSet> getOptionValueSets();

    Map<OptionValueSet, List<OptionValue>> getOptionValueForOptionValueSets();

    List<OptionValueUI> getOptionValuesUI();

    Set<OptionValueSetUI> getOptionValueSetsUI();

    Map<OptionValueSetUI, List<OptionValueUI>> getOptionValueForOptionValueSetsUI();

    Map<OptionValueSetUI, List<OptionValueUI>> getOptionValueUI();

    Map<OptionValueSetEnum, List<OptionValueUI>> getOptionValueUIMap();

    OptionValueSetEnum getOptionValueSetEnum(OptionValueSet optionValueSet);

    OptionValueSetEnum getOptionValueSetEnum(OptionValueSetUI optionValueSet);
}
