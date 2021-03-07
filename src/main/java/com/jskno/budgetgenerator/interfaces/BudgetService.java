package com.jskno.budgetgenerator.interfaces;

import com.jskno.budgetgenerator.model.database.Budget;
import com.jskno.budgetgenerator.model.ui.BudgetUITable;
import javafx.collections.ObservableList;

public interface BudgetService {

    ObservableList<BudgetUITable> loadBudgetList();

    void deleteById(Long id);

    Budget saveOrUpdate(Budget budget);

    Budget findById(long budgetId);
}
