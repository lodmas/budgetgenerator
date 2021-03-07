package com.jskno.budgetgenerator.controllers.optionvalue;

import com.jskno.budgetgenerator.interfaces.ChoiceBoxService;
import com.jskno.budgetgenerator.interfaces.NumericFieldValidator;
import com.jskno.budgetgenerator.interfaces.OptionValueService;
import com.jskno.budgetgenerator.interfaces.RequiredFieldValidator;
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
public class UpdateOptionValueController extends AbstractOptionValueController {

    @Autowired
    public UpdateOptionValueController(OptionValueService optionValueService,
           ChoiceBoxService choiceBoxService, RequiredFieldValidator requiredFieldValidator,
           NumericFieldValidator numericFieldValidator, ApplicationEventPublisher applicationEventPublisher) {

        super(optionValueService, choiceBoxService, requiredFieldValidator, numericFieldValidator,
                applicationEventPublisher);
    }

    private Button updateButton;

    public OptionValueUI start(Long selectedItemId) {

        this.optionValueUI = optionValueService.getOptionValueUIById(selectedItemId);

        Stage modalStage = new Stage();
        modalStage.setScene(new Scene(getRoot()));
        modalStage.setResizable(false);
        modalStage.initModality(Modality.APPLICATION_MODAL);

        initialize(optionValueUI.getOptionValueSetUI());

        modalStage.showAndWait();
        return optionValueUI;
    }

    @Override
    protected ButtonBar buildButtonBar() {

        ButtonBar buttonBar = new ButtonBar();
        buttonBar.setPadding(new Insets(30));
        updateButton = new Button("Actualizar");
        updateButton.setOnAction(actionEvent -> updateButtonAction(actionEvent));
        cancelButton = new Button("Cancelar");
        cancelButton.setOnAction(actionEvent -> cancelButtonAction(actionEvent));
        buttonBar.getButtons().addAll(updateButton, cancelButton);

        return buttonBar;
    }

    @Override
    protected void initialize(OptionValueSetUI optionValueSetUI) {
        populateFieldsWithSelectedItem(this.optionValueUI);
        super.initialize(optionValueSetUI);
    }

    private void populateFieldsWithSelectedItem(OptionValueUI selectedItem) {
        // Populate controls with selected OptionValue
        optionValueSetUIChoiceBox.getSelectionModel().select(selectedItem.getOptionValueSetUI());
        valueTextField.setText(selectedItem.getValue());
        labelTextField.setText(selectedItem.getLabel());
        positionTextField.setText(String.valueOf(selectedItem.getPosition()));
        disableCheckBox.setSelected(selectedItem.isDisable());
    }

    public void updateButtonAction(ActionEvent actionEvent) {
        System.out.println("Update " + optionValueUI);

        if(!validateFields()) {
            return;
        }

        optionValueUI.setOptionValueSetUI(optionValueSetUIChoiceBox.getValue());
        optionValueUI.setValue(valueTextField.getText());
        optionValueUI.setLabel(labelTextField.getText());
        if (positionTextField.getText() != null) {
            optionValueUI.setPosition(Integer.valueOf(positionTextField.getText()));
        }
        optionValueUI.setDisable(disableCheckBox.isDisable());


        OptionValue optionValue = DtoToEntityConverter.getOptionValue(optionValueUI);
        optionValueService.updateOptionValue(optionValue);
//        listView.getSelectionModel().selectedItemProperty().addListener(itemChangeListener);
//        modifiedProperty.set(false);

        // and select it
//        listView.getSelectionModel().select(item);

//        switchScreenService.showNextScreen(updateButton, "/fxml/itemsView.fxml");

        applicationEventPublisher.publishEvent(new OptionValuesChangedEvent(
                OptionValueSetEnum.getFromId(optionValueUI.getOptionValueSetUI().getId()),
                "OptionValueUpdated"));
        updateButton.getScene().getWindow().hide();
    }

}
