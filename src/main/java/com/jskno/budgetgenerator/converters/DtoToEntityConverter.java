package com.jskno.budgetgenerator.converters;

import com.jskno.budgetgenerator.model.database.*;
import com.jskno.budgetgenerator.model.ui.*;
import com.jskno.budgetgenerator.utils.Constants;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class DtoToEntityConverter {

    public static Budget getBudget(BudgetUI budgetUI) {
        Budget budget = new Budget();
        budget.setId(budgetUI.getId());
        budget.setName(budgetUI.getName());
        budget.setTotalCost(budgetUI.getTotalCost());
        budget.setMarginPercent(budgetUI.getMarginPercent().divide(Constants.ONE_HUNDRED)
                .setScale(4, RoundingMode.HALF_UP));
        budget.setTotalMargin(budgetUI.getTotalMargin());
        budget.setTotalCostPlusMargin(budgetUI.getTotalCostPlusMargin());
        budget.setTotalVat(budgetUI.getTotalVat());
        budget.setTotal(budgetUI.getTotal());
        budgetUI.getBudgetItems().forEach(budgetItemUI -> budget.addBudgetItem(getBudgetItem(budgetItemUI)));

        return budget;
    }

    private static BudgetItem getBudgetItem(BudgetItemUI budgetItemUI) {
        BudgetItem budgetItem = new BudgetItem();
        budgetItem.setId(budgetItemUI.getId());
        budgetItem.setItemId(budgetItemUI.getItemId());
        budgetItem.setConcept(budgetItemUI.getConcept());
        budgetItem.setNotes(budgetItemUI.getNotes());
        budgetItem.setPrice(budgetItemUI.getPrice());
        budgetItem.setUnits(budgetItemUI.getUnits());
        budgetItem.setQuantity(budgetItemUI.getQuantity());
        budgetItem.setMargin(budgetItemUI.getMargin().divide(Constants.ONE_HUNDRED
                .setScale(4, RoundingMode.HALF_UP)));
        budgetItem.setVat(budgetItemUI.getVat().divide(Constants.ONE_HUNDRED
                .setScale(4, RoundingMode.HALF_UP)));
        budgetItem.setMarginAmount(budgetItemUI.getMarginAmount());
        budgetItem.setTotalCostPlusMargin(budgetItemUI.getTotalCostPlusMargin());
        budgetItem.setVatAmount(budgetItemUI.getVatAmount());
        budgetItem.setTotalCost(budgetItemUI.getTotalCost());
        budgetItem.setTotalBudgetItem(budgetItemUI.getTotalBudgetItem());
        return budgetItem;
    }

    public static Item getItem(ItemUI itemUI) {
        Item item = new Item();
        item.setId(itemUI.getId());
        item.setConcept(itemUI.getConcept());
        item.setNotes(itemUI.getNotes());
        item.setNotes(itemUI.getNotes());
        item.setCategory(itemUI.getCategory());
        item.setPrice(itemUI.getPrice());
        item.setUnits(itemUI.getUnits());
        item.setVat(itemUI.getVat().divide(Constants.ONE_HUNDRED
                .setScale(4, RoundingMode.HALF_UP)));
        item.setMargin(itemUI.getMargin().divide(Constants.ONE_HUNDRED
                .setScale(4, RoundingMode.HALF_UP)));
        return item;
    }


    public static OptionValue getOptionValue(OptionValueUI optionValueUI) {
        OptionValue optionValue = new OptionValue();
        optionValue.setId(optionValueUI.getId());
        optionValue.setValue(optionValueUI.getValue());
        optionValue.setLabel(optionValueUI.getLabel());
        optionValue.setPosition(optionValueUI.getPosition());
        optionValue.setDisable(optionValueUI.isDisable());
        optionValue.setOptionValueSet(getOptionValueSet(optionValueUI.getOptionValueSetUI()));
        return optionValue;
    }

    public static OptionValueSet getOptionValueSet(OptionValueSetUI optionValueSetUI) {
        OptionValueSet optionValueSet = new OptionValueSet();
        optionValueSet.setId(optionValueSetUI.getId());
        optionValueSet.setValue(optionValueSetUI.getValue());
        optionValueSet.setLabel(optionValueSetUI.getLabel());
        return optionValueSet;
    }

}
