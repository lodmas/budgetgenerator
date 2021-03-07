package com.jskno.budgetgenerator.controllers.item;

import com.jskno.budgetgenerator.interfaces.ChoiceBoxService;
import com.jskno.budgetgenerator.interfaces.ItemService;
import com.jskno.budgetgenerator.interfaces.NumericFieldValidator;
import com.jskno.budgetgenerator.interfaces.RequiredFieldValidator;
import com.jskno.budgetgenerator.model.database.Item;
import com.jskno.budgetgenerator.model.ui.ItemUI;
import com.jskno.budgetgenerator.converters.DtoToEntityConverter;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class UpdateItemController extends AbstractItemController {

    private Button updateButton;

    @Autowired
    public UpdateItemController(ItemService itemService, ChoiceBoxService choiceBoxService,
                RequiredFieldValidator requiredFieldValidator, NumericFieldValidator numericFieldValidator) {
        super(itemService, choiceBoxService, requiredFieldValidator, numericFieldValidator);
    }

    public ItemUI start(Long selectedItemId) {


        this.itemUI = itemService.getOptionValueUIById(selectedItemId);

        Stage modalStage = new Stage();
        modalStage.setScene(new Scene(getRoot()));
        modalStage.setResizable(false);
        modalStage.initModality(Modality.APPLICATION_MODAL);

        initialize();

        modalStage.showAndWait();
        return itemUI;
    }

    @Override
    protected ButtonBar buildButtonBar() {
        ButtonBar buttonBar = new ButtonBar();
        buttonBar.setPrefWidth(353);
        buttonBar.setPrefHeight(75);
        buttonBar.setPadding(new Insets(30));
        updateButton = new Button("Guardar");
        updateButton.setOnAction(actionEvent -> updateButtonAction(actionEvent));
        cancelButton.setText("Cancelar");
        cancelButton.setOnAction(actionEvent -> cancelButtonAction(actionEvent));
        buttonBar.getButtons().addAll(updateButton, cancelButton);

        return buttonBar;
    }

    @Override
    protected void initialize() {
        populateFieldsWithSelectedItem(this.itemUI);
        super.initialize();
    }

    private void populateFieldsWithSelectedItem(ItemUI selectedItem) {
        // Populate controls with selected Person
        conceptTextField.setText(selectedItem.getConcept());
        notesTextArea.setText(selectedItem.getNotes());
        categoryChoiceBox.getSelectionModel().select(selectedItem.getCategory());
        unitsChoiceBox.getSelectionModel().select(selectedItem.getUnits());
        priceTextField.setText(String.valueOf(selectedItem.getPrice()));
        marginTextField.setText(String.valueOf(selectedItem.getMargin()));
        vatTextField.setText(String.valueOf(selectedItem.getVat()));
    }

    public void updateButtonAction(ActionEvent actionEvent) {
        System.out.println("Update " + itemUI);
//        Item p = listView.getSelectionModel().getSelectedItem();
//        listView.getSelectionModel().selectedItemProperty().removeListener(itemChangeListener);

        if(!validateFields()) {
            return;
        }

        itemUI.setConcept(conceptTextField.getText());
        itemUI.setNotes(notesTextArea.getText());
        itemUI.setCategory(categoryChoiceBox.getValue());
        itemUI.setUnits(unitsChoiceBox.getValue());
        if (priceTextField.getText() != null) {
            itemUI.setPrice(new BigDecimal(priceTextField.getText()));
        }
        if (marginTextField.getText() != null) {
            itemUI.setMargin(new BigDecimal(marginTextField.getText()));
        }
        if (vatTextField.getText() != null) {
            itemUI.setVat(new BigDecimal(vatTextField.getText()));
        }


        Item item = DtoToEntityConverter.getItem(itemUI);
        itemService.updateItem(item);
//        listView.getSelectionModel().selectedItemProperty().addListener(itemChangeListener);
//        modifiedProperty.set(false);

        // and select it
//        listView.getSelectionModel().select(item);

        updateButton.getScene().getWindow().hide();
    }

}
