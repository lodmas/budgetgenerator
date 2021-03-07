package com.jskno.budgetgenerator.controllers.item;

import com.jskno.budgetgenerator.interfaces.ChoiceBoxService;
import com.jskno.budgetgenerator.interfaces.ItemService;
import com.jskno.budgetgenerator.interfaces.NumericFieldValidator;
import com.jskno.budgetgenerator.interfaces.RequiredFieldValidator;
import com.jskno.budgetgenerator.modalmessages.MessageBox;
import com.jskno.budgetgenerator.model.database.OptionValue;
import com.jskno.budgetgenerator.model.database.OptionValueSetEnum;
import com.jskno.budgetgenerator.model.ui.ItemUI;
import com.jskno.budgetgenerator.model.ui.converters.BigDecimalCurrencyStringConverter;
import com.jskno.budgetgenerator.model.ui.converters.BigDecimalPercentStringConverter;
import com.jskno.budgetgenerator.react.OptionValuesChangedEvent;
import com.jskno.budgetgenerator.react.openformevents.BudgetOpenViewFromEvent;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;

public abstract class AbstractItemController {

    protected ItemService itemService;
    protected ChoiceBoxService choiceBoxService;
    protected RequiredFieldValidator requiredFieldValidator;
    protected NumericFieldValidator numericFieldValidator;

    protected TextField conceptTextField;
    protected TextField descriptionTextField;
    protected TextArea notesTextArea;
    protected ChoiceBox<OptionValue> categoryChoiceBox;
    protected TextField priceTextField;
    protected ChoiceBox<OptionValue> unitsChoiceBox;
    protected TextField vatTextField;
    protected TextField marginTextField;

    protected Button cancelButton;

    protected ItemUI itemUI;

    public AbstractItemController(ItemService itemService, ChoiceBoxService choiceBoxService,
                      RequiredFieldValidator requiredFieldValidator, NumericFieldValidator numericFieldValidator) {

        this.itemService = itemService;
        this.choiceBoxService = choiceBoxService;
        this.requiredFieldValidator = requiredFieldValidator;
        this.numericFieldValidator = numericFieldValidator;
    }

    protected Parent getRoot() {

        initElements();
        AnchorPane formAnchorPane = new AnchorPane();

        GridPane mainGridPane = new GridPane();
        mainGridPane.setPrefWidth(650);
        mainGridPane.setPrefHeight(350);

        GridPane topGridPane = buildTopGridPane();
        GridPane middleGridPane = buildMiddleGridPane();
        ButtonBar buttonBar = buildButtonBar();

        mainGridPane.addRow(0, topGridPane);
        mainGridPane.addRow(1, middleGridPane);
        mainGridPane.addRow(2, buttonBar);
        mainGridPane.setValignment(buttonBar, VPos.BOTTOM);

        formAnchorPane.getChildren().add(mainGridPane);

        return formAnchorPane;
    }

    private GridPane buildTopGridPane() {
        GridPane topGridPane = new GridPane();
        topGridPane.setPadding(new Insets(10));
        topGridPane.setHgap(10);
        topGridPane.setVgap(10);

        Label conceptLabel = new Label("Concepto");
        Label categoryLabel = new Label("Categoría");
        categoryChoiceBox.prefWidth(280);
        categoryChoiceBox.prefHeight(25);
        Label notesLabel = new Label("Notes");
        notesTextArea.setPrefWidth(200);
        notesTextArea.setPrefHeight(220);

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

    private GridPane buildMiddleGridPane() {
        GridPane middleGridPane = new GridPane();
        middleGridPane.setPadding(new Insets(10));
        middleGridPane.setHgap(10);
        middleGridPane.setVgap(10);

        Label priceLabel = new Label("Precio");
        Label unitsLabel = new Label("Unidades");
        Label marginLabel = new Label("Beneficio %");
        Label vatLabel = new Label("IVA %");

        // Set alignments and spanning
//        middleGridPane.setHalignment(priceLabel, HPos.LEFT);
//        middleGridPane.setHalignment(unitsLabel, HPos.LEFT);
//        middleGridPane.setHalignment(vatLabel, HPos.LEFT);
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
        middleGridPane.getColumnConstraints().addAll(
                col1, col2, col3, col4, col5, col6, col7, col8);

        middleGridPane.addRow(0,
                priceLabel, priceTextField,
                unitsLabel, unitsChoiceBox,
                marginLabel, marginTextField,
                vatLabel, vatTextField);

        return middleGridPane;
    }

    protected abstract ButtonBar buildButtonBar();

    protected void initElements() {
        conceptTextField = new TextField();
        conceptTextField.setTooltip(new Tooltip("Concepto"));

        descriptionTextField = new TextField();

        notesTextArea = new TextArea();
        notesTextArea.setTooltip(new Tooltip("Notas"));

        categoryChoiceBox = new ChoiceBox<>();
        categoryChoiceBox.setTooltip(new Tooltip("Categoría"));

        priceTextField = new TextField();
        priceTextField.setTooltip(new Tooltip("Precio"));
        priceTextField.addEventFilter(KeyEvent.KEY_TYPED, keyEvent -> {
            if (!keyEvent.getCharacter().matches("\\d{0,7}([\\.]\\d{0,4})?")) {
                keyEvent.consume();
            }
        });
//        priceTextField.textProperty().addListener(new ChangeListener<String>() {
//            @Override
//            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
//                if (!newValue.matches("\\d{0,7}([\\.]\\d{0,4})?")) {
//                    priceTextField.setText(oldValue);
//                }
//            }
//        });
//        priceTextField.setTextFormatter(
//                new TextFormatter<>(new BigDecimalCurrencyStringConverter()));
        unitsChoiceBox = new ChoiceBox<>();
        unitsChoiceBox.setTooltip(new Tooltip("Unidades"));

        marginTextField = new TextField();
        marginTextField.setTooltip(new Tooltip("Margen"));
        marginTextField.addEventFilter(KeyEvent.KEY_TYPED, keyEvent -> {
            if (!keyEvent.getCharacter().matches("\\d{0,7}([\\.]\\d{0,4})?")) {
                keyEvent.consume();
            }
        });

        vatTextField = new TextField();
        vatTextField.setTooltip(new Tooltip("IVA"));
        vatTextField.addEventFilter(KeyEvent.KEY_TYPED, keyEvent -> {
            if (!keyEvent.getCharacter().matches("\\d{0,7}([\\.]\\d{0,4})?")) {
                keyEvent.consume();
            }
        });
//        vatTextField.setTextFormatter(
//                new TextFormatter<>(new BigDecimalPercentStringConverter()));

//        marginTextField.setTextFormatter(
//                new TextFormatter<>(new BigDecimalPercentStringConverter()));
        cancelButton = new Button();
    }

    protected void initialize() {
        initOptionValuesDropBox();
        loadOptionValueDropBox();
    }

    protected void initOptionValuesDropBox() {
        choiceBoxService.initChoiceBox(unitsChoiceBox);
        choiceBoxService.initChoiceBox(categoryChoiceBox);
    }

    private void loadOptionValueDropBox() {
        choiceBoxService.fillChoiceBox(unitsChoiceBox, OptionValueSetEnum.UNIT);
        choiceBoxService.fillChoiceBox(categoryChoiceBox, OptionValueSetEnum.CATEGORY);
    }

    public void cancelButtonAction(ActionEvent actionEvent) {
        cancelButton.getScene().getWindow().hide();
    }

    protected boolean validateFields() {
        String errorMessage = "";

        errorMessage = requiredFieldValidator.validateRequiredField(conceptTextField, errorMessage);
        errorMessage = requiredFieldValidator.validateRequiredField(categoryChoiceBox, errorMessage);
        errorMessage = requiredFieldValidator.validateRequiredField(unitsChoiceBox, errorMessage);

        errorMessage = requiredFieldValidator.validateRequiredField(priceTextField, errorMessage);
        errorMessage = requiredFieldValidator.validateRequiredField(marginTextField, errorMessage);
        errorMessage = requiredFieldValidator.validateRequiredField(vatTextField, errorMessage);

        errorMessage = numericFieldValidator.validateDecimalField(priceTextField, errorMessage);
        errorMessage = numericFieldValidator.validateDecimalField(marginTextField, errorMessage);
        errorMessage = numericFieldValidator.validateDecimalField(vatTextField, errorMessage);

        if (errorMessage.length() > 0) {
            MessageBox.show(errorMessage, "Datos no válidos");
            return false;
        }

        return true;
    }

}
