package com.jskno.budgetgenerator.react.openformevents;

import org.springframework.context.ApplicationEvent;

public class BudgetOpenViewFromEvent extends ApplicationEvent {

    private Long budgetId;

    public BudgetOpenViewFromEvent(Object source, Long budgetId) {
        super(source);
        this.budgetId = budgetId;
    }

    public Long getBudgetId() {
        return budgetId;
    }
}
