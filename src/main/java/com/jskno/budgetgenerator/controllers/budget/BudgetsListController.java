package com.jskno.budgetgenerator.controllers.budget;

import com.jskno.budgetgenerator.interfaces.BudgetService;
import com.jskno.budgetgenerator.interfaces.PdfService;
import com.jskno.budgetgenerator.modalmessages.ConfirmationBox;
import com.jskno.budgetgenerator.model.ui.BudgetUITable;
import com.jskno.budgetgenerator.model.ui.converters.BigDecimalCurrencyStringConverter;
import com.jskno.budgetgenerator.react.openformevents.BudgetOpenAddFormEvent;
import com.jskno.budgetgenerator.react.openformevents.BudgetOpenModifyFormEvent;
import com.jskno.budgetgenerator.react.openformevents.BudgetOpenViewFromEvent;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleObjectProperty;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Component
public class BudgetsListController {

    private BudgetService budgetService;
    private ApplicationEventPublisher applicationEventPublisher;
    private PdfService pdfService;

    @Autowired
    public BudgetsListController(BudgetService budgetService, ApplicationEventPublisher applicationEventPublisher, PdfService pdfService) {
        this.budgetService = budgetService;
        this.applicationEventPublisher = applicationEventPublisher;
        this.pdfService = pdfService;
    }

    private TableView<BudgetUITable> table;
    private TableColumn<BudgetUITable, BigDecimal> totalCol;

    public Node start() {
        table = new TableView<>();
        initialiseData();
        Parent root = getRoot();
        return root;
    }

    private Parent getRoot() {

        Label lblHeading = buildHeading();
        table = buildTable();
        HBox buttonSection = buildButtonSection();

        VBox paneMain = new VBox();
        paneMain.setSpacing(10);
        paneMain.setPadding(new Insets(20));
        paneMain.getChildren().addAll(lblHeading, table, buttonSection);

        return paneMain;

    }

    private Label buildHeading() {
        Label lblHeading = new Label("Lista de Presupuestos");
        lblHeading.setFont(new Font("Arial", 20));
        return lblHeading;
    }

    private TableView<BudgetUITable> buildTable() {
        table.setEditable(false);
        table.setPlaceholder(new Label("Ningún presupuesto que mostrar"));
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY );

        TableColumn<BudgetUITable, BudgetUITable> deleteCol = buildDeleteColumn();
        TableColumn<BudgetUITable, LocalDate> updatedDateCol = buildUpdatedDateColumn();
        TableColumn<BudgetUITable, String> nameCol = buildBudgetNameColumn();
        totalCol = buildTotalBudgetColumn();

        table.getColumns().addAll(deleteCol, updatedDateCol, nameCol, totalCol);

        return table;
    }

    private TableColumn<BudgetUITable, BudgetUITable> buildDeleteColumn() {
        TableColumn<BudgetUITable, BudgetUITable> deleteCol = new TableColumn<>("");
        deleteCol.setMinWidth(50);
        deleteCol.setPrefWidth(50);
        deleteCol.setMaxWidth(50);
        deleteCol.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
        deleteCol.setCellFactory(param -> new TableCell<>() {
            private final Button deleteButton = new Button("", getImageView());

            @Override
            protected void updateItem(BudgetUITable budgetUITable, boolean empty) {
                super.updateItem(budgetUITable, empty);

                if (budgetUITable == null) {
                    setGraphic(null);
                    return;
                }

                deleteButton.setMaxSize(1, 1);
                setGraphic(deleteButton);
                deleteButton.setOnAction(event -> onDeleteAction(budgetUITable.getId()));
            }
        });
        return deleteCol;
    }

    private ImageView getImageView() {
        InputStream input = this.getClass().getResourceAsStream("/images/delete.png");
        Image image = new Image(input);
        return new ImageView(image);
    }

    private TableColumn<BudgetUITable, LocalDate> buildUpdatedDateColumn() {
        TableColumn<BudgetUITable, LocalDate> updatedDateCol =
                new TableColumn("Fecha Actualización");
        updatedDateCol.setMinWidth(300);
        updatedDateCol.setPrefWidth(300);
        updatedDateCol.setMaxWidth(500);
        updatedDateCol.setCellValueFactory(budget -> {
            SimpleObjectProperty<LocalDate> property = new SimpleObjectProperty<>();
            property.setValue(budget.getValue().getUpdateDate());
            return property;
        });
//        updatedDateCol.setCellFactory(
//                TextFieldTableCell.forTableColumn());
        updatedDateCol.setCellFactory(column -> new TableCell<>() {
            DateTimeFormatter myDateFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");

            @Override
            protected void updateItem(LocalDate item, boolean empty) {
                super.updateItem(item, empty);

                if (item == null || empty) {
                    setText(null);
                    setStyle("");
                } else {
                    // Format date.
                    setText(myDateFormatter.format(item));
                }
                }
            });
        return updatedDateCol;
    }

    private TableColumn<BudgetUITable, String> buildBudgetNameColumn() {
        TableColumn<BudgetUITable, String> nameCol = new TableColumn<>("Nombre");
        nameCol.setMinWidth(300);
        nameCol.setCellValueFactory(
                new PropertyValueFactory<>("Name"));
        nameCol.setCellFactory(
                TextFieldTableCell.forTableColumn());
        return nameCol;
    }

    private TableColumn<BudgetUITable, BigDecimal> buildTotalBudgetColumn() {
        totalCol = new TableColumn("Total");
        totalCol.setMinWidth(100);
        totalCol.setCellValueFactory(new PropertyValueFactory<>("Total"));
        totalCol.setCellFactory(
                TextFieldTableCell.forTableColumn(
                        new BigDecimalCurrencyStringConverter()));
        return totalCol;
    }

    private HBox buildButtonSection() {

        Button btnView = new Button("Ver Presupuesto");
        btnView.setMinWidth(60);
        btnView.setOnAction(e -> btnView_Clicked());

        Button btnModify = new Button("Modificar Presupuesto");
        btnModify.setMinWidth(60);
        btnModify.setOnAction(e -> btnModify_Clicked());

        Button btnAdd = new Button("Añadir Presupuesto");
        btnAdd.setMinWidth(60);
        btnAdd.setOnAction(e -> btnAdd_Clicked());

        Button btnPrint = new Button("Imprimir Presupuesto");
        btnPrint.setMinWidth(60);
        btnPrint.setOnAction(e -> btnPrint_Clicked());

        Region region = new Region();
        HBox.setHgrow(region, Priority.ALWAYS);

        HBox buttonsSection = new HBox();
        buttonsSection.setSpacing(8);
        buttonsSection.getChildren().addAll(btnView, btnModify, btnAdd, region, btnPrint);

        return buttonsSection;
    }

    public void initialiseData() {
//        budgetUITables = FXCollections.observableArrayList();
//        budgetUITables = budgetService.loadBudgetList();
        table.setItems(budgetService.loadBudgetList());
        table.getSelectionModel().selectFirst();
    }

    private void onDeleteAction(Long budgetId) {
        boolean confirm;
        confirm = ConfirmationBox.show(
                "Estás seguro de que quieres eliminar este presupuesto?", "Confirmación",
                "Sí", "No");
        if (confirm) {
            budgetService.deleteById(budgetId);
            initialiseData();
        }
    }

    public void btnView_Clicked() {
        long budgetId = table.getSelectionModel().getSelectedItem().getId();
        applicationEventPublisher.publishEvent(new BudgetOpenViewFromEvent(this, budgetId));
    }

    public void btnModify_Clicked() {
        long budgetId = table.getSelectionModel().getSelectedItem().getId();
        applicationEventPublisher.publishEvent(new BudgetOpenModifyFormEvent(this, budgetId));
    }

    public void btnAdd_Clicked() {
        applicationEventPublisher.publishEvent(new BudgetOpenAddFormEvent(this));
    }


    private void btnPrint_Clicked() {
        long budgetId = table.getSelectionModel().getSelectedItem().getId();
        System.out.println(budgetId);
        pdfService.generateBudgetPdf(budgetId);
    }

    public void reloadTab(long budgetId) {
        table.getItems().clear();
        table.getItems().addAll(budgetService.loadBudgetList());
        table.getSelectionModel().select(table.getItems().stream().filter(budgetUITable -> budgetUITable.getId() == budgetId).findFirst().get());
        table.refresh();
    }

}