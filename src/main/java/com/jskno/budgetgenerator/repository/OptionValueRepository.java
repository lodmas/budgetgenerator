package com.jskno.budgetgenerator.repository;

import com.jskno.budgetgenerator.model.database.OptionValue;
import com.jskno.budgetgenerator.model.database.OptionValueSet;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OptionValueRepository extends CrudRepository<OptionValue, Long> {

    List<OptionValue> findByOptionValueSet_Value(String optionValueSetLabel);

    OptionValue findById(long id);

    @Override
    List<OptionValue> findAll();
}
