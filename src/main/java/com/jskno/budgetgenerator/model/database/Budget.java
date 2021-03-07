package com.jskno.budgetgenerator.model.database;

import com.jskno.budgetgenerator.model.database.base.AbstractEntity;

import javax.persistence.*;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Budget extends AbstractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    @Column(scale=2)
    @Digits(integer = 9, fraction = 2)
    private BigDecimal totalCost;

    @Column(scale=4)
    @Digits(integer = 9, fraction = 4)
    private BigDecimal marginPercent;

    @Column(scale=2)
    @Digits(integer = 9, fraction = 2)
    private BigDecimal totalMargin;

    @Column(scale=2)
    @Digits(integer = 9, fraction = 2)
    private BigDecimal totalCostPlusMargin;

    @Column(scale=2)
    @Digits(integer = 9, fraction = 2)
    private BigDecimal totalVat;

    @Column(scale=2)
    @Digits(integer = 9, fraction = 2)
    private BigDecimal total;

    @OneToMany(
            mappedBy = "budget",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.EAGER
    )
    private List<BudgetItem> budgetItems = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(BigDecimal totalCost) {
        this.totalCost = totalCost;
    }

    public BigDecimal getMarginPercent() {
        return marginPercent;
    }

    public void setMarginPercent(BigDecimal marginPercent) {
        this.marginPercent = marginPercent;
    }

    public BigDecimal getTotalMargin() {
        return totalMargin;
    }

    public void setTotalMargin(BigDecimal totalMargin) {
        this.totalMargin = totalMargin;
    }

    public BigDecimal getTotalCostPlusMargin() {
        return totalCostPlusMargin;
    }

    public void setTotalCostPlusMargin(BigDecimal totalCostPlusMargin) {
        this.totalCostPlusMargin = totalCostPlusMargin;
    }

    public BigDecimal getTotalVat() {
        return totalVat;
    }

    public void setTotalVat(BigDecimal totalVat) {
        this.totalVat = totalVat;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public List<BudgetItem> getBudgetItems() {
        return budgetItems;
    }

    public void setBudgetItems(List<BudgetItem> budgetItems) {
        budgetItems.forEach(this::addBudgetItem);
    }

    public void addBudgetItem(BudgetItem budgetItem) {
        budgetItems.add(budgetItem);
        budgetItem.setBudget(this);
    }

    public void removeBudgetItem(BudgetItem budgetItem) {
        budgetItems.remove(budgetItem);
        budgetItem.setBudget(null);
    }
}
