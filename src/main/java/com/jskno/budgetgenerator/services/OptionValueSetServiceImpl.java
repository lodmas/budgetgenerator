package com.jskno.budgetgenerator.services;

import com.jskno.budgetgenerator.interfaces.OptionValueSetService;
import com.jskno.budgetgenerator.model.database.OptionValueSet;
import com.jskno.budgetgenerator.model.ui.OptionValueSetUI;
import com.jskno.budgetgenerator.model.ui.OptionValueUI;
import com.jskno.budgetgenerator.repository.OptionValueSetRepository;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class OptionValueSetServiceImpl implements OptionValueSetService {

    @Autowired
    private OptionValueSetRepository optionValueSetRepository;

    public OptionValueSetServiceImpl() {
    }

    @Override
    public OptionValueSetUI getOptionValueSetUIById(Long id) {
        return new OptionValueSetUI(
                optionValueSetRepository.findById(id)
                        .orElseThrow(() -> new IllegalArgumentException()));
    }

    @Override
    public ObservableList<OptionValueSetUI> loadObservableOptionValueSets() {
        List<OptionValueSet> optionValueSets = optionValueSetRepository.findAll();
        ObservableList<OptionValueSetUI> optionValueSetsUI = FXCollections.observableArrayList();
        optionValueSets.forEach(optionValueSet ->
                optionValueSetsUI.add(new OptionValueSetUI(optionValueSet)));
        return optionValueSetsUI;
    }

    @Override
    public List<OptionValueSetUI> loadOptionValueSets() {
        List<OptionValueSet> optionValueSets = optionValueSetRepository.findAll();
        List<OptionValueSetUI> optionValueSetsUI = new ArrayList<>();
        optionValueSets.forEach(optionValueSet ->
                optionValueSetsUI.add(new OptionValueSetUI(optionValueSet)));
        return optionValueSetsUI;
    }

    @Override
    public Map<OptionValueSetUI, List<OptionValueUI>> loadObservableMapOptionValueSets() {
        List<OptionValueSet> optionValueSets = optionValueSetRepository.findAll();
//        Map<OptionValueSetUI, List<OptionValueSetUI>> map = optionValueSets
//                .stream().collect(Collectors.toMap(
//                        optionValueSet -> new OptionValueSetUI(optionValueSet),
//                        optionValueSet -> optionValueSet.getOptionValues()));
        Map<OptionValueSetUI, List<OptionValueUI>> map = new HashMap<>();
        optionValueSets.forEach(optionValueSet -> {
            List<OptionValueUI> optionValuesUI = new ArrayList<>();
            optionValueSet.getOptionValues().forEach(optionValue ->
                    optionValuesUI.add(new OptionValueUI(optionValue)));
            map.put(new OptionValueSetUI(optionValueSet), optionValuesUI);
        });
        return map;
    }


}
