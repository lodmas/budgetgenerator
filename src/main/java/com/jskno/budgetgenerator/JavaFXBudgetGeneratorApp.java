package com.jskno.budgetgenerator;

import com.jskno.budgetgenerator.react.StageReadyEvent;
import javafx.application.Application;
import javafx.application.HostServices;
import javafx.application.Platform;
import javafx.stage.Stage;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.GenericApplicationContext;

public class JavaFXBudgetGeneratorApp extends Application {

    private ConfigurableApplicationContext context;

    @Override
    public void init() {
        initializeSpringFramework();
    }

    @Override
    public void start(Stage primaryStage) {
        this.context.publishEvent(new StageReadyEvent(primaryStage));
    }

    @Override
    public void stop() {
        stopSpringFramework();
        Platform.exit();
    }

    private void initializeSpringFramework() {
        ApplicationContextInitializer<GenericApplicationContext> initializer =
                ac -> {
                    ac.registerBean(Application.class, () -> JavaFXBudgetGeneratorApp.this);
                    ac.registerBean(Parameters.class, this::getParameters);
                    ac.registerBean(HostServices.class, this::getHostServices);
                };

        this.context = new SpringApplicationBuilder()
                .sources(BudgetGeneratorApp.class)
                .initializers(initializer)
                .run(getParameters().getRaw().toArray(new String[0]));
    }

    private void stopSpringFramework() {
        this.context.close();
    }
}
