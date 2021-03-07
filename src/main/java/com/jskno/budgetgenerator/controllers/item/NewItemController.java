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
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class NewItemController extends AbstractItemController {

    private Button saveButton;

    @Autowired
    public NewItemController(ItemService itemService, ChoiceBoxService choiceBoxService,
                 RequiredFieldValidator requiredFieldValidator, NumericFieldValidator numericFieldValidator) {
        super(itemService, choiceBoxService, requiredFieldValidator, numericFieldValidator);
    }

    public ItemUI start() {

//        this.itemUI = new ItemUI();

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
//        buttonBar.setPrefWidth(353);
//        buttonBar.setPrefHeight(75);
        buttonBar.setPadding(new Insets(30));
        saveButton = new Button("Guardar");
        saveButton.setOnAction(actionEvent -> saveButtonAction(actionEvent));
        cancelButton = new Button("Cancelar");
        cancelButton.setOnAction(actionEvent -> cancelButtonAction(actionEvent));
        buttonBar.getButtons().addAll(saveButton, cancelButton);

        return buttonBar;
    }

    public void saveButtonAction(ActionEvent actionEvent) {
        System.out.println("New Item Saved");

        if(!validateFields()) {
            return;
        }

        BigDecimal price = null;
        if (priceTextField.getText() != null) {
            price = new BigDecimal(priceTextField.getText());
        }
        BigDecimal margin = null;
        if (marginTextField.getText() != null) {
            margin = new BigDecimal(marginTextField.getText());
        }
        BigDecimal vat = null;
        if (vatTextField.getText() != null) {
            vat = new BigDecimal(vatTextField.getText());
        }

        itemUI = new ItemUI(
                conceptTextField.getText(),
                categoryChoiceBox.getValue(),
                price,
                vat,
                margin,
                unitsChoiceBox.getValue(),
                notesTextArea.getText());
        Item item = DtoToEntityConverter.getItem(itemUI);
        item = itemService.saveItem(item);
        itemUI.setId(item.getId());

        saveButton.getScene().getWindow().hide();
    }

}
