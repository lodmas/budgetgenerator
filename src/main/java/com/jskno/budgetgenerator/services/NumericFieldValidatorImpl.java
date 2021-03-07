package com.jskno.budgetgenerator.services;

import com.jskno.budgetgenerator.interfaces.NumericFieldValidator;
import javafx.scene.control.TextField;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class NumericFieldValidatorImpl implements NumericFieldValidator {

    public String validateIntegerField(TextField textField, String errorMessage) {
        if(textField.getText() != null && !textField.getText().isEmpty()) {
            try {
                Integer.parseInt(textField.getText());
            } catch (NumberFormatException e) {
                errorMessage += "\n" + textField.getTooltip().getText() +
                        " debe ser un número entero válido.";
            }
        }
        return errorMessage;
    }

    public String validateDecimalField(TextField textField, String errorMessage) {
        if(textField.getText() != null && !textField.getText().isEmpty()) {
//            BigDecimal price = (BigDecimal) textField.getTextFormatter()
//                    .getValueConverter().fromString(textField.getText());
            try {
                new BigDecimal(textField.getText());
            } catch (NumberFormatException e) {
                errorMessage += "\n" + textField.getTooltip().getText() +
                        " debe ser un número decimal válido.";
            }
        }
        return errorMessage;
    }
}
