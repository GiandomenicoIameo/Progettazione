package org.example.demo.view.core;

import javafx.application.Application;
import javafx.stage.Stage;
import org.example.demo.view.dock.Administrator;

public class Main extends Application {
    @Override
    public void start(Stage stage) {
        View view = new Administrator();

        Sequence.setCurrStage(stage);
        view.show("administrator.fxml");
    }

    public static void main(String[] args) {
        launch();
    }
}