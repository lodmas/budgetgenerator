package com.jskno.budgetgenerator.controllers.budget;

import com.jskno.budgetgenerator.converters.DtoToEntityConverter;
import com.jskno.budgetgenerator.converters.OptionValueConverter;
import com.jskno.budgetgenerator.interfaces.BudgetService;
import com.jskno.budgetgenerator.interfaces.ConfigurationService;
import com.jskno.budgetgenerator.interfaces.RequiredFieldValidator;
import com.jskno.budgetgenerator.modalmessages.ConfirmationBox;
import com.jskno.budgetgenerator.modalmessages.MessageBox;
import com.jskno.budgetgenerator.model.database.Budget;
import com.jskno.budgetgenerator.model.database.OptionValue;
import com.jskno.budgetgenerator.model.database.OptionValueSetEnum;
import com.jskno.budgetgenerator.model.ui.BudgetItemUI;
import com.jskno.budgetgenerator.model.ui.BudgetUI;
import com.jskno.budgetgenerator.model.ui.ItemUI;
import com.jskno.budgetgenerator.model.ui.converters.BigDecimalCurrencyStringConverter;
import com.jskno.budgetgenerator.model.ui.converters.BigDecimalPercentStringConverter;
import com.jskno.budgetgenerator.model.ui.customElements.MyTextFieldTableCell;
import com.jskno.budgetgenerator.react.cancelevents.BudgetCancelOperationEvent;
import com.jskno.budgetgenerator.react.operationevents.BudgetOperationPerformedEvent;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.ChoiceBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.util.StringConverter;
import javafx.util.converter.BigDecimalStringConverter;
import org.springframework.context.ApplicationEventPublisher;

import java.io.InputStream;
import java.math.BigDecimal;

public abstract class AbstractBudgetController {

    protected ApplicationEventPublisher applicationEventPublisher;
    protected TileController tileController;
    protected BudgetService budgetService;
    protected ConfigurationService configurationService;
    protected RequiredFieldValidator requiredFieldValidator;

    protected BudgetUI budgetUI;

    protected ChoiceBox<OptionValue> unitsChoiceBox;

    protected TextField nameTextField;
    protected TextField totalCostTextField;
    protected TextField marginPercentTextField;
    protected TextField totalMarginTextField;
    protected TextField totalCostPlusMarginTextField;
    protected TextField totalVatTextField;
    protected TextField totalBudgetTextField;

    protected TableView<BudgetItemUI> table;

    protected TableColumn<BudgetItemUI, BigDecimal> priceColumn;
    protected TableColumn<BudgetItemUI, BigDecimal> quantityColumn;
    protected TableColumn<BudgetItemUI, BigDecimal> marginCol;
    protected TableColumn<BudgetItemUI, BigDecimal> totalCol;

    protected ObservableList<BudgetItemUI> items;

    public AbstractBudgetController(ApplicationEventPublisher applicationEventPublisher,
                TileController tileController, BudgetService budgetService,
                ConfigurationService configurationService, RequiredFieldValidator requiredFieldValidator) {
        this.applicationEventPublisher = applicationEventPublisher;
        this.tileController = tileController;
        this.budgetService = budgetService;
        this.configurationService = configurationService;
        this.requiredFieldValidator = requiredFieldValidator;
    }

    protected void initializeObjects() {
        table = new TableView<>();
        this.nameTextField = new TextField();
        this.totalCostTextField = new TextField();
        this.marginPercentTextField = new TextField();
        this.totalMarginTextField = new TextField();
        this.totalCostPlusMarginTextField = new TextField();
        this.totalVatTextField = new TextField();
        this.totalBudgetTextField = new TextField();
    }

    protected void fillFields(BudgetUI budgetUI) {
        items = budgetUI.getBudgetItems();
        table.setItems(items);

        nameTextField.textProperty().bindBidirectional(budgetUI.nameProperty());
        totalCostTextField.textProperty().bind(budgetUI.totalCostProperty().asString());
        marginPercentTextField.textProperty().bind(budgetUI.marginPercentProperty().asString());
        totalMarginTextField.textProperty().bind(budgetUI.totalMarginProperty().asString());
        totalCostPlusMarginTextField.textProperty().bind(budgetUI.totalCostPlusMarginProperty().asString());
        totalVatTextField.textProperty().bind(budgetUI.totalVatProperty().asString());
        totalBudgetTextField.textProperty().bind(budgetUI.totalProperty().asString());
    }

    protected VBox getRoot() {
        Label lblHeading = buildHeadingSection();
        HBox nameBox = buildNameBudgetSection();
        buildBudgetItemsTable();
        HBox budgetSummaryFields = buildBudgetSummarySection();
        HBox actionSection = buildActionSection();

        VBox paneMain = new VBox();
        paneMain.setSpacing(10);
        paneMain.setPadding(new Insets(10, 10, 10, 10));
        paneMain.getChildren().addAll(lblHeading, nameBox, table, budgetSummaryFields, actionSection);

        return paneMain;
    }

    protected Label buildHeadingSection() {
        Label lblHeading = new Label("Budget Designer");
        lblHeading.setFont(new Font("Arial", 20));
        return lblHeading;
    }

    protected HBox buildNameBudgetSection() {
        Label nameLabel = new Label("Nombre Presupuesto");
        nameTextField.setTooltip(new Tooltip("Nombre del presupuesto"));
        nameTextField.setOnAction(actionEvent -> budgetUI.setName(nameTextField.getText()));

        HBox nameHBox = new HBox();
        nameHBox.setSpacing(8);
        nameHBox.setAlignment(Pos.BOTTOM_CENTER);
        nameHBox.getChildren().addAll(nameLabel, nameTextField);

        return nameHBox;
    }

    protected void buildBudgetItemsTable() {

        table.setPlaceholder(new Label("Sin conceptos que mostrar"));
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY );

        TableColumn<BudgetItemUI, BudgetItemUI> deleteCol = buildDeleteColumn();
        TableColumn<BudgetItemUI, String> conceptCol = buildConceptColumn();
        buildPriceColumn();
        TableColumn<BudgetItemUI, OptionValue> unitsColumn = buildUnitsColumn();
        buildQuantityColumn();
        TableColumn<BudgetItemUI, BigDecimal> totalCostItemColumn = buildTotalCostItemColumn();
        TableColumn marginNestedCols = buildMarginNestedColumns();
        TableColumn<BudgetItemUI, BigDecimal> totalCostPlusMarginColumn = buildTotalCostPlusMarginColumn();
        TableColumn vatNestedCols = buildVatNestedColumns();
        buildTotalColumn();

        table.getColumns().addAll(deleteCol, conceptCol, priceColumn, unitsColumn,
                quantityColumn, totalCostItemColumn, marginNestedCols,
                totalCostPlusMarginColumn, vatNestedCols, totalCol);

        setTableEditable();
    }

    private TableColumn<BudgetItemUI, BudgetItemUI> buildDeleteColumn() {

        TableColumn<BudgetItemUI, BudgetItemUI> deleteCol = new TableColumn<>("");
        deleteCol.setMinWidth(50);
        deleteCol.setPrefWidth(50);
        deleteCol.setMaxWidth(60);
        deleteCol.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
        deleteCol.setCellFactory(param -> new TableCell<>() {
            private final Button deleteButton = new Button("", getImageView());

            @Override
            protected void updateItem(BudgetItemUI budgetItemUI, boolean empty) {
                super.updateItem(budgetItemUI, empty);

                if (budgetItemUI == null) {
                    setGraphic(null);
                    return;
                }

                deleteButton.setMaxSize(1, 1);
                setGraphic(deleteButton);
                deleteButton.setOnAction(event -> onDeleteAction(budgetItemUI));
                budgetUI.recalculateTotals();
            }
        });

        return deleteCol;
    }

    protected ImageView getImageView() {
        InputStream input = this.getClass().getResourceAsStream("/images/delete.png");
        Image image = new Image(input);
        return new ImageView(image);
    }

    private TableColumn<BudgetItemUI, String> buildConceptColumn() {

        TableColumn<BudgetItemUI, String> conceptCol = new TableColumn<>("Concepto");
        conceptCol.setMinWidth(310);
        conceptCol.setCellValueFactory(
                new PropertyValueFactory<>("Concept"));
        conceptCol.setCellFactory(
                TextFieldTableCell.forTableColumn());
//        conceptCol.setCellFactory(
//                EditCell.forTableColumn());

        conceptCol.setOnEditCommit(e -> conceptCol_OnEditCommit(e));

        return conceptCol;
    }

    private void buildPriceColumn() {
        priceColumn = buildNumericEditableColumn(
                "Precio",
                "Price",
                90,
                new BigDecimalCurrencyStringConverter());
        priceColumn.setOnEditCommit(e -> priceCol_OnEditCommit(e));
    }

    private TableColumn<BudgetItemUI, OptionValue> buildUnitsColumn() {
        TableColumn<BudgetItemUI, OptionValue> unitsColumn = new TableColumn("Unidades");
        unitsColumn.setMinWidth(85);
        unitsColumn.setCellValueFactory(new PropertyValueFactory<>("Units"));
        unitsColumn.setCellFactory(
                ChoiceBoxTableCell.forTableColumn(
                        new OptionValueConverter(),
                        FXCollections.observableArrayList(configurationService.getConfiguration(
                                OptionValueSetEnum.UNIT))));
        unitsColumn.setOnEditCommit(e -> unitsCol_OnEditCommit(e));

        return unitsColumn;
    }

    private void buildQuantityColumn() {
        quantityColumn = buildNumericEditableColumn(
                "Cantidad",
                "Quantity",
                80,
                new BigDecimalStringConverter());
        quantityColumn.setOnEditCommit(e -> quantityCol_OnEditCommit(e));
    }

    protected TableColumn<BudgetItemUI, BigDecimal> buildTotalCostItemColumn() {
        TableColumn<BudgetItemUI, BigDecimal> col = buildNumericNotEditableColumn(
                "Total Coste",
                "TotalCost",
                100,
                new BigDecimalCurrencyStringConverter());
        return col;
    }

    protected TableColumn buildMarginNestedColumns() {
        TableColumn marginNestedColumns = new TableColumn("Beneficio");
        buildMarginColumn();
        TableColumn<BudgetItemUI, BigDecimal> marginAmountColumn = buildMarginAmountColumn();
        marginNestedColumns.getColumns().addAll(marginCol, marginAmountColumn);
        return marginNestedColumns;
    }

    protected void buildMarginColumn() {
        marginCol = buildNumericEditableColumn(
                "%",
                "Margin",
                55,
                new BigDecimalPercentStringConverter());
        marginCol.setOnEditCommit(e -> marginCol_OnEditCommit(e));
    }

    protected TableColumn<BudgetItemUI, BigDecimal> buildMarginAmountColumn() {
        TableColumn<BudgetItemUI, BigDecimal> col = buildNumericNotEditableColumn(
                "Euros",
                "MarginAmount",
                90,
                new BigDecimalCurrencyStringConverter());
        return col;
    }

    protected TableColumn<BudgetItemUI, BigDecimal> buildTotalCostPlusMarginColumn() {
        TableColumn<BudgetItemUI, BigDecimal> col = buildNumericNotEditableColumn(
                "Coste+Beneficio",
                "TotalCostPlusMargin",
                110,
                new BigDecimalCurrencyStringConverter());
        return col;
    }

    protected TableColumn buildVatNestedColumns() {
        TableColumn vatNestedColumns = new TableColumn("IVA");
        TableColumn<BudgetItemUI, BigDecimal> vatColumn = buildVatColumn();
        TableColumn<BudgetItemUI, BigDecimal> vatAmountColumn = buildVatAmountColumn();
        vatNestedColumns.getColumns().addAll(vatColumn, vatAmountColumn);
        return vatNestedColumns;
    }

    protected TableColumn<BudgetItemUI, BigDecimal> buildVatColumn() {
        TableColumn<BudgetItemUI, BigDecimal> vatColumn = buildNumericEditableColumn(
                "%",
                "Vat",
                55,
                new BigDecimalPercentStringConverter());
        vatColumn.setOnEditCommit(e -> vatCol_OnEditCommit(e));
        return vatColumn;
    }

    protected TableColumn<BudgetItemUI, BigDecimal> buildVatAmountColumn() {
        TableColumn<BudgetItemUI, BigDecimal> col = buildNumericNotEditableColumn(
                "Euros",
                "VatAmount",
                90,
                new BigDecimalCurrencyStringConverter());
        return col;
    }

    protected void buildTotalColumn() {
        totalCol = buildNumericNotEditableColumn(
                "Total Item",
                "TotalBudgetItem",
                110,
                new BigDecimalCurrencyStringConverter());
    }

    protected HBox buildBudgetSummarySection() {
        Label totalLabel = new Label("TOTALES");
        totalLabel.setMinWidth(100);
        totalLabel.setFont(Font.font(Font.getDefault().getFamily(), FontWeight.BOLD, 15));

        buildTotalTextField(totalCostTextField, true, "-fx-opacity: 0.6;", 100,
                new TextFormatter<>(new BigDecimalCurrencyStringConverter()));

        buildTotalTextField(marginPercentTextField, true, "-fx-opacity: 0.6;", 60,
                new TextFormatter<>(new BigDecimalPercentStringConverter()));
        marginPercentTextField.setMaxWidth(105);

        buildTotalTextField(totalMarginTextField, true, "-fx-opacity: 0.6;", 80,
                new TextFormatter<>(new BigDecimalCurrencyStringConverter()));

        buildTotalTextField(totalCostPlusMarginTextField, true, "-fx-opacity: 0.6;", 100,
                new TextFormatter<>(new BigDecimalCurrencyStringConverter()));

        buildTotalTextField(totalVatTextField, true, "-fx-opacity: 0.6;", 100,
                new TextFormatter<>(new BigDecimalCurrencyStringConverter()));

        buildTotalTextField(totalBudgetTextField, true, "-fx-opacity: 0.6;", 100,
                new TextFormatter<>(new BigDecimalCurrencyStringConverter()));

        Region region1 = new Region();
        region1.setMinWidth(505);
        region1.setMaxWidth(770);
        HBox.setHgrow(region1, Priority.ALWAYS);
        Region region2 = new Region();
        region2.setMinWidth(45);
        region2.setMaxWidth(130);
        HBox.setHgrow(region2, Priority.ALWAYS);

        HBox budgetSummaryFields = new HBox();
//        budgetSummaryFields.setMinWidth(1260);
//        budgetSummaryFields.setPrefWidth(1260);
        budgetSummaryFields.setPadding(new Insets(5));
        budgetSummaryFields.setSpacing(5);
        budgetSummaryFields.getChildren().addAll(
                totalLabel, region1, totalCostTextField, marginPercentTextField,
                totalMarginTextField, totalCostPlusMarginTextField, region2,
                totalVatTextField, totalBudgetTextField);

        return budgetSummaryFields;
    }

    private TableColumn<BudgetItemUI, BigDecimal> buildNumericEditableColumn(String columnHeader,
                String propertyName, double width, StringConverter<BigDecimal> converter) {
        TableColumn<BudgetItemUI, BigDecimal> col = new TableColumn<>(columnHeader);
        col.setMinWidth(width);
        col.setCellValueFactory(new PropertyValueFactory<>(propertyName));
        col.setCellFactory(MyTextFieldTableCell.forTableColumn(converter));
        col.setEditable(true);

//        col.addEventHandler(KeyEvent.KEY_TYPED, keyEvent -> {
//            if (!keyEvent.getCharacter().matches("\\d{0,7}([\\.]\\d{0,4})?")) {
//                keyEvent.consume();
//            }
//        });
//        col.cellValueFactoryProperty().addListener(
//                new ChangeListener<Callback<TableColumn.CellDataFeatures<BudgetItemUI, BigDecimal>, ObservableValue<BigDecimal>>>() {
//            @Override
//            public void changed(ObservableValue<? extends
//                    Callback<TableColumn.CellDataFeatures<BudgetItemUI, BigDecimal>, ObservableValue<BigDecimal>>> observableValue, Callback<TableColumn.CellDataFeatures<BudgetItemUI, BigDecimal>, ObservableValue<BigDecimal>> cellDataFeaturesObservableValueCallback, Callback<TableColumn.CellDataFeatures<BudgetItemUI, BigDecimal>, ObservableValue<BigDecimal>> t1) {
//                if (!t1.toString().matches("\\d{0,7}([\\.]\\d{0,4})?")) {
//                    System.out.println("asfs");
//                }
//            }
//        });


        return col;
    }

    protected TableColumn<BudgetItemUI, BigDecimal> buildNumericNotEditableColumn(
            String columnHeader, String propertyName, double width,
            StringConverter<BigDecimal> converter) {

        TableColumn<BudgetItemUI, BigDecimal> col = new TableColumn<>(columnHeader);
        col.setMinWidth(width);
        col.setCellValueFactory(new PropertyValueFactory<>(propertyName));
        col.setCellFactory(MyTextFieldTableCell.forTableColumn(converter));
        col.setEditable(false);
        col.setStyle("-fx-opacity: 0.6;");
        return col;
    }

    private void buildTotalTextField(TextField textField, boolean isDisable, String style,
                     double width, TextFormatter textFormatter) {
        textField.setDisable(isDisable);
        textField.setStyle(style);
        textField.setMinWidth(width);
        textField.setTextFormatter(textFormatter);
    }

    protected abstract HBox buildActionSection();

    private void onDeleteAction(BudgetItemUI budgetItemUI) {
        boolean confirm;
        confirm = ConfirmationBox.show(
                "Estás seguro de que quieres eliminar este item?", "Confirmación",
                "Sí", "No");
        if (confirm) {
            items.remove(budgetItemUI);
        }
    }

    private void conceptCol_OnEditCommit(Event e) {
        TableColumn.CellEditEvent<BudgetItemUI, String> ce;
        ce = (TableColumn.CellEditEvent<BudgetItemUI, String>) e;
        BudgetItemUI budgetItemUI = ce.getRowValue();
        budgetItemUI.setConcept(ce.getNewValue());
    }

    protected void priceCol_OnEditCommit(Event e) {
        TableColumn.CellEditEvent<BudgetItemUI, BigDecimal> ce;
        ce = (TableColumn.CellEditEvent<BudgetItemUI, BigDecimal>) e;
        BudgetItemUI budgetItemUI = ce.getRowValue();
        final BigDecimal value = ce.getNewValue() != null
                ? ce.getNewValue() : ce.getOldValue();
        budgetItemUI.setPrice(value);

//        BudgetItemUI budgetItemUIFromBudget = budgetUI.getBudgetItems().filtered(budgetItemUI1 -> budgetItemUI.getId() == budgetItemUI.getId()).get(0);
//        budgetItemUIFromBudget.setPrice(value);

        table.refresh();
        budgetUI.recalculateTotalCost();
//        budgetUI.recalculateTotalMargin();
//        budgetUI.recalculateTotalCostPlusMargin();
//        budgetUI.recalculateTotalVat();
//        budgetUI.recalculateTotalBudget();
//        totalTextField.setText(String.valueOf(budgetUI.getTotal()));
    }

    private void unitsCol_OnEditCommit(Event e) {
        TableColumn.CellEditEvent<BudgetItemUI, OptionValue> ce;
        ce = (TableColumn.CellEditEvent<BudgetItemUI, OptionValue>) e;
        BudgetItemUI budgetItemUI = ce.getRowValue();
        budgetItemUI.setUnits(ce.getNewValue());
    }

    protected void quantityCol_OnEditCommit(Event e) {
        TableColumn.CellEditEvent<BudgetItemUI, BigDecimal> ce;
        ce = (TableColumn.CellEditEvent<BudgetItemUI, BigDecimal>) e;
        BudgetItemUI budgetItemUI = ce.getRowValue();
        budgetItemUI.setQuantity(ce.getNewValue());
        table.refresh();
        budgetUI.recalculateTotalCost();
    }

    protected void marginCol_OnEditCommit(Event e) {
        TableColumn.CellEditEvent<BudgetItemUI, BigDecimal> ce;
        ce = (TableColumn.CellEditEvent<BudgetItemUI, BigDecimal>) e;
        BudgetItemUI budgetItemUI = ce.getRowValue();
        budgetItemUI.setMargin(ce.getNewValue() != null ? ce.getNewValue() : ce.getOldValue());
        table.refresh();
        budgetUI.recalculateTotalMargin();
    }

    protected void vatCol_OnEditCommit(Event e) {
        TableColumn.CellEditEvent<BudgetItemUI, BigDecimal> ce;
        ce = (TableColumn.CellEditEvent<BudgetItemUI, BigDecimal>) e;
        BudgetItemUI budgetItemUI = ce.getRowValue();
        budgetItemUI.setVat(ce.getNewValue() != null ? ce.getNewValue() : ce.getOldValue());
        table.refresh();
        budgetUI.recalculateTotalVat();
    }

    protected void btnAdd_Clicked() {
        ObservableList<ItemUI> selectedItems = tileController.show(items);
        selectedItems.forEach(itemUI -> items.add(new BudgetItemUI(itemUI)));
        budgetUI.recalculateTotals();
    }

    protected void btnSaveOrUpdate_Clicked() {

        if(!validateFields()) {
            return;
        }

        Budget budget = DtoToEntityConverter.getBudget(budgetUI);

        Long budgetId = budgetService.saveOrUpdate(budget).getId();
        resetFields();
        applicationEventPublisher.publishEvent(new BudgetOperationPerformedEvent(this, budgetId));
    }

    protected boolean validateFields() {
        String errorMessage = "";

        errorMessage = requiredFieldValidator.validateRequiredField(nameTextField, errorMessage);

        if (errorMessage.length() > 0) {
            MessageBox.show(errorMessage, "Invalid Data");
            return false;
        }

        return true;
    }

    protected void btnCancel_Clicked() {
        resetFields();
        applicationEventPublisher.publishEvent(new BudgetCancelOperationEvent(this, budgetUI.getId()));
    }

    protected void resetFields() {
        nameTextField.clear();
        table.getItems().clear();
        totalBudgetTextField.clear();
    }

    private void setTableEditable() {
        table.setEditable(true);
        // allows the individual cells to be selected
        table.getSelectionModel().cellSelectionEnabledProperty().set(true);
        // when character or numbers pressed it will start edit in editable fields
//        table.setOnKeyPressed(event -> {
//            if (event.getCode().isLetterKey() || event.getCode().isDigitKey()) {
//                editFocusedCell();
//            }
//            else if (event.getCode() == KeyCode.RIGHT
//                    || event.getCode() == KeyCode.TAB) {
//                table.getSelectionModel().selectNext();
//                event.consume();
//            } else if (event.getCode() == KeyCode.LEFT) {
//                // work around due to
//                // TableView.getSelectionModel().selectPrevious() due to a bug
//                // stopping it from working on
//                // the first column in the last row of the table
//                selectPrevious();
//                event.consume();
//            }
//        });
    }

    private void editFocusedCell() {
        final TablePosition<BudgetItemUI, ?> focusedCell = table
                .focusModelProperty().get().focusedCellProperty().get();
        table.edit(focusedCell.getRow(), focusedCell.getTableColumn());
    }

    private void selectPrevious() {
        if (table.getSelectionModel().isCellSelectionEnabled()) {
            // in cell selection mode, we have to wrap around, going from
            // right-to-left, and then wrapping to the end of the previous line
            TablePosition<BudgetItemUI, ?> pos = table.getFocusModel()
                    .getFocusedCell();
            if (pos.getColumn() - 1 >= 0) {
                // go to previous row
                table.getSelectionModel().select(pos.getRow(),
                        getTableColumn(pos.getTableColumn(), -1));
            } else if (pos.getRow() < table.getItems().size()) {
                // wrap to end of previous row
                table.getSelectionModel().select(pos.getRow() - 1,
                        table.getVisibleLeafColumn(
                                table.getVisibleLeafColumns().size() - 1));
            }
        } else {
            int focusIndex = table.getFocusModel().getFocusedIndex();
            if (focusIndex == -1) {
                table.getSelectionModel().select(table.getItems().size() - 1);
            } else if (focusIndex > 0) {
                table.getSelectionModel().select(focusIndex - 1);
            }
        }
    }

    private TableColumn<BudgetItemUI, ?> getTableColumn(
            final TableColumn<BudgetItemUI, ?> column, int offset) {
        int columnIndex = table.getVisibleLeafIndex(column);
        int newColumnIndex = columnIndex + offset;
        return table.getVisibleLeafColumn(newColumnIndex);
    }
}
