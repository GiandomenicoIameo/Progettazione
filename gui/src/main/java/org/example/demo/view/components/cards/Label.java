package org.example.demo.view.components.cards;

import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.example.demo.view.core.Component;

public class Label {
    @FXML private Button label;

    @FXML
    public void delete() {
        Pane boxEtichette = (Pane) label.getParent();

        if (boxEtichette != null) {
            // 2. Rimuoviamo l'etichetta dall'HBox
            boxEtichette.getChildren().remove(label);

            // 3. Visto che il tuo HBox non ha testi fissi, se è vuoto lo nascondiamo!
            if (boxEtichette.getChildren().isEmpty()) {
                boxEtichette.setVisible(false);
                boxEtichette.setManaged(false);
            }
        } else {
            System.out.println("Errore: Impossibile trovare l'HBox genitore.");
        }
    }
}
