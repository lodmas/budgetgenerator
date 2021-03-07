package com.jskno.budgetgenerator.utils;

import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;

public class TextFieldValidator {

    public static void integerValidation(TextField positionTextField, KeyEvent keyEvent) {
        if (!keyEvent.getCode().isDigitKey()) {
            positionTextField.cancelEdit();
        }
    }
}
