package com.jskno.budgetgenerator.controllers.item;

import com.jskno.budgetgenerator.interfaces.ChoiceBoxService;
import com.jskno.budgetgenerator.interfaces.ItemService;
import com.jskno.budgetgenerator.model.database.OptionValue;
import com.jskno.budgetgenerator.model.database.OptionValueSetEnum;
import com.jskno.budgetgenerator.model.ui.ItemUI;
import com.jskno.budgetgenerator.react.OptionValuesChangedEvent;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;

@Component
public class ItemsListController {

    private ChoiceBoxService choiceBoxService;
    private ItemService itemService;
    private NewItemController newItemController;
    private UpdateItemController updateItemController;

    @Autowired
    public ItemsListController(ChoiceBoxService choiceBoxService, ItemService itemService,
                   NewItemController newItemController, UpdateItemController updateItemController) {

        this.choiceBoxService = choiceBoxService;
        this.itemService = itemService;
        this.newItemController = newItemController;
        this.updateItemController = updateItemController;
    }

    private TextField conceptTextField;
    private TextArea notesTextArea;
    private ChoiceBox<OptionValue> categoryChoiceBox;
    private TextField priceTextField;
    private ChoiceBox<OptionValue> unitsChoiceBox;
    private TextField vatTextField;
    private TextField marginTextField;
    private Button removeButton;
    private Button newItemButton;
    private Button updateButton;
    private ListView<ItemUI> listView;

    // Observable objects returned by extractor (applied to each list element) are listened for changes and
    // transformed into "update" change of ListChangeListener.
    private final ObservableList<ItemUI> itemsList = FXCollections.observableArrayList(ItemUI.extractor);

    private ItemUI selectedItem;
    private final BooleanProperty modifiedProperty = new SimpleBooleanProperty(false);
    private ChangeListener<ItemUI> itemChangeListener;


    public Node start() {
        Parent root = getRoot();
        init();
        return root;
    }

    private Parent getRoot() {

        AnchorPane mainAnchorPane = new AnchorPane();
//        mainAnchorPane.prefWidth(600);
//        mainAnchorPane.prefHeight(400);
//        mainAnchorPane.setPadding(new Insets(20));

        SplitPane splitPane = new SplitPane();
//        splitPane.prefWidth(600);
//        splitPane.prefHeight(400);
        splitPane.setDividerPositions(0.30);
        splitPane.setPadding(new Insets(20));

        AnchorPane listAnchorPane = buildItemsList();
        AnchorPane formAnchorPane = buildItemForm();

        splitPane.getItems().add(listAnchorPane);
        splitPane.getItems().add(formAnchorPane);

        mainAnchorPane.getChildren().add(splitPane);
        mainAnchorPane.setTopAnchor(splitPane, 30.0);
        mainAnchorPane.setLeftAnchor(splitPane, 20.0);

        return mainAnchorPane;
    }

    public AnchorPane buildItemsList() {
        AnchorPane listAnchorPane = new AnchorPane();
//        listAnchorPane.setPrefWidth(160);
//        listAnchorPane.setPrefHeight(100);

        listView = new ListView<>();
//        listView.prefWidth(160);
//        listView.prefHeight(100);

//        itemsList = FXCollections.observableArrayList(Item.extractor);
//        itemsList.addAll(itemService.getItems());
//        listView.setItems(itemsList);

        listAnchorPane.getChildren().add(listView);

        return listAnchorPane;
    }

    public AnchorPane buildItemForm() {
        AnchorPane formAnchorPane = new AnchorPane();

        GridPane mainGridPane = new GridPane();
        mainGridPane.setPrefWidth(650);
        mainGridPane.setPrefHeight(350);

        GridPane topGridPane = buildTopGrid();
        GridPane middleGridPane = buildMiddleGrid();
        ButtonBar buttonBar = buildButtonBar();

        mainGridPane.addRow(0, topGridPane);
        mainGridPane.addRow(1, middleGridPane);
        mainGridPane.addRow(2, buttonBar);
        mainGridPane.setValignment(buttonBar, VPos.BOTTOM);

        formAnchorPane.getChildren().add(mainGridPane);

        return formAnchorPane;
    }

    public GridPane buildTopGrid() {
        GridPane topGridPane = new GridPane();
        topGridPane.setPadding(new Insets(10));
        topGridPane.setHgap(10);
        topGridPane.setVgap(10);

        Label conceptLabel = new Label("Concepto");
        conceptTextField = new TextField();
        conceptTextField.setDisable(true);
        conceptTextField.setStyle("-fx-opacity: 0.6;");

        Label categoryLabel = new Label("Categoría");
        categoryChoiceBox = new ChoiceBox<>();
        categoryChoiceBox.prefWidth(280);
        categoryChoiceBox.prefHeight(25);
        categoryChoiceBox.setDisable(true);
        categoryChoiceBox.setStyle("-fx-opacity: 0.6;");

        Label notesLabel = new Label("Notes");
        notesTextArea = new TextArea();
        notesTextArea.setPrefWidth(200);
        notesTextArea.setPrefHeight(220);
        notesTextArea.setDisable(true);
        notesTextArea.setStyle("-fx-opacity: 0.6;");

        topGridPane.setHalignment(conceptLabel, HPos.LEFT);
        topGridPane.setHalignment(categoryLabel, HPos.LEFT);
        topGridPane.setHalignment(notesLabel, HPos.LEFT);

        // Set column widths
        ColumnConstraints col01 = new ColumnConstraints();
        col01.setPercentWidth(35);
        ColumnConstraints col02 = new ColumnConstraints();
        col02.setPercentWidth(65);
        topGridPane.getColumnConstraints().addAll(col01, col02);

        topGridPane.addRow(0, conceptLabel, conceptTextField);
        topGridPane.addRow(1, categoryLabel, categoryChoiceBox);
        topGridPane.addRow(2, notesLabel, notesTextArea);

        return topGridPane;
    }

    public GridPane buildMiddleGrid() {
        GridPane middleGridPane = new GridPane();
        middleGridPane.setPadding(new Insets(10));
        middleGridPane.setHgap(10);
        middleGridPane.setVgap(10);

        Label priceLabel = new Label("Precio");
        priceTextField = new TextField();
        priceTextField.setDisable(true);
        priceTextField.setStyle("-fx-opacity: 0.6;");

        Label unitsLabel = new Label("Unidades");
        unitsChoiceBox = new ChoiceBox<>();
        unitsChoiceBox.setDisable(true);
        unitsChoiceBox.setStyle("-fx-opacity: 0.6;");

        Label marginLabel = new Label("Beneficio %");
        marginTextField = new TextField();
        marginTextField.setDisable(true);
        marginTextField.setStyle("-fx-opacity: 0.6;");

        Label vatLabel = new Label("IVA %");
        vatTextField = new TextField();
        vatTextField.setDisable(true);
        vatTextField.setStyle("-fx-opacity: 0.6;");


        // Set alignments and spanning
//        middleGridPane.setHalignment(priceLabel, HPos.LEFT);
//        middleGridPane.setHalignment(unitsLabel, HPos.LEFT);
//        middleGridPane.setHalignment(marginLabel, HPos.LEFT);
//        middleGridPane.setColumnSpan(priceTextField, 2);
//        middleGridPane.setColumnSpan(unitsChoiceBox, 2);
//        middleGridPane.setColumnSpan(marginTextField, 2);

        // Set column widths
        ColumnConstraints col1 = new ColumnConstraints();
        col1.setPercentWidth(8);
        ColumnConstraints col2 = new ColumnConstraints();
        col2.setPercentWidth(18);
        ColumnConstraints col3 = new ColumnConstraints();
        col3.setPercentWidth(12);
        ColumnConstraints col4 = new ColumnConstraints();
        col4.setPercentWidth(20);
        ColumnConstraints col5 = new ColumnConstraints();
        col5.setPercentWidth(14);
        ColumnConstraints col6 = new ColumnConstraints();
        col6.setPercentWidth(12);
        ColumnConstraints col7 = new ColumnConstraints();
        col7.setPercentWidth(6);
        ColumnConstraints col8 = new ColumnConstraints();
        col8.setPercentWidth(10);
        middleGridPane.getColumnConstraints().addAll(col1, col2, col3, col4, col5, col6, col7, col8);

        middleGridPane.addRow(0,
                priceLabel, priceTextField,
                unitsLabel, unitsChoiceBox,
                marginLabel, marginTextField,
                vatLabel, vatTextField);

        return middleGridPane;
    }

    public ButtonBar buildButtonBar() {
        ButtonBar buttonBar = new ButtonBar();

//        buttonBar.setPrefWidth(353);
//        buttonBar.setPrefHeight(75);
        buttonBar.setPadding(new Insets(30));
        newItemButton = new Button("Nuevo");
        newItemButton.setOnAction(actionEvent -> showNewItemScreen(actionEvent));
        updateButton = new Button("Actualizar");
        updateButton.setOnAction(actionEvent -> showUpdateItemScreen(actionEvent));
        removeButton = new Button("Borrar");
        removeButton.setOnAction(actionEvent -> removeButtonAction(actionEvent));
        buttonBar.getButtons().addAll(newItemButton, updateButton, removeButton);

        return buttonBar;
    }


    public void init() {

        initOptionValuesDropBox();
        loadOptionValueDropBox();

        // Disable the Remove/Edit buttons if nothing is selected in the ListView control
        removeButton.disableProperty().bind(listView.getSelectionModel().selectedItemProperty().isNull());
        updateButton.disableProperty().bind(
                listView.getSelectionModel().selectedItemProperty().isNull());
//                .or(modifiedProperty.not())
//                .or(conceptTextField.textProperty().isEmpty()
//                .or(descriptionTextField.textProperty().isEmpty())));
//        createButton.disableProperty().bind(listView.getSelectionModel().selectedItemProperty().isNotNull()
//                .or(conceptTextField.textProperty().isEmpty()
//                        .or(descriptionTextField.textProperty().isEmpty())));

//        SampleData.fillSampleData(itemsList);
        itemsList.addAll(itemService.loadBudgetList());

        // Use a sorted list; sort by lastname; then by firstname
        SortedList<ItemUI> sortedList = new SortedList<>(itemsList);

        // sort by lastname first, then by firstname; ignore notes
        sortedList.setComparator((p1, p2) -> {
            return p1.getConcept().compareToIgnoreCase(p2.getConcept());
        });
        listView.setItems(sortedList);

        listView.getSelectionModel().selectedItemProperty().addListener(
                itemChangeListener = (observable, oldValue, newValue) -> {
                    System.out.println("Selected item: " + newValue);
                    // newValue can be null if nothing is selected
                    selectedItem = newValue;
                    modifiedProperty.set(false);
                    if (newValue != null) {
                        // Populate controls with selected Person
                        conceptTextField.setText(selectedItem.getConcept());
                        notesTextArea.setText(selectedItem.getNotes());
                        categoryChoiceBox.setValue(selectedItem.getCategory());
                        unitsChoiceBox.setValue(selectedItem.getUnits());
                        priceTextField.setText(String.valueOf(selectedItem.getPrice()));
                        vatTextField.setText(String.valueOf(selectedItem.getVat()));
                        marginTextField.setText(String.valueOf(selectedItem.getMargin()));
                    } else {
                        conceptTextField.setText("");
                        notesTextArea.setText("");
                        categoryChoiceBox.setValue(null);
                        unitsChoiceBox.setValue(null);
                    }
                });

        // Pre-select the first item
        listView.getSelectionModel().selectFirst();

    }

    protected void initOptionValuesDropBox() {
        choiceBoxService.initChoiceBox(unitsChoiceBox);
        choiceBoxService.initChoiceBox(categoryChoiceBox);
    }

    private void loadOptionValueDropBox() {
        choiceBoxService.fillChoiceBox(unitsChoiceBox, OptionValueSetEnum.UNIT);
        choiceBoxService.fillChoiceBox(categoryChoiceBox, OptionValueSetEnum.CATEGORY);
    }

    private void removeButtonAction(ActionEvent actionEvent) {
        System.out.println("Remove " + selectedItem);
        try {
            itemService.removeItemById(selectedItem.getId());
            loadAfterRemove(selectedItem);
        } catch (DataIntegrityViolationException ex) {

        }
    }

    private void loadAfterRemove(ItemUI selectedItem) {
        itemsList.clear();
        itemsList.addAll(itemService.loadBudgetList());

        // Use a sorted list; sort by lastname; then by firstname
        SortedList<ItemUI> sortedList = new SortedList<>(itemsList);

        // sort by lastname first, then by firstname; ignore notes
        sortedList.setComparator((p1, p2) ->
            p1.getConcept().compareToIgnoreCase(p2.getConcept())
        );
        listView.setItems(sortedList);

        // Pre-select the first item
        listView.getSelectionModel().selectFirst();

    }

    private void showUpdateItemScreen(ActionEvent actionEvent) {
        ItemUI itemUI = updateItemController.start(selectedItem.getId());
        load(itemUI);
//        switchScreenService.showUpdateItemScreen(newItemButton, "/fxml/itemUpdate.fxml", selectedItem);

//        System.out.println("Update " + selectedItem);
//        Item p = listView.getSelectionModel().getSelectedItem();
//        listView.getSelectionModel().selectedItemProperty().removeListener(itemChangeListener);
//        p.setConcept(conceptTextField.getText());
//        p.setDescription(descriptionTextField.getText());
//        p.setNotes(notesTextArea.getText());
//        listView.getSelectionModel().selectedItemProperty().addListener(itemChangeListener);
//        modifiedProperty.set(false);
    }

    private void showNewItemScreen(ActionEvent actionEvent) {
        ItemUI itemUI = newItemController.start();
        load(itemUI);
    }

    public void load(ItemUI itemUI) {
//        listView.getSelectionModel().selectedItemProperty().removeListener(itemChangeListener);
        itemsList.clear();
        itemsList.addAll(itemService.loadBudgetList());

        // Use a sorted list; sort by lastname; then by firstname
        SortedList<ItemUI> sortedList = new SortedList<>(itemsList);

        // sort by lastname first, then by firstname; ignore notes
        sortedList.setComparator((p1, p2) ->
                p1.getConcept().compareToIgnoreCase(p2.getConcept())
        );
        listView.setItems(sortedList);

        // Pre-select the first item
//        listView.getSelectionModel().selectedItemProperty().addListener(itemChangeListener);
        if(itemUI != null && itemUI.getId() != null) {
            listView.getSelectionModel().select(itemUI);
            listView.getFocusModel().focus(listView.getSelectionModel().getSelectedIndex());
        }
//        else {
//            listView.getSelectionModel().selectFirst();
//        }
    }

    @EventListener
    public void onApplicationEvent(OptionValuesChangedEvent optionValuesChangedEvent) {
        reloadOptionValueDropBox(optionValuesChangedEvent.getOptionValueSetEnum());
    }

    private void reloadOptionValueDropBox(OptionValueSetEnum optionValueSetEnum) {
        if (optionValueSetEnum == OptionValueSetEnum.CATEGORY) {
            choiceBoxService.reloadOptionValueDropBox(categoryChoiceBox, OptionValueSetEnum.CATEGORY);
        } else {
            choiceBoxService.reloadOptionValueDropBox(unitsChoiceBox, OptionValueSetEnum.UNIT);
        }
    }




}
