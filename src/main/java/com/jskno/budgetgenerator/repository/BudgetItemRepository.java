package com.jskno.budgetgenerator.repository;

import com.jskno.budgetgenerator.model.database.Budget;
import com.jskno.budgetgenerator.model.database.BudgetItem;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BudgetItemRepository extends CrudRepository<BudgetItem, Long> {

    List<BudgetItem> findByBudget_Id(long budgetId);

    Budget findById(long id);

}
