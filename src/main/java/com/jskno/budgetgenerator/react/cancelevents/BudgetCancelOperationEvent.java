package com.jskno.budgetgenerator.react.cancelevents;

import org.springframework.context.ApplicationEvent;

public class BudgetCancelOperationEvent extends ApplicationEvent {

    private Long budgetId;

    public BudgetCancelOperationEvent(Object source, Long budgetId) {
        super(source);
        this.budgetId = budgetId;
    }

    public Long getBudgetId() {
        return budgetId;
    }
}
