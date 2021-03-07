package com.jskno.budgetgenerator.model.ui;

import com.jskno.budgetgenerator.model.database.Item;
import com.jskno.budgetgenerator.model.database.OptionValue;
import com.jskno.budgetgenerator.utils.Constants;
import javafx.beans.Observable;
import javafx.beans.property.*;
import javafx.util.Callback;

import java.math.BigDecimal;
import java.util.Objects;

public class ItemUI {

    private final LongProperty id = new SimpleLongProperty();
    private final StringProperty concept = new SimpleStringProperty(this, "concept", "");
    private OptionValue category;
    private final ObjectProperty<BigDecimal> price = new SimpleObjectProperty(this, "price", 0);
    private OptionValue units;
    private ObjectProperty<BigDecimal> vat = new SimpleObjectProperty(this, "vat", 0);
    private final ObjectProperty<BigDecimal> margin = new SimpleObjectProperty(this, "margin", 0);
    private final StringProperty notes = new SimpleStringProperty(this, "notes", "sample notes");

    public ItemUI() {
    }

    public ItemUI(String concept, OptionValue category, BigDecimal price, BigDecimal vat,
              BigDecimal margin, OptionValue units, String notes) {
        this.concept.set(concept);
        this.category = category;
        this.price.set(price);
        this.vat.set(vat);
        this.margin.set(margin);
        this.units = units;
        this.notes.set(notes);
    }

    public ItemUI(Item item) {
        this.id.set(item.getId());
        this.concept.set(item.getConcept());
        this.category = item.getCategory();
        this.price.set(item.getPrice());
        this.vat.set(item.getVat().multiply(Constants.ONE_HUNDRED).setScale(2));
        this.margin.set(item.getMargin().multiply(Constants.ONE_HUNDRED).setScale(2));
        this.units = item.getUnits();
        this.notes.set(item.getNotes());
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

    public String getConcept() {
        return concept.get();
    }

    public StringProperty conceptProperty() {
        return concept;
    }

    public void setConcept(String concept) {
        this.concept.set(concept);
    }

    public OptionValue getCategory() {
        return category;
    }

    public void setCategory(OptionValue category) {
        this.category = category;
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

    public BigDecimal getVat() {
        return vat.get();
    }

    public ObjectProperty<BigDecimal> vatProperty() {
        return vat;
    }

    public void setVat(BigDecimal vat) {
        this.vat.set(vat);
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

    public OptionValue getUnits() {
        return units;
    }

    public void setUnits(OptionValue units) {
        this.units = units;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ItemUI itemUI = (ItemUI) o;
        return id.equals(itemUI.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return concept.get();
    }

    public static Callback<ItemUI, Observable[]> extractor = p -> new Observable[]
            {p.conceptProperty()};
}
