package com.jskno.budgetgenerator.controllers.optionvalue;

import com.jskno.budgetgenerator.interfaces.*;
import com.jskno.budgetgenerator.model.database.OptionValue;
import com.jskno.budgetgenerator.model.database.OptionValueSetEnum;
import com.jskno.budgetgenerator.model.ui.OptionValueSetUI;
import com.jskno.budgetgenerator.model.ui.OptionValueUI;
import com.jskno.budgetgenerator.react.OptionValuesChangedEvent;
import com.jskno.budgetgenerator.converters.DtoToEntityConverter;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class NewOptionValueController extends AbstractOptionValueController {

    private OptionValueSetService optionValueSetService;

    @Autowired
    public NewOptionValueController(OptionValueService optionValueService,
            OptionValueSetService optionValueSetService, ChoiceBoxService choiceBoxService,
            RequiredFieldValidator requiredFieldValidator, NumericFieldValidator numericFieldValidator,
            ApplicationEventPublisher applicationEventPublisher) {

        super(optionValueService, choiceBoxService, requiredFieldValidator,
                numericFieldValidator, applicationEventPublisher);
        this.optionValueSetService = optionValueSetService;
    }

    private Button saveButton;

    public OptionValueUI start(Long optionValueSetId) {

        OptionValueSetUI optionValueSetUI = optionValueSetService.getOptionValueSetUIById(optionValueSetId);

        Stage modalStage = new Stage();
        modalStage.setScene(new Scene(getRoot()));
        modalStage.setResizable(false);
        modalStage.initModality(Modality.APPLICATION_MODAL);

        initialize(optionValueSetUI);

        modalStage.showAndWait();
        return optionValueUI;
    }

    @Override
    protected ButtonBar buildButtonBar() {
        ButtonBar buttonBar = new ButtonBar();
        buttonBar.setPadding(new Insets(30));
        saveButton = new Button("Guardar");
        saveButton.setOnAction(actionEvent -> saveButtonAction(actionEvent));
        cancelButton = new Button("Cancelar");
        cancelButton.setOnAction(actionEvent -> cancelButtonAction(actionEvent));
        buttonBar.getButtons().addAll(saveButton, cancelButton);

        return buttonBar;
    }

    public void saveButtonAction(ActionEvent actionEvent) {

        if(!validateFields()) {
            return;
        }

        optionValueUI = new OptionValueUI();

        optionValueUI.setOptionValueSetUI(optionValueSetUIChoiceBox.getValue());
        optionValueUI.setValue(valueTextField.getText());
        optionValueUI.setLabel(labelTextField.getText());
        if (positionTextField.getText() != null) {
            optionValueUI.setPosition(Integer.valueOf(positionTextField.getText()));
        }
        optionValueUI.setDisable(disableCheckBox.isDisable());


        OptionValue optionValue = DtoToEntityConverter.getOptionValue(optionValueUI);
        optionValue = optionValueService.saveOptionValue(optionValue);
        optionValueUI.setId(optionValue.getId());
        applicationEventPublisher.publishEvent(new OptionValuesChangedEvent(
                OptionValueSetEnum.getFromId(optionValueUI.getOptionValueSetUI().getId()),
                "OptionValueCreated"));

        saveButton.getScene().getWindow().hide();
    }

}
