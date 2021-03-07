package com.jskno.budgetgenerator.interfaces;

import com.jskno.budgetgenerator.model.ui.ItemUI;
import com.jskno.budgetgenerator.model.ui.ItemDataTable;
import javafx.collections.ObservableList;

import java.util.List;

public interface SampleDataService {

    List<ItemUI> fillSampleData(List<ItemUI> backingList);

    ObservableList<ItemDataTable> loadDataFromStoredBudget();

    ObservableList<ItemDataTable> loadDataFromItemsDBSelection();

    void fillSampleData(ObservableList<ItemUI> backingList);
}
