package com.jskno.budgetgenerator.controllers.budget;

import com.jskno.budgetgenerator.interfaces.ItemService;
import com.jskno.budgetgenerator.model.ui.BudgetItemUI;
import com.jskno.budgetgenerator.model.ui.ItemUI;
import com.jskno.budgetgenerator.utils.Constants;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.text.TextAlignment;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class TileController {

    private ItemService itemService;

    @Autowired
    public TileController(ItemService itemService) {
        this.itemService = itemService;
    }

    private Stage modalStage;
    private static ObservableList<ItemUI> items;
    private static ObservableList<ItemUI> selectedItems;
    private static DropShadow shadow;

    private TilePane tile;
    private ObservableList<VBox> tiles;

    public ObservableList<ItemUI> show(ObservableList<BudgetItemUI> itemsInBudget) {
        init(itemsInBudget);
        loadTilePane();
        return selectedItems;
    }

    public void init(ObservableList<BudgetItemUI> itemsInBudget) {
        items = itemService.loadObservableItemsList();
        if(!itemsInBudget.isEmpty()) {
            List<Long> ids = itemsInBudget.stream()
                    .map(BudgetItemUI::getItemId).collect(Collectors.toList());
            for (Iterator<ItemUI> iterator = items.iterator(); iterator.hasNext(); ) {
                if(ids.contains(iterator.next().getId())) {
                    iterator.remove();
                }
            }
        }
        selectedItems = FXCollections.observableArrayList();
        tiles = FXCollections.observableArrayList();
        shadow = new DropShadow();
    }

    private void loadTilePane() {

        tile = new TilePane();
        tile.setHgap(20);
        tile.setVgap(20);
        tile.setPadding(new Insets(20));
        tile.setPrefColumns(4);

        TextField lookUpField = new TextField();
        lookUpField.setOnKeyReleased(actionEvent -> filterItems(actionEvent));

        HBox lookUpBox = new HBox(lookUpField);
        lookUpBox.setSpacing(40);
        lookUpBox.setAlignment(Pos.CENTER);

        for (ItemUI item : items) {

//            Image img = new Image(f.toURI().toString(),
//                    200, 200, true, true);
//
//            ImageView iview = new ImageView(img);
//            iview.setFitWidth(200);
//            iview.setFitHeight(200);
//            iview.setPreserveRatio(true);

            Button button = new Button(item.getConcept());
            button.setPrefWidth(150);
            button.setPrefHeight(150);
            button.setTooltip(new Tooltip(item.getNotes()));
            button.setTextAlignment(TextAlignment.CENTER);

            button.setOnAction(actionEvent -> processItemSelected(actionEvent, item));

            Region spacer = new Region();

            VBox box = new VBox(10, button, spacer);
            box.setVgrow(spacer, Priority.ALWAYS);
            box.setAlignment(Pos.CENTER);

            tiles.add(box);
            tile.getChildren().add(box);
        }

        ScrollPane scroll = new ScrollPane(tile);
        scroll.setPrefWidth(920);
        scroll.setPrefHeight(450);

        Button cancelButton = new Button("Cancelar");
        cancelButton.setOnAction(actionEvent -> cancelScreen(actionEvent));

        Button addButton = new Button("Añadir Elementos");
        addButton.setOnAction(actionEvent -> selectItems(actionEvent));

        HBox hBox = new HBox(addButton, cancelButton);
        hBox.setSpacing(40);
        hBox.setAlignment(Pos.CENTER);

        VBox vBox = new VBox(lookUpBox, scroll, hBox);
        vBox.setSpacing(40);
        vBox.setAlignment(Pos.CENTER);
        vBox.setPadding(new Insets(40));

        Scene scene = new Scene(vBox);

        modalStage = new Stage();
        modalStage.initModality(Modality.APPLICATION_MODAL);
        modalStage.setScene(scene);
        modalStage.setTitle("Items to add to budget");
        modalStage.setResizable(false);
        modalStage.showAndWait();

    }

    private void cancelScreen(ActionEvent actionEvent) {
        selectedItems.removeAll();
        modalStage.close();
    }

    private void processItemSelected(ActionEvent actionEvent, ItemUI item) {
        Button button = (Button) actionEvent.getSource();
        if(button.getText().contains("X")) {
            selectedItems.remove(item);
            button.setText(item.getConcept());
            button.setEffect(null);
        } else {
            selectedItems.add(item);
            button.setText(item.getConcept() + Constants.LINE_SEPARATOR + Constants.LINE_SEPARATOR
                    + Constants.LINE_SEPARATOR + Constants.LINE_SEPARATOR + "X");
            button.setEffect(shadow);
        }
    }

    private void selectItems(ActionEvent actionEvent) {
        modalStage.close();
    }


    private void filterItems(KeyEvent actionEvent) {
        String lookupText = ((TextField) actionEvent.getSource()).getText();
        if(!lookupText.isEmpty()) {
            ObservableList<VBox> filteredList = FXCollections.observableArrayList();
            tiles.forEach(box -> {
                Button button = (Button) box.getChildren()
                        .filtered(node -> node instanceof Button).get(0);
                if(button.getText().contains(lookupText)) {
                    filteredList.add(box);
                }
            });
            tile.getChildren().clear();
            tile.getChildren().addAll(filteredList);
        } else {
            tile.getChildren().clear();
            tile.getChildren().addAll(tiles);
        }
    }

}
