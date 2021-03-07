package com.jskno.budgetgenerator.services;

import com.jskno.budgetgenerator.interfaces.RequiredFieldValidator;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import org.springframework.stereotype.Service;

@Service
public class RequiredFieldValidatorImpl implements RequiredFieldValidator {


    @Override
    public String validateRequiredField(TextField textField, String errorMessage) {
        if (textField.getText() == null || textField.getText().length() == 0) {
            errorMessage += "\n" + textField.getTooltip().getText() + " es un campo obligatorio.";
        }
        return errorMessage;
    }

    @Override
    public String validateRequiredField(ChoiceBox choiceBox, String errorMessage) {
        if (choiceBox.getValue() == null) {
            errorMessage += "\n" + choiceBox.getTooltip().getText() + " es un campo obligatorio.";
        }
        return errorMessage;
    }
}
