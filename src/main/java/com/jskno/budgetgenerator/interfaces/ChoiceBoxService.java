package com.jskno.budgetgenerator.interfaces;

import com.jskno.budgetgenerator.model.database.OptionValue;
import com.jskno.budgetgenerator.model.database.OptionValueSet;
import com.jskno.budgetgenerator.model.database.OptionValueSetEnum;
import com.jskno.budgetgenerator.model.ui.OptionValueSetUI;
import javafx.scene.control.ChoiceBox;

public interface ChoiceBoxService {

    void initChoiceBox(ChoiceBox<OptionValue> choiceBox);

    void fillChoiceBox(ChoiceBox<OptionValue> choiceBox, OptionValueSetEnum optionValueSet);

    void fillOptionValueSetUIChoiceBox(ChoiceBox<OptionValueSetUI> choiceBox);

    void reloadOptionValueDropBox(ChoiceBox<OptionValue> categoryChoiceBox, OptionValueSetEnum category);
}
