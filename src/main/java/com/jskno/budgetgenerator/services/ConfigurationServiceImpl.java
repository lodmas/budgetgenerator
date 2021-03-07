package com.jskno.budgetgenerator.services;

import com.jskno.budgetgenerator.interfaces.ConfigurationService;
import com.jskno.budgetgenerator.model.database.OptionValue;
import com.jskno.budgetgenerator.model.database.OptionValueSet;
import com.jskno.budgetgenerator.model.database.OptionValueSetEnum;
import com.jskno.budgetgenerator.model.ui.OptionValueSetUI;
import com.jskno.budgetgenerator.model.ui.OptionValueUI;
import com.jskno.budgetgenerator.react.OptionValuesChangedEvent;
import com.jskno.budgetgenerator.repository.OptionValueRepository;
import com.jskno.budgetgenerator.repository.OptionValueSetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ConfigurationServiceImpl implements ConfigurationService {

    private List<OptionValueSet> optionValueSets;
    private Map<OptionValueSet, List<OptionValue>> optionValueForOptionValueSets;
    private Set<OptionValueSetUI> optionValueSetsUI = new HashSet<>();
    private Map<OptionValueSetUI, List<OptionValueUI>> optionValueForOptionValueSetsUI = new HashMap<>();

    private OptionValueRepository optionValueRepository;

    private OptionValueSetRepository optionValueSetRepository;

    @Autowired
    public ConfigurationServiceImpl(OptionValueRepository optionValueRepository, OptionValueSetRepository optionValueSetRepository) {
        this.optionValueRepository = optionValueRepository;
        this.optionValueSetRepository = optionValueSetRepository;
    }

    @PostConstruct
    public void loadConfiguration() {
        optionValueSets =  optionValueSetRepository.findAll();
        optionValueForOptionValueSets = optionValueSets
                .stream().collect(Collectors.toMap(optionValueSet -> optionValueSet,OptionValueSet::getOptionValues));
    }

    private Map<OptionValueSet, Set<OptionValue>> getOptionValueForOptionValueSetsDB() {

        List<OptionValue> optionValues = optionValueRepository.findAll();

        Map<OptionValueSet, Set<OptionValue>> map = optionValues.stream()
                .collect(Collectors.groupingBy(OptionValue::getOptionValueSet, HashMap::new, Collectors.toCollection(HashSet::new)));
        return map;
    }



    private Map<OptionValueSetUI, List<OptionValueUI>> getOptionValueUIForOptionValueSetsUI() {

        Map<OptionValueSetUI, List<OptionValueUI>> map = getOptionValuesUI().stream()
                .collect(Collectors.groupingBy(OptionValueUI::getOptionValueSetUI, HashMap::new, Collectors.toCollection(ArrayList::new)));
        return map;
    }

    @Override
    public List<OptionValue> getConfiguration(OptionValueSet optionValueSet) {
        return new ArrayList<>(optionValueForOptionValueSets.get(optionValueSet));
    }

    @Override
    public List<OptionValue> getConfiguration(OptionValueSetEnum optionValueSetEnum) {
        return new ArrayList<>(optionValueForOptionValueSets.get(
                getOptionValueSet(optionValueSetEnum)
        ));
    }

    @Override
    public List<OptionValue> getOptionValues() {
        return optionValueForOptionValueSets.values()
                .stream()
                .flatMap(List::stream)
                .collect(Collectors.toList());
    }

    @Override
    public List<OptionValueSet> getOptionValueSet() {
        return optionValueForOptionValueSets.keySet()
                .stream()
                .collect(Collectors.toList());
    }

    @Override
    public OptionValueSet getOptionValueSet(OptionValueSetEnum optionValueSetEnum) {
        return optionValueSets.stream()
                .filter(optionValueSet -> optionValueSet.getValue().equals(optionValueSetEnum.getValue()))
                .findFirst()
                .get();
    }

    @Override
    public List<OptionValueSetUI> getOptionValueSetUI() {
        return optionValueForOptionValueSets.keySet()
                .stream()
                .map(optionValueSet -> new OptionValueSetUI(optionValueSet))
                .collect(Collectors.toList());
    }

    @Override
    public List<OptionValueUI> getOptionValuesUI(OptionValueSet optionValueSet) {
        return optionValueForOptionValueSets.get(optionValueSet)
                .stream()
                .map(optionValue -> new OptionValueUI(optionValue))
                .collect(Collectors.toList());
    }

    @Override
    public List<OptionValueSet> getOptionValueSets() {
        return optionValueSets;
    }

    @Override
    public Map<OptionValueSet, List<OptionValue>> getOptionValueForOptionValueSets() {
        return optionValueForOptionValueSets;
    }

    @Override
    public List<OptionValueUI> getOptionValuesUI() {
        List<OptionValueUI> list = getOptionValues()
                .stream()
                .map(optionValue -> new OptionValueUI(optionValue))
                .collect(Collectors.toList());
        return list;
    }

    @Override
    public Set<OptionValueSetUI> getOptionValueSetsUI() {
        return optionValueSetsUI;
    }

    @Override
    public Map<OptionValueSetUI, List<OptionValueUI>> getOptionValueForOptionValueSetsUI() {
        return optionValueForOptionValueSetsUI;
    }

    @Override
    public Map<OptionValueSetUI, List<OptionValueUI>> getOptionValueUI() {

        Map<OptionValueSetUI, List<OptionValueUI>> map = new HashMap<>();
        for(OptionValueSet ovs : optionValueSets) {
            OptionValueSetUI ovsUI = new OptionValueSetUI(ovs);
            List<OptionValueUI> ovUIs = ovs.getOptionValues()
                    .stream()
                    .map(optionValue -> new OptionValueUI(optionValue))
                    .collect(Collectors.toList());
            map.put(ovsUI, ovUIs);
        }
        return map;
    }

    @Override
    public Map<OptionValueSetEnum, List<OptionValueUI>> getOptionValueUIMap() {

        Map<OptionValueSetEnum, List<OptionValueUI>> map = new HashMap<>();
        for(OptionValueSet ovs : optionValueSetRepository.findAll()) {
            OptionValueSetEnum ovsEnum = getOptionValueSetEnum(ovs);
            List<OptionValueUI> ovUIs = ovs.getOptionValues().stream().map(optionValue -> new OptionValueUI(optionValue)).collect(Collectors.toList());
            map.put(ovsEnum, ovUIs);
        }
        return map;
    }

    @Override
    public OptionValueSetEnum getOptionValueSetEnum(OptionValueSet optionValueSet) {
        return Arrays.asList(OptionValueSetEnum.values()).stream()
                .filter(optionValueSetEnum -> optionValueSet.getValue().equals(optionValueSetEnum.getValue()))
                .findFirst()
                .get();
    }

    @Override
    public OptionValueSetEnum getOptionValueSetEnum(OptionValueSetUI optionValueSet) {
        return Arrays.asList(OptionValueSetEnum.values()).stream()
                .filter(optionValueSetEnum -> optionValueSet.getValue().equals(optionValueSetEnum.getValue()))
                .findFirst()
                .get();
    }

    @EventListener()
    @Order(Ordered.HIGHEST_PRECEDENCE)
    public void onApplicationEvent(OptionValuesChangedEvent optionValuesChangedEvent) {
        loadConfiguration();
    }

}
