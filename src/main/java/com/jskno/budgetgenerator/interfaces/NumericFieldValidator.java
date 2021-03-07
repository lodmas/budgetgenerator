package com.jskno.budgetgenerator.interfaces;

import javafx.scene.control.TextField;

public interface NumericFieldValidator {

    String validateIntegerField(TextField textField, String errorMessage);
    String validateDecimalField(TextField textField, String errorMessage);

}
