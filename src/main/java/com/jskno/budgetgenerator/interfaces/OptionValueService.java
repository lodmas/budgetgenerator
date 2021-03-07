package com.jskno.budgetgenerator.interfaces;

import com.jskno.budgetgenerator.model.database.OptionValue;
import com.jskno.budgetgenerator.model.database.OptionValueSet;
import com.jskno.budgetgenerator.model.ui.OptionValueUI;
import javafx.collections.ObservableList;

import java.util.List;

public interface OptionValueService {

    ObservableList<OptionValueUI> loadObservableOptionValues();

    ObservableList<OptionValueUI> loadObservableOptionValuesForSet(OptionValueSet optionValueSet);

    List<OptionValueUI> loadOptionValues();

    List<OptionValueUI> loadOptionValuesForSet(OptionValueSet optionValueSet);

    OptionValue getOptionValueById(Long id);

    OptionValueUI getOptionValueUIById(Long id);

    void removeOptionValueById(Long id);

    OptionValue updateOptionValue(OptionValue optionValue);

    OptionValue saveOptionValue(OptionValue optionValue);
}
