package com.jskno.budgetgenerator.model.database;

import com.jskno.budgetgenerator.model.database.base.AbstractEntity;

import javax.persistence.*;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import java.math.BigDecimal;
import java.util.Objects;

@Entity
public class Item extends AbstractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String concept;

    @ManyToOne
    private OptionValue category;

    @Column(scale=2)
    @Digits(integer = 9, fraction = 2)
    private BigDecimal price;

    @Column(scale=4)
    @Digits(integer = 9, fraction = 4)
    private BigDecimal vat;

    @Column(scale=4)
    @Digits(integer = 9, fraction = 4)
    private BigDecimal margin;

    @ManyToOne
    private OptionValue units;

    private String notes;

    public Item() {
    }

    public Item(Long id, String concept, String description, OptionValue category,
                BigDecimal price, BigDecimal vat, BigDecimal margin, OptionValue units,
                String notes) {

        this.id = id;
        this.concept = concept;
        this.category = category;
        this.price = price;
        this.vat = vat;
        this.margin = margin;
        this.units = units;
        this.notes = notes;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getConcept() {
        return concept;
    }

    public void setConcept(String concept) {
        this.concept = concept;
    }

    public OptionValue getCategory() {
        return category;
    }

    public void setCategory(OptionValue category) {
        this.category = category;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getVat() {
        return vat;
    }

    public void setVat(BigDecimal vat) {
        this.vat = vat;
    }

    public BigDecimal getMargin() {
        return margin;
    }

    public void setMargin(BigDecimal margin) {
        this.margin = margin;
    }

    public OptionValue getUnits() {
        return units;
    }

    public void setUnits(OptionValue units) {
        this.units = units;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Item itemDB = (Item) o;
        return id.equals(itemDB.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return getConcept();
    }

}
