package com.jskno.budgetgenerator.interfaces;

import com.jskno.budgetgenerator.model.ui.ItemUI;
import javafx.scene.Node;

public interface SwitchScreenService {

    void showNextScreen(Node currentScreenNode, String screenPath);

    void showUpdateItemScreen(Node currentScreenNode, String screenPath, ItemUI selectedItem);
}
