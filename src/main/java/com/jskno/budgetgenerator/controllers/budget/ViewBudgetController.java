package com.jskno.budgetgenerator.controllers.budget;

import com.jskno.budgetgenerator.converters.OptionValueConverter;
import com.jskno.budgetgenerator.interfaces.BudgetService;
import com.jskno.budgetgenerator.interfaces.ConfigurationService;
import com.jskno.budgetgenerator.interfaces.RequiredFieldValidator;
import com.jskno.budgetgenerator.model.database.OptionValue;
import com.jskno.budgetgenerator.model.ui.BudgetItemUI;
import com.jskno.budgetgenerator.model.ui.BudgetUI;
import com.jskno.budgetgenerator.model.ui.converters.BigDecimalCurrencyStringConverter;
import com.jskno.budgetgenerator.model.ui.converters.BigDecimalPercentStringConverter;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.ChoiceBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.converter.BigDecimalStringConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class ViewBudgetController extends AbstractBudgetController {

    @Autowired
    public ViewBudgetController(ApplicationEventPublisher applicationEventPublisher,
                    TileController tileController, BudgetService budgetService,
                    ConfigurationService configurationService, RequiredFieldValidator requiredFieldValidator) {
        super(applicationEventPublisher, tileController, budgetService,
                configurationService, requiredFieldValidator);
    }

    public Node start(Long budgetId) {
        initializeObjects();
        VBox root = getRoot();
        initialiseData(budgetId);
        return root;
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

    protected HBox buildNameBudgetSection() {
        Label nameLabel = new Label("Nombre Presupuesto");
        nameTextField.setEditable(false);
        nameTextField.setDisable(true);
        nameTextField.setStyle("-fx-opacity: 0.8;");

        HBox nameHBox = new HBox();
        nameHBox.setSpacing(8);
        nameHBox.setAlignment(Pos.BOTTOM_CENTER);
        nameHBox.getChildren().addAll(nameLabel, nameTextField);

        return nameHBox;
    }

    protected void buildBudgetItemsTable() {

        table.setEditable(false);
//        table.setDisable(true);
        table.setStyle("-fx-opacity: 0.8;");
        table.setPlaceholder(new Label("Sin conceptos que mostrar"));
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY );

        TableColumn<BudgetItemUI, BudgetItemUI> deleteCol = buildDeleteColumn();
        TableColumn<BudgetItemUI, String> conceptCol = buildConceptColumn();
        buildPriceColumn();
        TableColumn<BudgetItemUI, OptionValue> unitsColumn = buildUnitsColumn();
        buildQuantityColumn();
        TableColumn<BudgetItemUI, BigDecimal> totalCostItemColumn = buildTotalCostItemColumn();
        TableColumn marginNestedCols = buildMarginNestedColumns();
        TableColumn<BudgetItemUI, BigDecimal> marginAmountColumn = buildMarginAmountColumn();
        TableColumn<BudgetItemUI, BigDecimal> totalCostPlusMarginColumn = buildTotalCostPlusMarginColumn();
        TableColumn vatNestedCols = buildVatNestedColumns();
        buildTotalColumn();

        table.getColumns().addAll(deleteCol, conceptCol, priceColumn, unitsColumn,
                quantityColumn, totalCostItemColumn, marginNestedCols,
                totalCostPlusMarginColumn, vatNestedCols, totalCol);
    }


    private TableColumn<BudgetItemUI, BudgetItemUI> buildDeleteColumn() {

        TableColumn<BudgetItemUI, BudgetItemUI> deleteCol = new TableColumn<>("");
        deleteCol.setMinWidth(50);
        deleteCol.setPrefWidth(50);
        deleteCol.setMaxWidth(60);
        deleteCol.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
        deleteCol.setCellFactory(param -> new TableCell<>() {
            //            private final Button deleteButton = new Button("X");
            private final Button deleteButton = new Button("", getImageView());

            @Override
            protected void updateItem(BudgetItemUI budgetItemUI, boolean empty) {
                super.updateItem(budgetItemUI, empty);

                if (budgetItemUI == null) {
                    setGraphic(null);
                    return;
                }

                deleteButton.setMaxSize(1, 1);
                deleteCol.setEditable(false);
                deleteButton.setStyle("-fx-opacity: 0.6;");
                setGraphic(deleteButton);
            }
        });

        return deleteCol;
    }

    private TableColumn<BudgetItemUI, String> buildConceptColumn() {

        TableColumn<BudgetItemUI, String> conceptCol = new TableColumn<>("Concepto");
        conceptCol.setMinWidth(300);
        conceptCol.setCellValueFactory(
                new PropertyValueFactory<>("Concept"));
        conceptCol.setCellFactory(
                TextFieldTableCell.forTableColumn());

        return conceptCol;
    }

    private void buildPriceColumn() {
        priceColumn = buildNumericNotEditableColumn(
                "Precio",
                "Price",
                90,
                new BigDecimalCurrencyStringConverter());
    }

    private TableColumn<BudgetItemUI, OptionValue> buildUnitsColumn() {
        TableColumn<BudgetItemUI, OptionValue> unitsColumn = new TableColumn("Unidades");
        unitsColumn.setMinWidth(80);
        unitsColumn.setCellValueFactory(
                new PropertyValueFactory<>("Units"));
        unitsColumn.setCellFactory(
                ChoiceBoxTableCell.forTableColumn(
                        new OptionValueConverter()));

        return unitsColumn;
    }

    private void buildQuantityColumn() {
        quantityColumn = buildNumericNotEditableColumn(
                "Cantidad",
                "Quantity",
                90,
                new BigDecimalStringConverter());
    }

    protected void buildMarginColumn() {
        marginCol = buildNumericNotEditableColumn(
                "%",
                "Margin",
                70,
                new BigDecimalPercentStringConverter());
    }

    protected TableColumn<BudgetItemUI, BigDecimal> buildVatColumn() {
        TableColumn<BudgetItemUI, BigDecimal> vatColumn = buildNumericNotEditableColumn(
                "%",
                "Vat",
                60,
                new BigDecimalPercentStringConverter());
        return vatColumn;
    }

    private void initialiseData(Long budgetId) {
        budgetUI = new BudgetUI(budgetService.findById(budgetId));
        items = budgetUI.getBudgetItems();
        table.setItems(items);

        fillFields();
    }

    private void fillFields() {
        nameTextField.setText(budgetUI.getName());
        totalCostTextField.setText(budgetUI.getTotalCost().toString());
        marginPercentTextField.setText(budgetUI.getMarginPercent().toString());
        totalMarginTextField.setText(budgetUI.getTotalMargin().toString());
        totalCostPlusMarginTextField.setText(budgetUI.getTotalCostPlusMargin().toString());
        totalVatTextField.setText(budgetUI.getTotalVat().toString());
        totalBudgetTextField.setText(budgetUI.getTotal().toString());
    }

    protected HBox buildActionSection() {
        Button btnCancel = new Button("OK");
        btnCancel.setMinWidth(60);
        btnCancel.setOnAction(e -> btnCancel_Clicked());

        HBox actionHBox = new HBox();
        actionHBox.setSpacing(8);
        actionHBox.setAlignment(Pos.BOTTOM_RIGHT);
        actionHBox.getChildren().addAll(btnCancel);

        return actionHBox;
    }

}
