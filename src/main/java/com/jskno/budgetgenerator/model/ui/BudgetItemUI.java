package com.jskno.budgetgenerator.model.ui;

import com.jskno.budgetgenerator.model.database.BudgetItem;
import com.jskno.budgetgenerator.model.database.OptionValue;
import com.jskno.budgetgenerator.model.database.base.AbstractEntity;
import com.jskno.budgetgenerator.utils.Constants;
import javafx.beans.binding.Binding;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.DoubleBinding;
import javafx.beans.property.*;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

public class BudgetItemUI extends AbstractEntity {

    private LongProperty id;

    private LongProperty itemId;

    private StringProperty concept;

    private StringProperty notes;

    private ObjectProperty<BigDecimal> price;

    private ObjectProperty<BigDecimal> quantity;

    private ObjectProperty<BigDecimal> margin;

    private ObjectProperty<BigDecimal> vat;

    private ObjectProperty<OptionValue> units;

    private Binding<BigDecimal> totalCost;

    private Binding<BigDecimal> marginAmount;

    private Binding<BigDecimal> totalCostPlusMargin;

    private Binding<BigDecimal> vatAmount;

    private Binding<BigDecimal> totalBudgetItem;

    private LongProperty budgetId;

    public BudgetItemUI(BudgetItem budgetItem) {
        this.id = new SimpleLongProperty(budgetItem.getId());
        this.itemId = new SimpleLongProperty(budgetItem.getItemId());
        this.concept = new SimpleStringProperty(budgetItem.getConcept());
        this.notes = new SimpleStringProperty(budgetItem.getNotes());
        this.price = new SimpleObjectProperty(budgetItem.getPrice());
        this.units = new SimpleObjectProperty<>(budgetItem.getUnits());
        this.quantity = new SimpleObjectProperty(budgetItem.getQuantity());
        this.vat = new SimpleObjectProperty(budgetItem.getVat()
                .multiply(Constants.ONE_HUNDRED));
        this.margin = new SimpleObjectProperty(budgetItem.getMargin()
                .multiply(Constants.ONE_HUNDRED));
        this.budgetId = new SimpleLongProperty(budgetItem.getBudget().getId());
        defineBinding();
    }

    public BudgetItemUI(ItemUI itemUI) {
        this.id = new SimpleLongProperty();
        this.itemId = new SimpleLongProperty(itemUI.getId());
        this.concept = new SimpleStringProperty(itemUI.getConcept());
        this.notes = new SimpleStringProperty(itemUI.getNotes());
        this.price = new SimpleObjectProperty(itemUI.getPrice());
        this.units = new SimpleObjectProperty(itemUI.getUnits());
        this.quantity = new SimpleObjectProperty(new BigDecimal(1));
        this.vat = new SimpleObjectProperty(itemUI.getVat());
        this.margin = new SimpleObjectProperty(itemUI.getMargin());
        this.budgetId = new SimpleLongProperty();
        defineBinding();
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

    public long getItemId() {
        return itemId.get();
    }

    public LongProperty itemIdProperty() {
        return itemId;
    }

    public void setItemId(long itemId) {
        this.itemId.set(itemId);
    }

    public String getConcept() {
        return concept.get();
    }

    public StringProperty conceptProperty() {
        return concept;
    }

    public void setConcept(String concept) {
        this.concept.set(concept);
    }

    public String getNotes() {
        return notes.get();
    }

    public StringProperty notesProperty() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes.set(notes);
    }

    public BigDecimal getPrice() {
        return price.get();
    }

    public ObjectProperty<BigDecimal> priceProperty() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price.set(price);
    }

    public BigDecimal getQuantity() {
        return quantity.get();
    }

    public ObjectProperty<BigDecimal> quantityProperty() {
        return quantity;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity.set(quantity);
    }

    public BigDecimal getMargin() {
        return margin.get();
    }

    public ObjectProperty<BigDecimal> marginProperty() {
        return margin;
    }

    public void setMargin(BigDecimal margin) {
        this.margin.set(margin);
    }

    public BigDecimal getVat() {
        return vat.get();
    }

    public ObjectProperty<BigDecimal> vatProperty() {
        return vat;
    }

    public void setVat(BigDecimal vat) {
        this.vat.set(vat);
    }

    public OptionValue getUnits() {
        return units.get();
    }

    public ObjectProperty<OptionValue> unitsProperty() {
        return units;
    }

    public void setUnits(OptionValue units) {
        this.units.set(units);
    }

    public BigDecimal getTotalCost() {
        return totalCost.getValue();
    }

    public Binding<BigDecimal> totalCostProperty() {
        return totalCost;
    }

    public BigDecimal getMarginAmount() {
        return marginAmount.getValue();
    }

    public Binding<BigDecimal> marginAmountProperty() {
        return marginAmount;
    }

    public BigDecimal getTotalCostPlusMargin() {
        return totalCostPlusMargin.getValue();
    }

    public Binding<BigDecimal> totalCostPlusMarginProperty() {
        return totalCostPlusMargin;
    }

    public BigDecimal getVatAmount() {
        return vatAmount.getValue();
    }

    public Binding<BigDecimal> vatAmountProperty() {
        return vatAmount;
    }

    public BigDecimal getTotalBudgetItem() {
        return totalBudgetItem.getValue();
    }

    public Binding<BigDecimal> totalBudgetItemProperty() {
        return totalBudgetItem;
    }

    public long getBudgetId() {
        return budgetId.get();
    }

    public LongProperty budgetIdProperty() {
        return budgetId;
    }

    public void setBudgetId(long budgetId) {
        this.budgetId.set(budgetId);
    }

    private void defineBinding() {
        defineTotalCostBinding();
        defineMarginAmountBinding();
        defineTotalCostPlusMarginBinding();
        defineVatAmountBinding();
        defineTotalBudgetItemBinding();
    }

    private void defineTotalCostBinding() {
        this.totalCost = Bindings.createObjectBinding(
                () -> calculateTotalCost(),
                this.quantity, this.price);
    }

    private BigDecimal calculateTotalCost() {
        return this.quantity.get()
                .multiply(this.price.get())
                .setScale(2, RoundingMode.HALF_UP);
    }


    private void defineMarginAmountBinding() {
        this.marginAmount = Bindings.createObjectBinding(
                () -> calculateMarginAmount(),
                this.totalCost, this.margin);
    }

    private BigDecimal calculateMarginAmount() {
        return this.totalCost.getValue()
                .multiply(this.margin.get())
                .divide(BigDecimal.valueOf(100))
                .setScale(2, RoundingMode.HALF_UP);
    }

    private void defineTotalCostPlusMarginBinding() {
        this.totalCostPlusMargin = Bindings.createObjectBinding(
                () -> calculateTotalCostPlusMargin(),
                        this.totalCost, this.marginAmount);
    }

    private BigDecimal calculateTotalCostPlusMargin() {
        return this.totalCost.getValue()
                .add(this.marginAmount.getValue())
                .setScale(2, RoundingMode.HALF_UP);
    }

    private void defineVatAmountBinding() {
        this.vatAmount = Bindings.createObjectBinding(
                () -> calculateVatAmount(),
                this.totalCostPlusMargin, this.vat);
    }

    private BigDecimal calculateVatAmount() {
        return this.totalCostPlusMargin.getValue()
                .multiply(this.vat.getValue())
                .divide(BigDecimal.valueOf(100))
                .setScale(2, RoundingMode.HALF_UP);
    }

    private void defineTotalBudgetItemBinding() {
        this.totalBudgetItem = Bindings.createObjectBinding(
                () -> calculateTotalBudgetItem(),
                this.totalCostPlusMargin, this.vatAmount);
    }

    private BigDecimal calculateTotalBudgetItem() {
        return this.totalCostPlusMargin.getValue()
                .add(this.vatAmount.getValue())
                .setScale(2, RoundingMode.HALF_UP);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BudgetItemUI that = (BudgetItemUI) o;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
