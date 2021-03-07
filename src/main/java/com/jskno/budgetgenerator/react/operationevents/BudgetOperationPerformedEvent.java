package com.jskno.budgetgenerator.react.operationevents;

import org.springframework.context.ApplicationEvent;

public class BudgetOperationPerformedEvent extends ApplicationEvent {

    private Long budgetId;

    public BudgetOperationPerformedEvent(Object source, Long budgetId) {
        super(source);
        this.budgetId = budgetId;
    }

    public Long getBudgetId() {
        return budgetId;
    }
}
