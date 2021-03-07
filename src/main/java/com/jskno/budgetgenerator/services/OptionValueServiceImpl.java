package com.jskno.budgetgenerator.services;

import com.jskno.budgetgenerator.interfaces.OptionValueService;
import com.jskno.budgetgenerator.model.database.OptionValue;
import com.jskno.budgetgenerator.model.database.OptionValueSet;
import com.jskno.budgetgenerator.model.ui.OptionValueUI;
import com.jskno.budgetgenerator.repository.OptionValueRepository;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OptionValueServiceImpl implements OptionValueService {

    private OptionValueRepository optionValueRepository;

    @Autowired
    public OptionValueServiceImpl(OptionValueRepository optionValueRepository) {
        this.optionValueRepository = optionValueRepository;
    }

    @Override
    public ObservableList<OptionValueUI> loadObservableOptionValues() {
        List<OptionValue> optionValues = optionValueRepository.findAll();
        ObservableList<OptionValueUI> optionValuesUI = FXCollections.observableArrayList();
        optionValues.forEach(optionValue ->
                optionValuesUI.add(new OptionValueUI(optionValue)));
        return optionValuesUI;
    }

    @Override
    public ObservableList<OptionValueUI> loadObservableOptionValuesForSet(OptionValueSet optionValueSet) {
        List<OptionValue> optionValues = optionValueRepository
                .findByOptionValueSet_Value(optionValueSet.getValue());
        ObservableList<OptionValueUI> optionValuesUI = FXCollections.observableArrayList();
        optionValues.forEach(optionValue ->
                optionValuesUI.add(new OptionValueUI(optionValue)));
        return optionValuesUI;
    }

    @Override
    public List<OptionValueUI> loadOptionValues() {
        List<OptionValue> optionValues = optionValueRepository.findAll();
        List<OptionValueUI> optionValuesUI = new ArrayList<>();
        optionValues.forEach(optionValue ->
                optionValuesUI.add(new OptionValueUI(optionValue)));
        return optionValuesUI;
    }

    @Override
    public List<OptionValueUI> loadOptionValuesForSet(OptionValueSet optionValueSet) {
        List<OptionValue> optionValues = optionValueRepository
                .findByOptionValueSet_Value(optionValueSet.getValue());
        List<OptionValueUI> optionValuesUI = new ArrayList<>();
        optionValues.forEach(optionValue ->
                optionValuesUI.add(new OptionValueUI(optionValue)));
        return optionValuesUI;
    }

    @Override
    public OptionValue getOptionValueById(Long id) {
        return optionValueRepository.findById(id).get();
    }

    @Override
    public OptionValueUI getOptionValueUIById(Long id) {
        return new OptionValueUI(getOptionValueById(id));
    }

    @Override
    public void removeOptionValueById(Long id) {
        OptionValue optionValue = optionValueRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Cannot delete a Id that does not exist !!"));
        optionValueRepository.delete(optionValue);
    }

    @Override
    public OptionValue updateOptionValue(OptionValue optionValue) {
        return optionValueRepository.save(optionValue);
    }

    @Override
    public OptionValue saveOptionValue(OptionValue optionValue) {
        return optionValueRepository.save(optionValue);
    }


}
