package com.jskno.budgetgenerator.model.ui.converters;

import javafx.scene.control.TableCell;

public class CurrencyFieldTableCell<BudgetItemUI, Double> extends TableCell<BudgetItemUI, Double> {

    @Override
    public void updateItem(Double price, boolean empty) {
        super.updateItem(price, empty);
        if (empty) {
            setText(null);
        } else {
            setText(String.format("US$%.2f", price));
        }
    }


}
