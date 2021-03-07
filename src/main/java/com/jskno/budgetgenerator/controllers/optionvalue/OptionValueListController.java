package com.jskno.budgetgenerator.controllers.optionvalue;

import com.jskno.budgetgenerator.interfaces.ChoiceBoxService;
import com.jskno.budgetgenerator.interfaces.ConfigurationService;
import com.jskno.budgetgenerator.interfaces.OptionValueService;
import com.jskno.budgetgenerator.model.database.OptionValueSetEnum;
import com.jskno.budgetgenerator.model.ui.OptionValueSetUI;
import com.jskno.budgetgenerator.model.ui.OptionValueUI;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class OptionValueListController {

    private ConfigurationService configurationService;
    private ChoiceBoxService choiceBoxService;
    private OptionValueService optionValueService;
    private NewOptionValueController newOptionValueController;
    private UpdateOptionValueController updateOptionValueController;

    @Autowired
    public OptionValueListController(ConfigurationService configurationService, ChoiceBoxService choiceBoxService,
                     OptionValueService optionValueService, NewOptionValueController newOptionValueController,
                     UpdateOptionValueController updateOptionValueController) {
        this.configurationService = configurationService;
        this.choiceBoxService = choiceBoxService;
        this.optionValueService = optionValueService;
        this.newOptionValueController = newOptionValueController;
        this.updateOptionValueController = updateOptionValueController;
    }

    private TextField valueTextField;
    private TextField labelTextField;
    private TextField positionTextField;
    private CheckBox disableCheckBox;
    private Button removeButton;
    private Button newItemButton;
    private Button updateButton;

    private Map<OptionValueSetEnum, List<OptionValueUI>> optionValueSetUIListMap;

    private ChoiceBox<OptionValueSetUI> optionValueSetUIChoiceBox;
    private ListView<OptionValueUI> listView;

    private final ObservableMap<OptionValueSetUI, OptionValueUI> optionValueSets =
            FXCollections.observableHashMap();
    private final ObservableList<OptionValueUI> optionValuesUIList = FXCollections.observableArrayList(OptionValueUI.extractor);
//    private final ObservableList<OptionValueUI> optionValuesUIList = FXCollections.observableArrayList();
    // Observable objects returned by extractor (applied to each list element) are listened for changes and
    // transformed into "update" change of ListChangeListener.

    private OptionValueUI selectedItem;
    private final BooleanProperty modifiedProperty = new SimpleBooleanProperty(false);
    private ChangeListener<OptionValueUI> itemChangeListener;

    public Node start() {

        Parent root = getRoot();
        init();
        return root;
    }

    private Parent getRoot() {

        AnchorPane mainAnchorPane = new AnchorPane();
//        BorderPane mainAnchorPane = new BorderPane();
//        mainAnchorPane.prefWidth(600);
//        mainAnchorPane.prefHeight(400);

        HBox optionValueSetHBox = buildCategoriesAndUnitsDropBox();
        SplitPane splitPane = buildSplitPane();

        mainAnchorPane.getChildren().addAll(optionValueSetHBox, splitPane);
        mainAnchorPane.setTopAnchor(optionValueSetHBox, 30.0);
        mainAnchorPane.setLeftAnchor(optionValueSetHBox, 20.0);
        mainAnchorPane.setTopAnchor(splitPane, 40.0);
        mainAnchorPane.setLeftAnchor(splitPane, 300.0);

        return mainAnchorPane;
    }

    private HBox buildCategoriesAndUnitsDropBox() {
        HBox optionValueSetHBox = new HBox();
//        optionValueSetHBox.setPrefHeight(10);
//        optionValueSetHBox.setPadding(new Insets(20));
        optionValueSetHBox.setSpacing(8);
        optionValueSetHBox.setAlignment(Pos.TOP_LEFT);
        Label optionValueSetLabel = new Label("Option Value Set");
        optionValueSetUIChoiceBox = new ChoiceBox<>();
        optionValueSetHBox.getChildren().addAll(optionValueSetLabel, optionValueSetUIChoiceBox);
        return optionValueSetHBox;
    }

    private SplitPane buildSplitPane() {
        SplitPane splitPane = new SplitPane();
//        splitPane.prefWidth(600);
//        splitPane.prefHeight(395);
        splitPane.setDividerPositions(0.30);
//        splitPane.setPadding(new Insets(0, 0,100,0));

        AnchorPane listAnchorPane = buildOptionValuesList();
        AnchorPane formAnchorPane = buildOptionValueForm();

        splitPane.getItems().add(listAnchorPane);
        splitPane.getItems().add(formAnchorPane);
        return splitPane;
    }

    public AnchorPane buildOptionValuesList() {
        AnchorPane listAnchorPane = new AnchorPane();
//        listAnchorPane.setPrefWidth(160);
//        listAnchorPane.setPrefHeight(380);

        listView = new ListView<>();
//        listView.prefWidth(160);
//        listView.prefHeight(380);

        listAnchorPane.getChildren().add(listView);

        return listAnchorPane;
    }

    public AnchorPane buildOptionValueForm() {
        AnchorPane formAnchorPane = new AnchorPane();
//        formAnchorPane.setPrefWidth(440);
//        formAnchorPane.setPrefHeight(380);

        GridPane mainGridPane = new GridPane();
//        mainGridPane.setPrefWidth(440);
//        mainGridPane.setPrefHeight(380);

        GridPane formGridPane = buildForm();
        ButtonBar buttonBar = buildButtonBar();

        mainGridPane.addRow(0, formGridPane);
        mainGridPane.addRow(2, buttonBar);
        mainGridPane.setValignment(buttonBar, VPos.BOTTOM);

        formAnchorPane.getChildren().add(mainGridPane);

        return formAnchorPane;
    }

    private GridPane buildForm() {
        GridPane formGridPane = new GridPane();
        formGridPane.setPadding(new Insets(10));
        formGridPane.setHgap(10);
        formGridPane.setVgap(10);
//        formGridPane.setMinWidth(440);
//        formGridPane.setPrefWidth(440);
//        formGridPane.setMaxWidth(600);

        Label valueLabel = new Label("Value");
        valueTextField = new TextField();
        valueTextField.setDisable(true);
        valueTextField.setStyle("-fx-opacity: 0.6;");
        Label labelLabel = new Label("Label");
        labelTextField = new TextField();
        labelTextField.setDisable(true);
        labelTextField.setStyle("-fx-opacity: 0.6;");
        Label positionLabel = new Label("Position");
        positionTextField = new TextField();
        positionTextField.setDisable(true);
        positionTextField.setStyle("-fx-opacity: 0.6;");
        Label disableLabel = new Label("Disable");
        disableCheckBox = new CheckBox();
        disableCheckBox.setDisable(true);

        formGridPane.setHalignment(valueLabel, HPos.LEFT);
        formGridPane.setHalignment(labelLabel, HPos.LEFT);
        formGridPane.setHalignment(positionLabel, HPos.LEFT);
        formGridPane.setHalignment(disableLabel, HPos.LEFT);

        // Set column widths
        ColumnConstraints col01 = new ColumnConstraints();
        col01.setPercentWidth(35);
        ColumnConstraints col02 = new ColumnConstraints();
        col02.setPercentWidth(65);
        formGridPane.getColumnConstraints().addAll(col01, col02);

        formGridPane.addRow(0, valueLabel, valueTextField);
        formGridPane.addRow(1, labelLabel, labelTextField);
        formGridPane.addRow(2, positionLabel, positionTextField);
        formGridPane.addRow(3, disableLabel, disableCheckBox);

        return formGridPane;
    }

    private ButtonBar buildButtonBar() {
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

    private void init() {
        removeButton.disableProperty()
                .bind(listView.getSelectionModel().selectedItemProperty().isNull());
        updateButton.disableProperty()
                .bind(listView.getSelectionModel().selectedItemProperty().isNull());
//                .or(modifiedProperty.not())
//                .or(conceptTextField.textProperty().isEmpty()
//                .or(descriptionTextField.textProperty().isEmpty())));
//        createButton.disableProperty().bind(listView.getSelectionModel().selectedItemProperty().isNotNull()
//                .or(conceptTextField.textProperty().isEmpty()
//                        .or(descriptionTextField.textProperty().isEmpty())));

//        SampleData.fillSampleData(itemsList);

        optionValueSetUIListMap = configurationService.getOptionValueUIMap();

        choiceBoxService.fillOptionValueSetUIChoiceBox(optionValueSetUIChoiceBox);
        optionValueSetUIChoiceBox.setOnAction(actionEvent -> processOptionValueSelected(actionEvent));
//        optionValueSetUIChoiceBox.getSelectionModel().selectedItemProperty()
//                .addListener((observableValue, optionValueSetUI, t1) ->
//                        processOptionValueSelected(observableValue, optionValueSetUI, t1));
//        optionValuesUIList.addAll(optionValueSetUIListMap.get(OptionValueSetEnum.CATEGORY));

//        // Use a sorted list; sort by lastname; then by firstname
//        SortedList<OptionValueUI> sortedList = new SortedList<>(optionValuesUIList);
//
//        // sort by lastname first, then by firstname; ignore notes
//        sortedList.setComparator((p1, p2) -> {
//            int result = p1.getPosition() - p2.getPosition();
//            return result;
//        });
//        listView.setItems(sortedList);

        listView.getSelectionModel().selectedItemProperty().addListener(
                itemChangeListener = (observable, oldValue, newValue) -> {
                    System.out.println("Selected item: " + newValue);
                    // newValue can be null if nothing is selected
                    selectedItem = newValue;
                    modifiedProperty.set(false);
                    if (newValue != null) {
                        // Populate controls with selected Person
                        valueTextField.setText(selectedItem.getValue());
                        labelTextField.setText(selectedItem.getLabel());
                        positionTextField.setText(String.valueOf(selectedItem.getPosition()));
                        disableCheckBox.setSelected(selectedItem.isDisable());
                    } else {
                        valueTextField.setText("");
                        labelTextField.setText("");
                        positionTextField.setText("");
                        disableCheckBox.setSelected(false);
                    }
                });

        // Pre-select the first item
        optionValueSetUIChoiceBox.getSelectionModel().select(0);
//        listView.getSelectionModel().selectFirst();
    }

    private void processOptionValueSelected(ActionEvent actionEvent) {
        ChoiceBox<OptionValueSetUI> optionValueSetUIChoiceBox = (ChoiceBox<OptionValueSetUI>) actionEvent.getSource();
        OptionValueSetUI selectedOvs = optionValueSetUIChoiceBox.getSelectionModel().getSelectedItem();
        if(selectedOvs != null) {
            reloadOptionValuesList(selectedOvs);
        }
    }

    private void reloadOptionValuesList(OptionValueSetUI selectedOvs) {
        optionValuesUIList.clear();
        optionValuesUIList.addAll(optionValueSetUIListMap.get(
                configurationService.getOptionValueSetEnum(selectedOvs)));

        // Use a sorted list; sort by lastname; then by firstname
        SortedList<OptionValueUI> sortedList = new SortedList<>(optionValuesUIList);

        // sort by lastname first, then by firstname; ignore notes
        sortedList.setComparator((p1, p2) -> {
            int result = p1.getPosition() - p2.getPosition();
            return result;
        });
        listView.setItems(sortedList);
        if(selectedItem != null && selectedItem.getId() != 0) {
            listView.getSelectionModel().select(selectedItem);
        } else if(listView.getItems().size() > 0) {
            listView.getSelectionModel().selectFirst();
        }
    }

    private void removeButtonAction(ActionEvent actionEvent) {
        System.out.println("Remove " + selectedItem);
        try {
            optionValueService.removeOptionValueById(selectedItem.getId());
            loadAfterRemove(selectedItem);
        } catch (DataIntegrityViolationException ex) {

        }
    }

    private void showUpdateItemScreen(ActionEvent actionEvent) {
        //itemUpdateController2.setSelectedItem(selectedItem);
        OptionValueUI updateOptionValue = updateOptionValueController.start(selectedItem.getId());
        load(updateOptionValue);
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
        OptionValueUI newOptionValue = newOptionValueController.start(
                optionValueSetUIChoiceBox.getSelectionModel().getSelectedItem().getId());
        load(newOptionValue != null ? newOptionValue : selectedItem);
    }

    public void loadAfterRemove(OptionValueUI removedItem) {
        optionValueSetUIChoiceBox.setOnAction(null);
        optionValuesUIList.clear();

        optionValueSetUIListMap = configurationService.getOptionValueUIMap();
        choiceBoxService.fillOptionValueSetUIChoiceBox(optionValueSetUIChoiceBox);

        optionValueSetUIChoiceBox.getSelectionModel().select(removedItem.getOptionValueSetUI());
        optionValuesUIList.addAll(optionValueSetUIListMap.get(
                configurationService.getOptionValueSetEnum(optionValueSetUIChoiceBox.getValue())));
        listView.getSelectionModel().selectFirst();

        optionValueSetUIChoiceBox.setOnAction(actionEvent -> processOptionValueSelected(actionEvent));

    }

    public void load(OptionValueUI updatedOrNewItem) {
        optionValueSetUIChoiceBox.setOnAction(null);
        optionValuesUIList.clear();

        optionValueSetUIListMap = configurationService.getOptionValueUIMap();
        choiceBoxService.fillOptionValueSetUIChoiceBox(optionValueSetUIChoiceBox);
        if(updatedOrNewItem != null && updatedOrNewItem.getId() != 0) {
            optionValueSetUIChoiceBox.getSelectionModel().select(
                    updatedOrNewItem.getOptionValueSetUI());
            optionValuesUIList.addAll(optionValueSetUIListMap.get(
                    configurationService.getOptionValueSetEnum(updatedOrNewItem.getOptionValueSetUI())));
            listView.getSelectionModel().select(updatedOrNewItem);
        }

        optionValueSetUIChoiceBox.setOnAction(actionEvent -> processOptionValueSelected(actionEvent));

    }
}
