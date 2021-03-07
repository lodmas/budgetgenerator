package com.jskno.budgetgenerator.model.ui;

import com.jskno.budgetgenerator.model.database.Budget;
import com.jskno.budgetgenerator.model.database.BudgetItem;
import com.jskno.budgetgenerator.utils.Constants;
import javafx.beans.Observable;
import javafx.beans.binding.Binding;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.DoubleBinding;
import javafx.beans.binding.ObjectBinding;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.List;
import java.util.SimpleTimeZone;
import java.util.stream.Collectors;

public class BudgetUI {

    private LongProperty id;

    private ObjectProperty<Date> updateDate;

    private StringProperty name;

    private ObjectProperty<BigDecimal> totalCost;

    private ObjectBinding<BigDecimal> marginPercent;

    private ObjectProperty<BigDecimal> totalMargin;

    private ObjectProperty<BigDecimal> totalCostPlusMargin;

    private ObjectProperty<BigDecimal> totalVat;

    private ObjectProperty<BigDecimal> total;

    private ObservableList<BudgetItemUI> budgetItems = FXCollections.observableArrayList();
//    private ObservableList<DoubleBinding> totalCostBudgetItems = FXCollections.observableArrayList();
    private ObservableList<BudgetItemUI> totalCostBudgetItems = FXCollections.observableArrayList(
            budgetItemUI -> new Observable[]{budgetItemUI.totalCostProperty()});

    public BudgetUI() {
        this.id = new SimpleLongProperty();
        this.updateDate = new SimpleObjectProperty<>();
        this.name = new SimpleStringProperty();
        this.totalCost = new SimpleObjectProperty(new BigDecimal(0));
        this.totalMargin = new SimpleObjectProperty(new BigDecimal(0));
        this.totalCostPlusMargin = new SimpleObjectProperty(new BigDecimal(0));
        this.totalVat = new SimpleObjectProperty(new BigDecimal(0));
        this.total = new SimpleObjectProperty(new BigDecimal(0));
//        defineBinding();
        defineMarginPercentBinding();
    }

    public BudgetUI(Budget budget) {
        this.updateDate = new SimpleObjectProperty(budget.getUpdatedAt());
        this.id = new SimpleLongProperty(budget.getId());
        this.name = new SimpleStringProperty(budget.getName());
        addBudgetItemsFromDomain(budget.getBudgetItems());
//        this.totalCost = new SimpleDoubleProperty();
        this.totalCost = new SimpleObjectProperty(budget.getTotalCost());
        this.totalMargin = new SimpleObjectProperty(budget.getTotalMargin());
        this.totalCostPlusMargin = new SimpleObjectProperty(budget.getTotalCostPlusMargin());
        this.totalVat = new SimpleObjectProperty(budget.getTotalVat());
        this.total = new SimpleObjectProperty(budget.getTotal());
//        defineBinding();
        defineMarginPercentBinding();
    }

    private void addBudgetItemsFromDomain(List<BudgetItem> budgetItems) {
        budgetItems.forEach(budgetItem -> this.budgetItems.add(new BudgetItemUI(budgetItem)));
    }

    public Long getId() {
        return id != null ? id.get() : null;
    }

    public LongProperty idProperty() {
        return id;
    }

    public void setId(long id) {
        this.id.set(id);
    }

    public Date getUpdateDate() {
        return updateDate.get();
    }

    public ObjectProperty<Date> updateDateProperty() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate.set(updateDate);
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

    public BigDecimal getTotalCost() {
        return totalCost.get();
    }

    public ObjectProperty<BigDecimal> totalCostProperty() {
        return totalCost;
    }

    public void setTotalCost(BigDecimal totalCost) {
        this.totalCost.set(totalCost);
    }

    public BigDecimal getMarginPercent() {
        return marginPercent.getValue();
    }

    public ObjectBinding<BigDecimal> marginPercentProperty() {
        return marginPercent;
    }

    public BigDecimal getTotalMargin() {
        return totalMargin.get();
    }

    public ObjectProperty<BigDecimal> totalMarginProperty() {
        return totalMargin;
    }

    public void setTotalMargin(BigDecimal totalMargin) {
        this.totalMargin.set(totalMargin);
    }

    public BigDecimal getTotalCostPlusMargin() {
        return totalCostPlusMargin.get();
    }

    public ObjectProperty<BigDecimal> totalCostPlusMarginProperty() {
        return totalCostPlusMargin;
    }

    public void setTotalCostPlusMargin(BigDecimal totalCostPlusMargin) {
        this.totalCostPlusMargin.set(totalCostPlusMargin);
    }

    public BigDecimal getTotalVat() {
        return totalVat.get();
    }

    public ObjectProperty<BigDecimal> totalVatProperty() {
        return totalVat;
    }

    public void setTotalVat(BigDecimal totalVat) {
        this.totalVat.set(totalVat);
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

    public ObservableList<BudgetItemUI> getBudgetItems() {
        return budgetItems;
    }

    public void recalculateTotalCost() {
        this.setTotalCost(
                this.getBudgetItems().stream()
                        .map(BudgetItemUI::getTotalCost)
                        .reduce(BigDecimal.ZERO, BigDecimal::add)
                        .setScale(2, RoundingMode.HALF_UP));
        recalculateTotalMargin();
        recalculateTotalCostPlusMargin();
        recalculateTotalVat();
        recalculateTotalBudget();
    }

    public void recalculateTotalMargin() {
        this.setTotalMargin(
                this.getBudgetItems().stream()
                        .map(BudgetItemUI::getMarginAmount)
                        .reduce(BigDecimal.ZERO, BigDecimal::add)
                        .setScale(2, RoundingMode.HALF_UP));
        recalculateTotalCostPlusMargin();
        recalculateTotalVat();
        recalculateTotalBudget();
    }

    public void recalculateTotalCostPlusMargin() {
        this.setTotalCostPlusMargin(
                this.getBudgetItems().stream()
                        .map(BudgetItemUI::getTotalCostPlusMargin)
                        .reduce(BigDecimal.ZERO, BigDecimal::add)
                        .setScale(2, RoundingMode.HALF_UP));
        recalculateTotalVat();
        recalculateTotalBudget();
    }

    public void recalculateTotalVat() {
        this.setTotalVat(
                this.getBudgetItems().stream()
                        .map(BudgetItemUI::getVatAmount)
                        .reduce(BigDecimal.ZERO, BigDecimal::add)
                        .setScale(2, RoundingMode.HALF_UP));
        recalculateTotalBudget();
    }

    public void recalculateTotalBudget() {
        this.setTotal(
                this.getBudgetItems().stream()
                        .map(BudgetItemUI::getTotalBudgetItem)
                        .reduce(BigDecimal.ZERO, BigDecimal::add)
                        .setScale(2, RoundingMode.HALF_UP));
    }

    public void recalculateTotals() {
        this.recalculateTotalCost();
//        this.recalculateTotalMargin();
//        this.recalculateTotalCostPlusMargin();
//        this.recalculateTotalVat();
//        this.recalculateTotalBudget();
    }


//    private void defineBinding() {
//        this.budgetItems.forEach(budgetItemUI -> this.totalCostBudgetItems.add(budgetItemUI.totalCostProperty()));
//        this.totalCost = Bindings.createDoubleBinding(() ->
//                        this.totalCostBudgetItems.stream().collect(Collectors.summingDouble(DoubleBinding::get)),
//                this.totalCostBudgetItems);
//    }

//    private void defineBinding() {
//        this.totalCost.bind(Bindings.createDoubleBinding(() ->
//            this.totalCostBudgetItems.stream()
//                    .map(BudgetItemUI::getTotalCost)
//                    .collect(Collectors.summingDouble(Number::doubleValue)),
//                this.totalCostBudgetItems));
//    }

    private void defineBinding() {
        this.totalCost.bind(Bindings.createObjectBinding(() ->
                        this.totalCostBudgetItems.stream()
                                .map(BudgetItemUI::getTotalCost)
                                .reduce(BigDecimal.ZERO, BigDecimal::add),
                this.totalCostBudgetItems));
    }

    private void defineMarginPercentBinding() {
        this.marginPercent = Bindings.createObjectBinding(() -> {
            if(this.totalCost.get().compareTo(BigDecimal.ZERO) == 0) {
                return BigDecimal.ZERO;
            } else {
                return this.totalMargin.get()
                        .divide(this.totalCost.get(), 4, RoundingMode.HALF_UP)
                        .multiply(Constants.ONE_HUNDRED);
            }
        },
        this.totalMargin);
    }
}
