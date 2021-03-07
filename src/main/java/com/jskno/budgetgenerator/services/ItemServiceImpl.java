package com.jskno.budgetgenerator.services;

import com.jskno.budgetgenerator.interfaces.ItemService;
import com.jskno.budgetgenerator.model.database.Item;
import com.jskno.budgetgenerator.model.ui.ItemUI;
import com.jskno.budgetgenerator.repository.ItemRepository;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ItemServiceImpl implements ItemService {

    @Autowired
    private ItemRepository itemRepository;

    public ItemServiceImpl() {
    }

    @Override
    public ObservableList<ItemUI> loadObservableItemsList() {
        List<Item> items = itemRepository.findAll();
        ObservableList<ItemUI> itemsUI = FXCollections.observableArrayList();
        items.forEach(item -> itemsUI.add(new ItemUI(item)));
        return itemsUI;
    }

    @Override
    public List<ItemUI> loadBudgetList() {
        List<Item> items = itemRepository.findAll();
        List<ItemUI> itemsUI = new ArrayList<>();
        items.forEach(item -> itemsUI.add(new ItemUI(item)));
        return itemsUI;
    }

    @Override
    public Item saveItem(Item item) {
        return itemRepository.save(item);
    }

    @Override
    public Item updateItem(Item item) {
        return itemRepository.save(item);
    }


    @Override
    public void removeItemById(Long itemId) {
        itemRepository.deleteById(itemId);
    }

    @Override
    public Item getOptionValueById(Long id) {
        return itemRepository.findById(id).orElseThrow(() -> new IllegalArgumentException());
    }

    @Override
    public ItemUI getOptionValueUIById(Long id) {
        return new ItemUI(getOptionValueById(id));
    }

}
