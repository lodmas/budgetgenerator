package com.jskno.budgetgenerator.services;

import com.jskno.budgetgenerator.controllers.item.UpdateItemController;
import com.jskno.budgetgenerator.interfaces.SwitchScreenService;
import com.jskno.budgetgenerator.model.ui.ItemUI;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class SwitchScreenServiceImpl implements SwitchScreenService {

    @Override
    public void showNextScreen(Node currentScreenNode, String screenPath) {
        currentScreenNode.getScene().getWindow().hide();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource(screenPath));

        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Parent root = loader.getRoot();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));

        stage.show();
//        stage.showAndWait();
    }

    @Override
    public void showUpdateItemScreen(Node currentScreenNode, String screenPath, ItemUI selectedItem) {
        currentScreenNode.getScene().getWindow().hide();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource(screenPath));


        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        UpdateItemController itemUpdateController = loader.getController();
//        itemUpdateController.setSelectedItem(selectedItem);

        Parent root = loader.getRoot();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));

        stage.show();
//        stage.showAndWait();
    }
}
