package com.jskno.budgetgenerator.controllers.optionvalue;

import com.jskno.budgetgenerator.interfaces.ChoiceBoxService;
import com.jskno.budgetgenerator.interfaces.NumericFieldValidator;
import com.jskno.budgetgenerator.interfaces.OptionValueService;
import com.jskno.budgetgenerator.interfaces.RequiredFieldValidator;
import com.jskno.budgetgenerator.modalmessages.MessageBox;
import com.jskno.budgetgenerator.model.ui.OptionValueSetUI;
import com.jskno.budgetgenerator.model.ui.OptionValueUI;
import com.jskno.budgetgenerator.utils.TextFieldValidator;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.VPos;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import org.springframework.context.ApplicationEventPublisher;

public abstract class AbstractOptionValueController {

    protected OptionValueService optionValueService;
    protected ChoiceBoxService choiceBoxService;
    protected RequiredFieldValidator requiredFieldValidator;
    protected NumericFieldValidator numericFieldValidator;
    protected ApplicationEventPublisher applicationEventPublisher;

    public AbstractOptionValueController(OptionValueService optionValueService, ChoiceBoxService choiceBoxService,
                 RequiredFieldValidator requiredFieldValidator, NumericFieldValidator numericFieldValidator,
                 ApplicationEventPublisher applicationEventPublisher) {

        this.optionValueService = optionValueService;
        this.choiceBoxService = choiceBoxService;
        this.requiredFieldValidator = requiredFieldValidator;
        this.numericFieldValidator = numericFieldValidator;
        this.applicationEventPublisher = applicationEventPublisher;
    }

    protected TextField valueTextField;
    protected TextField labelTextField;
    protected TextField positionTextField;
    protected CheckBox disableCheckBox;

    protected Button cancelButton;

    protected ChoiceBox<OptionValueSetUI> optionValueSetUIChoiceBox;

    protected OptionValueUI optionValueUI;

    protected Parent getRoot() {

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

        Label optionValueSetLabel = new Label("Option Value Set");
        optionValueSetUIChoiceBox = new ChoiceBox<>();
        optionValueSetUIChoiceBox.setDisable(true);
        optionValueSetUIChoiceBox.setStyle("-fx-opacity: 0.8;");
        Label valueLabel = new Label("Value");
        valueTextField = new TextField();
        valueTextField.setTooltip(new Tooltip("Value"));
        Label labelLabel = new Label("Label");
        labelTextField = new TextField();
        labelTextField.setTooltip(new Tooltip("Label"));
        Label positionLabel = new Label("Position");
        positionTextField = new TextField();
        positionTextField.setTooltip(new Tooltip("Posición"));
//        positionTextField.setOnKeyPressed(keyEvent -> TextFieldValidator
//                .integerValidation(positionTextField, keyEvent));
//        positionTextField.textProperty().addListener(new ChangeListener<String>() {
//            @Override
//            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
//                if(!t1.matches("[0-9]*")) {
//                    ((TextField)observableValue).textProperty().setValue(s);
//                }
//            }
//        });
        positionTextField.addEventFilter(KeyEvent.KEY_TYPED, keyEvent -> {
            if (!keyEvent.getCharacter().matches("[0-9]*")) {
                keyEvent.consume();
            }
        });
        Label disableLabel = new Label("Disable");
        disableCheckBox = new CheckBox();

        formGridPane.setHalignment(optionValueSetLabel, HPos.LEFT);
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

        formGridPane.addRow(0, optionValueSetLabel, optionValueSetUIChoiceBox);
        formGridPane.addRow(1, valueLabel, valueTextField);
        formGridPane.addRow(2, labelLabel, labelTextField);
        formGridPane.addRow(3, positionLabel, positionTextField);
        formGridPane.addRow(4, disableLabel, disableCheckBox);

        return formGridPane;
    }

    protected abstract ButtonBar buildButtonBar();

    protected void initialize(OptionValueSetUI optionValueSetUI) {
        choiceBoxService.fillOptionValueSetUIChoiceBox(optionValueSetUIChoiceBox);
        optionValueSetUIChoiceBox.getSelectionModel().select(optionValueSetUI);
    }

    public void cancelButtonAction(ActionEvent actionEvent) {
        cancelButton.getScene().getWindow().hide();
    }

    protected boolean validateFields() {
        String errorMessage = "";

        errorMessage = requiredFieldValidator.validateRequiredField(valueTextField, errorMessage);
        errorMessage = requiredFieldValidator.validateRequiredField(labelTextField, errorMessage);
        errorMessage = requiredFieldValidator.validateRequiredField(positionTextField, errorMessage);

        errorMessage = numericFieldValidator.validateIntegerField(positionTextField, errorMessage);

        if (errorMessage.length() > 0) {
            MessageBox.show(errorMessage, "Datos no válidos");
            return false;
        }

        return true;
    }
}
