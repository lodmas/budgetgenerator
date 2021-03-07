package com.jskno.budgetgenerator.model.ui;

import com.jskno.budgetgenerator.model.database.Budget;
import javafx.beans.property.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;

public class BudgetUITable {

    private ObjectProperty<LocalDate> updateDate;

    private LongProperty id;

    private StringProperty name;

    private ObjectProperty<BigDecimal> total;

    public BudgetUITable(ObjectProperty<LocalDate> updateDate, LongProperty id,
                         StringProperty name, ObjectProperty<BigDecimal> total) {
        this.updateDate = updateDate;
        this.id = id;
        this.name = name;
        this.total = total;
    }

    public BudgetUITable(Budget budget) {
        this.updateDate = new SimpleObjectProperty(budget.getUpdatedAt());
        this.id = new SimpleLongProperty(budget.getId());
        this.name = new SimpleStringProperty(budget.getName());
        this.total = new SimpleObjectProperty(budget.getTotal());
    }

    public LocalDate getUpdateDate() {
        return updateDate.get();
    }

    public ObjectProperty<LocalDate> updateDateProperty() {
        return updateDate;
    }

    public void setUpdateDate(LocalDate updateDate) {
        this.updateDate.set(updateDate);
    }

    public long getId() {
        return id.get();
    }

    public LongProperty idProperty() {
        return id;
    }

    public void setId(long id) {
        this.id.set(id);
    }

    public String getName() {
        return name.get();
    }

    public StringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public BigDecimal getTotal() {
        return total.get();
    }

    public ObjectProperty<BigDecimal> totalProperty() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total.set(total);
    }
}
