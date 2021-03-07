package com.jskno.budgetgenerator.react;

import com.jskno.budgetgenerator.controllers.TabsController;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class StageListener implements ApplicationListener<StageReadyEvent> {

    private TabsController tabsController;

    @Autowired
    public StageListener(TabsController tabsController) {
        this.tabsController = tabsController;
    }

    @Override
    public void onApplicationEvent(StageReadyEvent stageReadyEvent) {

        System.out.println("Event received !!");
        Stage stage = stageReadyEvent.getStage();
        tabsController.start(stage);

    }
}
