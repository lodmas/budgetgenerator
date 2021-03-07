package com.jskno.budgetgenerator.controllers.budget;

import com.jskno.budgetgenerator.interfaces.BudgetService;
import com.jskno.budgetgenerator.interfaces.ConfigurationService;
import com.jskno.budgetgenerator.interfaces.RequiredFieldValidator;
import com.jskno.budgetgenerator.model.ui.BudgetUI;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class NewBudgetController extends AbstractBudgetController {

    @Autowired
    public NewBudgetController(ApplicationEventPublisher applicationEventPublisher,
               TileController tileController, BudgetService budgetService,
               ConfigurationService configurationService, RequiredFieldValidator requiredFieldValidator) {
        super(applicationEventPublisher, tileController, budgetService, configurationService, requiredFieldValidator);
    }

    public Node start() {
        initializeObjects();
        initialiseData();
        VBox root = getRoot();
        return root;
    }

    private void initialiseData() {
        budgetUI = new BudgetUI();
        fillFields(budgetUI);
    }

    protected HBox buildActionSection() {

        Button btnAdd = new Button("Añadir Linea");
        btnAdd.setMinWidth(60);
        btnAdd.setOnAction(e -> btnAdd_Clicked());

        Button btnSave = new Button("Guardar Presupuesto");
        btnSave.setMinWidth(60);
        btnSave.setOnAction(e -> btnSaveOrUpdate_Clicked());

        Button btnCancel = new Button("Cancelar");
        btnCancel.setMinWidth(60);
        btnCancel.setOnAction(e -> btnCancel_Clicked());

        Region region = new Region();
        HBox.setHgrow(region, Priority.ALWAYS);

        HBox actionHBox = new HBox();
        actionHBox.setSpacing(8);
        actionHBox.getChildren().addAll(btnAdd, region, btnSave, btnCancel);

        return actionHBox;
    }

}