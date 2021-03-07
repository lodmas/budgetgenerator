package com.jskno.budgetgenerator.interfaces;

import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;

public interface RequiredFieldValidator {

    String validateRequiredField(TextField textField, String errorMessage);

    String validateRequiredField(ChoiceBox choiceBox, String errorMessage);
}
