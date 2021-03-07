package com.jskno.budgetgenerator.react.openformevents;

import org.springframework.context.ApplicationEvent;

public class BudgetOpenModifyFormEvent extends ApplicationEvent {

    private Long budgetId;

    public BudgetOpenModifyFormEvent(Object source, Long budgetId) {
        super(source);
        this.budgetId = budgetId;
    }

    public Long getBudgetId() {
        return budgetId;
    }
}
