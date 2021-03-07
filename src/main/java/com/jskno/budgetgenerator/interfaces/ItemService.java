package com.jskno.budgetgenerator.interfaces;

import com.jskno.budgetgenerator.model.database.Item;
import com.jskno.budgetgenerator.model.database.OptionValue;
import com.jskno.budgetgenerator.model.ui.ItemUI;
import com.jskno.budgetgenerator.model.ui.OptionValueUI;
import javafx.collections.ObservableList;

import java.util.List;

public interface ItemService {

    ObservableList<ItemUI> loadObservableItemsList();

    List<ItemUI> loadBudgetList();

    Item saveItem(Item item);

    Item updateItem(Item item);

    void removeItemById(Long itemId);

    Item getOptionValueById(Long id);

    ItemUI getOptionValueUIById(Long id);
}
