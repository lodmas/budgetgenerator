package com.jskno.budgetgenerator.model.database;

import com.jskno.budgetgenerator.model.database.base.AbstractEntity;
import javafx.beans.property.DoubleProperty;

import javax.persistence.*;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import java.math.BigDecimal;
import java.util.Objects;

@Entity
public class BudgetItem extends AbstractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Long itemId;

    private String concept;

    private String notes;

    @Column(scale=2)
    @Digits(integer = 9, fraction = 2)
    private BigDecimal price;

    @Column(scale=2)
    @Digits(integer = 9, fraction = 2)
    private BigDecimal quantity;

    @Column(scale=4)
    @Digits(integer = 9, fraction = 4)
    private BigDecimal margin;

    @Column(scale=4)
    @Digits(integer = 9, fraction = 4)
    private BigDecimal vat;

    @ManyToOne
    private OptionValue units;

    @Column(scale=2)
    @Digits(integer = 9, fraction = 2)
    private BigDecimal totalCost;

    @Column(scale=2)
    @Digits(integer = 9, fraction = 2)
    private BigDecimal marginAmount;

    @Column(scale=2)
    @Digits(integer = 9, fraction = 2)
    private BigDecimal totalCostPlusMargin;

    @Column(scale=2)
    @Digits(integer = 9, fraction = 2)
    private BigDecimal vatAmount;

    @Column(scale=2)
    @Digits(integer = 9, fraction = 2)
    private BigDecimal totalBudgetItem;

    @ManyToOne(fetch = FetchType.LAZY)
    private Budget budget;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    public String getConcept() {
        return concept;
    }

    public void setConcept(String concept) {
        this.concept = concept;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getMargin() {
        return margin;
    }

    public void setMargin(BigDecimal margin) {
        this.margin = margin;
    }

    public BigDecimal getVat() {
        return vat;
    }

    public void setVat(BigDecimal vat) {
        this.vat = vat;
    }

    public OptionValue getUnits() {
        return units;
    }

    public void setUnits(OptionValue units) {
        this.units = units;
    }

    public BigDecimal getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(BigDecimal totalCost) {
        this.totalCost = totalCost;
    }

    public BigDecimal getMarginAmount() {
        return marginAmount;
    }

    public void setMarginAmount(BigDecimal marginAmount) {
        this.marginAmount = marginAmount;
    }

    public BigDecimal getTotalCostPlusMargin() {
        return totalCostPlusMargin;
    }

    public void setTotalCostPlusMargin(BigDecimal totalCostPlusMargin) {
        this.totalCostPlusMargin = totalCostPlusMargin;
    }

    public BigDecimal getVatAmount() {
        return vatAmount;
    }

    public void setVatAmount(BigDecimal vatAmount) {
        this.vatAmount = vatAmount;
    }

    public BigDecimal getTotalBudgetItem() {
        return totalBudgetItem;
    }

    public void setTotalBudgetItem(BigDecimal totalBudgetItem) {
        this.totalBudgetItem = totalBudgetItem;
    }

    public Budget getBudget() {
        return budget;
    }

    public void setBudget(Budget budget) {
        this.budget = budget;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BudgetItem that = (BudgetItem) o;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "BudgetItem{" +
                "id=" + id +
                ", itemId=" + itemId +
                ", concept='" + concept + '\'' +
                ", notes='" + notes + '\'' +
                ", price=" + price +
                ", quantity=" + quantity +
                ", margin=" + margin +
                ", vat=" + vat +
                ", units=" + units +
                ", totalCost=" + totalCost +
                ", marginAmount=" + marginAmount +
                ", vatAmount=" + vatAmount +
                ", totalBudgetItem=" + totalBudgetItem +
                ", budget=" + budget +
                '}';
    }
}
