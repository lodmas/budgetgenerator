package com.jskno.budgetgenerator.model.ui;

import com.jskno.budgetgenerator.model.database.Item;
import com.jskno.budgetgenerator.model.database.OptionValue;
import javafx.beans.property.*;

import java.math.BigDecimal;
import java.util.Objects;

public class ItemDataTable {

    private Long id;
    private StringProperty concept;
    private StringProperty description;
    private ObjectProperty<BigDecimal> price;
    private ObjectProperty<OptionValue> units;
    private ObjectProperty<BigDecimal> margin;
    private ObjectProperty<BigDecimal> quantity;
    private ObjectProperty<BigDecimal> totalItem;

    public ItemDataTable(Item itemDB) {

        this.id = itemDB.getId();
        this.concept = new SimpleStringProperty(itemDB.getConcept());
        this.price = new SimpleObjectProperty(itemDB.getPrice());
        this.units = new SimpleObjectProperty<>(itemDB.getUnits());
        this.margin = new SimpleObjectProperty(itemDB.getMargin());
        this.quantity = new SimpleObjectProperty(1);
//        this.totalItem = new SimpleObjectProperty(calculateTotal());
    }

    public ItemDataTable(Long id, String concept, String description, BigDecimal price,
                OptionValue units, BigDecimal margin,
                 BigDecimal quantity) {

        this.id = id;
        this.concept = new SimpleStringProperty(concept);
        this.description = new SimpleStringProperty(description);
        this.price = new SimpleObjectProperty(price);
        this.units = new SimpleObjectProperty<>(units);
        this.margin = new SimpleObjectProperty(margin);
        this.quantity = new SimpleObjectProperty(quantity);
//        this.totalItem = new SimpleObjectProperty(calculateTotal());
    }

    public Long getId() {
        return id;
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

    public String getDescription() {
        return description.get();
    }

    public StringProperty descriptionProperty() {
        return description;
    }

    public void setDescription(String description) {
        this.description.set(description);
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

    public OptionValue getUnits() {
        return units.get();
    }

    public ObjectProperty<OptionValue> unitsProperty() {
        return units;
    }

    public void setUnits(OptionValue units) {
        this.units.set(units);
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

    public BigDecimal getQuantity() {
        return quantity.get();
    }

    public ObjectProperty<BigDecimal> quantityProperty() {
        return quantity;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity.set(quantity);
    }

    public BigDecimal getTotalItem() {
        return totalItem.get();
    }

    public ObjectProperty<BigDecimal> totalItemProperty() {
        return totalItem;
    }

    public void setTotalItem(BigDecimal totalItem) {
        this.totalItem.set(totalItem);
    }

//    private Double calculateTotal() {
//        return this.getPrice() * this.getQuantity() * (1 + (this.getMargin() / 100));
//    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ItemDataTable that = (ItemDataTable) o;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
