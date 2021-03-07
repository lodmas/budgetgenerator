package com.jskno.budgetgenerator.repository;

import com.jskno.budgetgenerator.model.database.OptionValue;
import com.jskno.budgetgenerator.model.database.OptionValueSet;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OptionValueSetRepository extends CrudRepository<OptionValueSet, Long> {

    @Override
    List<OptionValueSet> findAll();
}
