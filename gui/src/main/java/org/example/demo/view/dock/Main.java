package org.example.demo.view.dock;

import javafx.application.Application;
import javafx.stage.Stage;
import org.example.demo.view.core.Sequence;

/*
    Il seguente metodo viene utilizzato per visualizzare
    la schermata homepage relativa agli Amministratori,
    utenti normali e agli Stakeholder.
 */

public class Main extends Application {
    @Override
    public void start(Stage stage) {
        Dock dock = new Administrator();

        Sequence.setCurrStage(stage);
        dock.show("administrator.fxml");
    }

    public static void main(String[] args) {
        launch();
    }
}