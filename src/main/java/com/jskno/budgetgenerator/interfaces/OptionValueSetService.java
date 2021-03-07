package com.jskno.budgetgenerator.interfaces;

import com.jskno.budgetgenerator.model.database.OptionValue;
import com.jskno.budgetgenerator.model.database.OptionValueSet;
import com.jskno.budgetgenerator.model.ui.OptionValueSetUI;
import com.jskno.budgetgenerator.model.ui.OptionValueUI;
import javafx.collections.ObservableList;

import java.util.List;
import java.util.Map;

public interface OptionValueSetService {

    OptionValueSetUI getOptionValueSetUIById(Long id);

    ObservableList<OptionValueSetUI> loadObservableOptionValueSets();

    List<OptionValueSetUI> loadOptionValueSets();

    Map<OptionValueSetUI, List<OptionValueUI>>  loadObservableMapOptionValueSets();
}
