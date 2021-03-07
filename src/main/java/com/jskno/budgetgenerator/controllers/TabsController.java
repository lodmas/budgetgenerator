package com.jskno.budgetgenerator.controllers;

import com.jskno.budgetgenerator.controllers.budget.BudgetsListController;
import com.jskno.budgetgenerator.controllers.budget.NewBudgetController;
import com.jskno.budgetgenerator.controllers.budget.UpdateBudgetController;
import com.jskno.budgetgenerator.controllers.budget.ViewBudgetController;
import com.jskno.budgetgenerator.controllers.item.ItemsListController;
import com.jskno.budgetgenerator.controllers.optionvalue.OptionValueListController;
import com.jskno.budgetgenerator.react.cancelevents.BudgetCancelOperationEvent;
import com.jskno.budgetgenerator.react.openformevents.BudgetOpenAddFormEvent;
import com.jskno.budgetgenerator.react.openformevents.BudgetOpenModifyFormEvent;
import com.jskno.budgetgenerator.react.openformevents.BudgetOpenViewFromEvent;
import com.jskno.budgetgenerator.react.operationevents.BudgetOperationPerformedEvent;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class TabsController {

    private BudgetsListController budgetsListController;
    private NewBudgetController newBudgetController;
    private UpdateBudgetController updateBudgetController;
    private ViewBudgetController viewBudgetController;

    private ItemsListController itemsListController;
    private OptionValueListController unitsAndCategoriesController;

    TabPane tabPane;

    @Autowired
    public TabsController(BudgetsListController budgetsListController, NewBudgetController newBudgetController,
                          UpdateBudgetController updateBudgetController, ViewBudgetController viewBudgetController,
                          ItemsListController itemsListController, OptionValueListController unitsAndCategoriesController) {
        this.budgetsListController = budgetsListController;
        this.newBudgetController = newBudgetController;
        this.updateBudgetController = updateBudgetController;
        this.viewBudgetController = viewBudgetController;
        this.itemsListController = itemsListController;
        this.unitsAndCategoriesController = unitsAndCategoriesController;
    }

    public void start(Stage stage) {

        Scene scene = new Scene(getRoot());

        stage.setScene(scene);
        stage.setTitle("Budget Design");

        stage.setMinWidth(1275);
        stage.setMinHeight(610);
        stage.setMaximized(true);

        stage.show();

    }

    private Parent getRoot() {
        this.tabPane = new TabPane();
        tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);

        Node budgetsListNode = budgetsListController.start();
        Node itemsNode = itemsListController.start();
        Node optionValueNode = unitsAndCategoriesController.start();

        Tab budgetsListTab = new Tab("Presusupestos", budgetsListNode);
        Tab itemsTab = new Tab("Materiales", itemsNode);
        Tab optionValueTab = new Tab("Unidades y Categorias", optionValueNode);
        tabPane.getTabs().addAll(budgetsListTab, itemsTab, optionValueTab);

        SingleSelectionModel<Tab> selectionModel = tabPane.getSelectionModel();

//        selectionModel.select(budgetsListTab); //select by object
//        selectionModel.select(1); //select by index starting with 0
//        selectionModel.clearSelection(); //clear your selection
        return tabPane;
    }

    /**
     * On pressing Modify button on Budget List Screen
     * @param budgetOpenViewFromEvent
     */
    @EventListener
    public void onApplicationEvent(BudgetOpenViewFromEvent budgetOpenViewFromEvent) {
        Node viewBudgetNode = viewBudgetController.start(budgetOpenViewFromEvent.getBudgetId());
        Tab viewBudgetTab = new Tab("Ver Presupuesto", viewBudgetNode);
        tabPane.getTabs().add(viewBudgetTab);

        tabPane.getSelectionModel().select(3);
    }

    /**
     * On pressing Modify button on Budget List Screen
     * @param budgetOpenModifyFormEvent
     */
    @EventListener
    public void onApplicationEvent(BudgetOpenModifyFormEvent budgetOpenModifyFormEvent) {
        Node modifyBudgetNode = updateBudgetController.start(budgetOpenModifyFormEvent.getBudgetId());
        Tab modifyBudgetTab = new Tab("Modificar Presupuesto", modifyBudgetNode);
        tabPane.getTabs().add(modifyBudgetTab);

        tabPane.getSelectionModel().select(3);
    }

    /**
     * On pressing Add button on Budget List Screen
     * @param budgetOpenAddFormEvent
     */
    @EventListener
    public void onApplicationEvent(BudgetOpenAddFormEvent budgetOpenAddFormEvent) {
        Node addBudgetNode = newBudgetController.start();
        Tab addBudgetTab = new Tab("Añadir Presupuesto", addBudgetNode);
        tabPane.getTabs().add(addBudgetTab);

        tabPane.getSelectionModel().select(3);
    }

    /**
     * On pressing Cancel button on Update Budget Screen
     * @param budgetCancelOperationEvent
     */
    @EventListener
    public void onApplicationEvent(BudgetCancelOperationEvent budgetCancelOperationEvent) {
        tabPane.getSelectionModel().select(0);
        tabPane.getTabs().remove(3);
    }

    /**
     * On saving in the New Budget Screen
     * @param budgetOperationPerformedEvent
     */
    @EventListener
    public void onApplicationEvent(BudgetOperationPerformedEvent budgetOperationPerformedEvent) {
        budgetsListController.reloadTab(budgetOperationPerformedEvent.getBudgetId());
        tabPane.getSelectionModel().select(0);
        tabPane.getTabs().remove(3);
    }

}
