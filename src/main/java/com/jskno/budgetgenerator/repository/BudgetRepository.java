package com.jskno.budgetgenerator.repository;

import com.jskno.budgetgenerator.model.database.Budget;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BudgetRepository extends CrudRepository<Budget, Long> {

    List<Budget> findByName(String name);

    Budget findById(long id);

    @Override
    List<Budget> findAll();

    List<Budget> findAllBy();
}
