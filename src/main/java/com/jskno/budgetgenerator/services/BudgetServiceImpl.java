package com.jskno.budgetgenerator.services;

import com.jskno.budgetgenerator.interfaces.BudgetService;
import com.jskno.budgetgenerator.model.database.Budget;
import com.jskno.budgetgenerator.model.ui.BudgetUITable;
import com.jskno.budgetgenerator.repository.BudgetRepository;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BudgetServiceImpl implements BudgetService {

    @Autowired
    private BudgetRepository budgetRepository;

    @Override
    public ObservableList<BudgetUITable> loadBudgetList() {
        List<Budget> budgets = budgetRepository.findAll();
        ObservableList<BudgetUITable> budgetUITableList = FXCollections.observableArrayList();
        budgets.forEach(budget -> budgetUITableList.add(new BudgetUITable(budget)));
        return budgetUITableList;
    }

    @Override
    public void deleteById(Long id) {
        budgetRepository.deleteById(id);
    }

    @Override
    public Budget saveOrUpdate(Budget budget) {
        return budgetRepository.save(budget);
    }

    @Override
    public Budget findById(long budgetId) {
        Budget budget = budgetRepository.findById(budgetId);
//        budget.getBudgetItems();
        return budget;
    }
}
