package com.jskno.budgetgenerator.services;

import com.jskno.budgetgenerator.converters.OptionValueConverter;
import com.jskno.budgetgenerator.converters.OptionValueSetUIConverter;
import com.jskno.budgetgenerator.interfaces.ChoiceBoxService;
import com.jskno.budgetgenerator.interfaces.ConfigurationService;
import com.jskno.budgetgenerator.model.database.OptionValue;
import com.jskno.budgetgenerator.model.database.OptionValueSetEnum;
import com.jskno.budgetgenerator.model.ui.OptionValueSetUI;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ChoiceBox;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ChoiceBoxServiceImpl implements ChoiceBoxService {

    private OptionValueConverter optionValueConverter;
    private OptionValueSetUIConverter optionValueSetUIConverter;
    private ConfigurationService configurationService;

    @Autowired
    public ChoiceBoxServiceImpl(OptionValueConverter optionValueConverter, OptionValueSetUIConverter optionValueSetUIConverter,
                ConfigurationService configurationService) {
        this.optionValueConverter = optionValueConverter;
        this.optionValueSetUIConverter = optionValueSetUIConverter;
        this.configurationService = configurationService;
    }

    @Override
    public void initChoiceBox(ChoiceBox<OptionValue> choiceBox) {
        choiceBox.setConverter(optionValueConverter);
    }

    @Override
    public void fillChoiceBox(ChoiceBox<OptionValue> choiceBox, OptionValueSetEnum optionValueSetEnum) {
        ObservableList<OptionValue> list =
                FXCollections.observableArrayList(configurationService.getConfiguration(optionValueSetEnum));

        choiceBox.getItems().clear();
        choiceBox.setConverter(optionValueConverter);
        choiceBox.setItems(list);
    }

    @Override
    public void fillOptionValueSetUIChoiceBox(ChoiceBox<OptionValueSetUI> choiceBox) {
        ObservableList<OptionValueSetUI> list =
                FXCollections.observableArrayList((configurationService.getOptionValueSetUI()));

        choiceBox.setConverter(optionValueSetUIConverter);
        choiceBox.setItems(list);
    }

    @Override
    public void reloadOptionValueDropBox(ChoiceBox<OptionValue> choiceBox, OptionValueSetEnum optionValueSetEnum) {
        OptionValue selectedOptionValue = choiceBox.getSelectionModel().getSelectedItem();
        fillChoiceBox(choiceBox, optionValueSetEnum);
        choiceBox.getSelectionModel().select(selectedOptionValue);
    }
}
