package com.jskno.budgetgenerator.model;


import com.jskno.budgetgenerator.interfaces.SampleDataService;
import com.jskno.budgetgenerator.model.database.OptionValue;
import com.jskno.budgetgenerator.model.database.OptionValueSet;
import com.jskno.budgetgenerator.model.database.OptionValueSetEnum;
import com.jskno.budgetgenerator.model.ui.ItemUI;
import com.jskno.budgetgenerator.model.ui.ItemDataTable;
import com.jskno.budgetgenerator.interfaces.ConfigurationService;
import com.jskno.budgetgenerator.model.database.Item;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class SampleDataServiceImpl implements SampleDataService {

    @Autowired
    private ConfigurationService configurationService;

    @Override
    public void fillSampleData(ObservableList<ItemUI> backingList) {

        OptionValueSet category = configurationService.getOptionValueSet(OptionValueSetEnum.CATEGORY);
        OptionValueSet units = configurationService.getOptionValueSet(OptionValueSetEnum.UNIT);

        backingList.add(new ItemUI("Waldo", configurationService.getConfiguration(category).get(1), new BigDecimal(20.50), new BigDecimal(11.30), new BigDecimal(20.50), configurationService.getConfiguration(units).get(1), "random notes 1"));
        backingList.add(new ItemUI("Herb", configurationService.getConfiguration(category).get(0), new BigDecimal(20.50), new BigDecimal(11.30), new BigDecimal(20.50), configurationService.getConfiguration(units).get(0), "random notes 2"));
        backingList.add(new ItemUI("Shawanna", configurationService.getConfiguration(category).get(0), new BigDecimal(20.50), new BigDecimal(11.30), new BigDecimal(20.50), configurationService.getConfiguration(units).get(0), "random notes 3"));
        backingList.add(new ItemUI("Flossie", configurationService.getConfiguration(category).get(1), new BigDecimal(20.50), new BigDecimal(11.30), new BigDecimal(20.50), configurationService.getConfiguration(units).get(1), "random notes 4"));
        backingList.add(new ItemUI("Magdalen", configurationService.getConfiguration(category).get(0), new BigDecimal(20.50), new BigDecimal(11.30), new BigDecimal(20.50), configurationService.getConfiguration(units).get(0), "random notes 5"));
        backingList.add(new ItemUI("Marylou", configurationService.getConfiguration(category).get(0), new BigDecimal(20.50), new BigDecimal(11.30), new BigDecimal(20.50), configurationService.getConfiguration(units).get(0), "random notes 6"));
        backingList.add(new ItemUI("Ethan", configurationService.getConfiguration(category).get(0), new BigDecimal(20.50), new BigDecimal(11.30), new BigDecimal(20.50), configurationService.getConfiguration(units).get(0), "random notes 7"));
        backingList.add(new ItemUI("Elli", configurationService.getConfiguration(category).get(0), new BigDecimal(20.50), new BigDecimal(11.30), new BigDecimal(20.50), configurationService.getConfiguration(units).get(0), "random notes 8"));
        backingList.add(new ItemUI("Andy", configurationService.getConfiguration(category).get(0), new BigDecimal(20.50), new BigDecimal(11.30), new BigDecimal(20.50), configurationService.getConfiguration(units).get(0), "random notes 9"));
        backingList.add(new ItemUI("Zenia", configurationService.getConfiguration(category).get(0), new BigDecimal(20.50), new BigDecimal(11.30), new BigDecimal(20.50), configurationService.getConfiguration(units).get(0), "random notes 10"));

        backingList.add(new ItemUI("Zenia", configurationService.getConfiguration(category).get(0), new BigDecimal(20.50), new BigDecimal(11.30), new BigDecimal(20.50), configurationService.getConfiguration(units).get(0), "random notes 10"));
        backingList.add(new ItemUI("Zenia", configurationService.getConfiguration(category).get(0), new BigDecimal(20.50), new BigDecimal(11.30), new BigDecimal(20.50), configurationService.getConfiguration(units).get(0), "random notes 10"));
        backingList.add(new ItemUI("Zenia", configurationService.getConfiguration(category).get(0), new BigDecimal(20.50), new BigDecimal(11.30), new BigDecimal(20.50), configurationService.getConfiguration(units).get(0), "random notes 10"));
        backingList.add(new ItemUI("Zenia", configurationService.getConfiguration(category).get(0), new BigDecimal(20.50), new BigDecimal(11.30), new BigDecimal(20.50), configurationService.getConfiguration(units).get(0), "random notes 10"));
        backingList.add(new ItemUI("Zenia", configurationService.getConfiguration(category).get(0), new BigDecimal(20.50), new BigDecimal(11.30), new BigDecimal(20.50), configurationService.getConfiguration(units).get(0), "random notes 10"));
        backingList.add(new ItemUI("Zenia", configurationService.getConfiguration(category).get(0), new BigDecimal(20.50), new BigDecimal(11.30), new BigDecimal(20.50), configurationService.getConfiguration(units).get(0), "random notes 10"));
        backingList.add(new ItemUI("Zenia", configurationService.getConfiguration(category).get(0), new BigDecimal(20.50), new BigDecimal(11.30), new BigDecimal(20.50), configurationService.getConfiguration(units).get(0), "random notes 10"));
        backingList.add(new ItemUI("Zenia", configurationService.getConfiguration(category).get(0), new BigDecimal(20.50), new BigDecimal(11.30), new BigDecimal(20.50), configurationService.getConfiguration(units).get(0), "random notes 10"));
        backingList.add(new ItemUI("Zenia", configurationService.getConfiguration(category).get(0), new BigDecimal(20.50), new BigDecimal(11.30), new BigDecimal(20.50), configurationService.getConfiguration(units).get(0), "random notes 10"));
        backingList.add(new ItemUI("Zenia", configurationService.getConfiguration(category).get(0), new BigDecimal(20.50), new BigDecimal(11.30), new BigDecimal(20.50), configurationService.getConfiguration(units).get(0), "random notes 10"));
        backingList.add(new ItemUI("Zenia", configurationService.getConfiguration(category).get(0), new BigDecimal(20.50), new BigDecimal(11.30), new BigDecimal(20.50), configurationService.getConfiguration(units).get(0), "random notes 10"));
    }

    @Override
    public List<ItemUI> fillSampleData(List<ItemUI> backingList) {

        OptionValueSet category = configurationService.getOptionValueSet(OptionValueSetEnum.CATEGORY);
        OptionValueSet units = configurationService.getOptionValueSet(OptionValueSetEnum.UNIT);

        backingList.add(new ItemUI("Waldo", configurationService.getConfiguration(category).get(1), new BigDecimal(20.50), new BigDecimal(11.30), new BigDecimal(20.50), configurationService.getConfiguration(units).get(1), "random notes 1"));
        backingList.add(new ItemUI("Herb", configurationService.getConfiguration(category).get(0), new BigDecimal(20.50), new BigDecimal(11.30), new BigDecimal(20.50), configurationService.getConfiguration(units).get(0), "random notes 2"));
        backingList.add(new ItemUI("Shawanna", configurationService.getConfiguration(category).get(0), new BigDecimal(20.50), new BigDecimal(11.30), new BigDecimal(20.50), configurationService.getConfiguration(units).get(0), "random notes 3"));
        backingList.add(new ItemUI("Flossie", configurationService.getConfiguration(category).get(1), new BigDecimal(20.50), new BigDecimal(11.30), new BigDecimal(20.50), configurationService.getConfiguration(units).get(1), "random notes 4"));
        backingList.add(new ItemUI("Magdalen", configurationService.getConfiguration(category).get(0), new BigDecimal(20.50), new BigDecimal(11.30), new BigDecimal(20.50), configurationService.getConfiguration(units).get(0), "random notes 5"));
        backingList.add(new ItemUI("Marylou", configurationService.getConfiguration(category).get(0), new BigDecimal(20.50), new BigDecimal(11.30), new BigDecimal(20.50), configurationService.getConfiguration(units).get(0), "random notes 6"));
        backingList.add(new ItemUI("Ethan", configurationService.getConfiguration(category).get(0), new BigDecimal(20.50), new BigDecimal(11.30), new BigDecimal(20.50), configurationService.getConfiguration(units).get(0), "random notes 7"));
        backingList.add(new ItemUI("Elli", configurationService.getConfiguration(category).get(0), new BigDecimal(20.50), new BigDecimal(11.30), new BigDecimal(20.50), configurationService.getConfiguration(units).get(0), "random notes 8"));
        backingList.add(new ItemUI("Andy", configurationService.getConfiguration(category).get(0), new BigDecimal(20.50), new BigDecimal(11.30), new BigDecimal(20.50), configurationService.getConfiguration(units).get(0), "random notes 9"));
        backingList.add(new ItemUI("Zenia", configurationService.getConfiguration(category).get(0), new BigDecimal(20.50), new BigDecimal(11.30), new BigDecimal(20.50), configurationService.getConfiguration(units).get(0), "random notes 10"));

        backingList.add(new ItemUI("Zenia", configurationService.getConfiguration(category).get(0), new BigDecimal(20.50), new BigDecimal(11.30), new BigDecimal(20.50), configurationService.getConfiguration(units).get(0), "random notes 10"));
        backingList.add(new ItemUI("Zenia", configurationService.getConfiguration(category).get(0), new BigDecimal(20.50), new BigDecimal(11.30), new BigDecimal(20.50), configurationService.getConfiguration(units).get(0), "random notes 10"));
        backingList.add(new ItemUI("Zenia", configurationService.getConfiguration(category).get(0), new BigDecimal(20.50), new BigDecimal(11.30), new BigDecimal(20.50), configurationService.getConfiguration(units).get(0), "random notes 10"));
        backingList.add(new ItemUI("Zenia", configurationService.getConfiguration(category).get(0), new BigDecimal(20.50), new BigDecimal(11.30), new BigDecimal(20.50), configurationService.getConfiguration(units).get(0), "random notes 10"));
        backingList.add(new ItemUI("Zenia", configurationService.getConfiguration(category).get(0), new BigDecimal(20.50), new BigDecimal(11.30), new BigDecimal(20.50), configurationService.getConfiguration(units).get(0), "random notes 10"));
        backingList.add(new ItemUI("Zenia", configurationService.getConfiguration(category).get(0), new BigDecimal(20.50), new BigDecimal(11.30), new BigDecimal(20.50), configurationService.getConfiguration(units).get(0), "random notes 10"));
        backingList.add(new ItemUI("Zenia", configurationService.getConfiguration(category).get(0), new BigDecimal(20.50), new BigDecimal(11.30), new BigDecimal(20.50), configurationService.getConfiguration(units).get(0), "random notes 10"));
        backingList.add(new ItemUI("Zenia", configurationService.getConfiguration(category).get(0), new BigDecimal(20.50), new BigDecimal(11.30), new BigDecimal(20.50), configurationService.getConfiguration(units).get(0), "random notes 10"));
        backingList.add(new ItemUI("Zenia", configurationService.getConfiguration(category).get(0), new BigDecimal(20.50), new BigDecimal(11.30), new BigDecimal(20.50), configurationService.getConfiguration(units).get(0), "random notes 10"));
        backingList.add(new ItemUI("Zenia", configurationService.getConfiguration(category).get(0), new BigDecimal(20.50), new BigDecimal(11.30), new BigDecimal(20.50), configurationService.getConfiguration(units).get(0), "random notes 10"));
        backingList.add(new ItemUI("Zenia", configurationService.getConfiguration(category).get(0), new BigDecimal(20.50), new BigDecimal(11.30), new BigDecimal(20.50), configurationService.getConfiguration(units).get(0), "random notes 10"));

        return backingList;
    }

    @Override
    public ObservableList<ItemDataTable> loadDataFromStoredBudget() {

        List<OptionValue> unitsOptions = configurationService
                .getConfiguration(OptionValueSetEnum.UNIT);
        ObservableList<ItemDataTable> data =
                FXCollections.observableArrayList();

        data.add(new ItemDataTable(1L, "Ladrillo del 1",
                "Ladrillo rojo", new BigDecimal(10.30),
                unitsOptions.get(0), new BigDecimal(11.30), new BigDecimal(2.0)));
        data.add(new ItemDataTable(9L, "Ladrillo del 9",
                "Ladrillo rojo", new BigDecimal(10.30),
                unitsOptions.get(0), new BigDecimal(11.30),new BigDecimal(2.0)));
        data.add(new ItemDataTable(3L, "Ladrillo del 3",
                "Ladrillo rojo", new BigDecimal(10.30),
                unitsOptions.get(0), new BigDecimal(11.30),new BigDecimal(2.0)));
        data.add(new ItemDataTable(4L, "Ladrillo del 4",
                "Ladrillo rojo", new BigDecimal(10.30),
                unitsOptions.get(0), new BigDecimal(11.30),new BigDecimal(2.0)));
        data.add(new ItemDataTable(7L, "Ladrillo del 7",
                "Ladrillo rojo", new BigDecimal(10.30),
                unitsOptions.get(0), new BigDecimal(11.30),new BigDecimal(2.0)));

        return data;
    }

    @Override
    public ObservableList<ItemDataTable> loadDataFromItemsDBSelection() {

        ObservableList<ItemDataTable> data =
                FXCollections.observableArrayList();

        List<Item> itemsDB = loadItemsDBData();
        itemsDB.forEach(itemDB -> data.add(new ItemDataTable(itemDB)));
        return data;

    }

    public List<Item> loadItemsDBData() {

        List<OptionValue> unitsOptions = configurationService
                .getConfiguration(OptionValueSetEnum.UNIT);
        List<OptionValue> categoryOptions = configurationService
                .getConfiguration(OptionValueSetEnum.CATEGORY);

        List<Item> data =new ArrayList<>();

        data.add(new Item(1L, "Ladrillo del 1",
                "Ladrillo rojo", categoryOptions.get(0), new BigDecimal(10.30), new BigDecimal(11.30), new BigDecimal(11.30),
                unitsOptions.get(0), "Some notes"));
        data.add(new Item(2L, "Ladrillo del 2",
                "Ladrillo rojo", categoryOptions.get(0), new BigDecimal(20.50), new BigDecimal(11.30), new BigDecimal(11.30),
                unitsOptions.get(0), "Some notes"));
        data.add(new Item(3L, "Ladrillo del 3",
                "Ladrillo rojo", categoryOptions.get(0), new BigDecimal(10.30), new BigDecimal(11.30), new BigDecimal(11.30),
                unitsOptions.get(0), "Some notes"));
        data.add(new Item(4L, "Ladrillo del 4",
                "Ladrillo rojo", categoryOptions.get(0), new BigDecimal(1.30), new BigDecimal(11.30), new BigDecimal(11.30),
                unitsOptions.get(0), "Some notes"));
        data.add(new Item(5L, "Ladrillo del 5",
                "Ladrillo rojo", categoryOptions.get(0), new BigDecimal(10.30), new BigDecimal(11.30), new BigDecimal(11.30),
                unitsOptions.get(0), "Some notes"));
        data.add(new Item(6L, "Ladrillo del 6",
                "Ladrillo rojo", categoryOptions.get(0), new BigDecimal(12.30), new BigDecimal(11.30), new BigDecimal(15.0),
                unitsOptions.get(0), "Some notes"));
        data.add(new Item(7L, "Ladrillo del 7",
                "Ladrillo rojo", categoryOptions.get(0), new BigDecimal(30.30), new BigDecimal(11.30), new BigDecimal(10.50),
                unitsOptions.get(0), "Some notes"));
        data.add(new Item(8L, "Ladrillo del 8",
                "Ladrillo rojo", categoryOptions.get(0), new BigDecimal(10.30), new BigDecimal(11.30), new BigDecimal(11.30),
                unitsOptions.get(0), "Some notes"));
        data.add(new Item(9L, "Ladrillo del 9",
                "Ladrillo rojo", categoryOptions.get(0), new BigDecimal(10.30), new BigDecimal(11.30), new BigDecimal(11.30),
                unitsOptions.get(0), "Some notes"));

        return data;
    }



/*
Glenn Marti
Waldo Soller
Herb Dinapoli
Shawanna Goehring
Flossie Slack
Magdalen Meadors
Marylou Berube
Ethan Nieto
Elli Combes
Andy Toupin
Zenia Linwood
Alan Mckeithan
Kattie Mellott
Benito Kearns
Lloyd Cundiff
Karleen Westrich
Jada Perrotta
Teofila Holbert
Moira Heart
Mitsuko Earwood
*/

}
