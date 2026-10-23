package org.example.demo.view.components.bars;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.example.demo.view.core.Component;
import org.example.demo.view.listeners.TopbarListener;

import java.io.IOException;

public class Topbar extends Component {
    @FXML private AnchorPane topbar;

    private TopbarListener listener;

    public void setListener(TopbarListener listener) {
        this.listener = listener;
    }

    @FXML
    public void viewSearchbar() {
        if(listener != null)
            listener.viewSearchbar();
    }

    @FXML
    public void initialize() {
        topbar.setOnMouseClicked((MouseEvent event) -> {
            if (event.getClickCount() == 2) {
                Stage stage = (Stage) topbar.getScene().getWindow();

                // Se è massimizzata la rimpicciolisce, se è piccola la massimizza
                stage.setMaximized(!stage.isMaximized());
            }
        });
    }

    @FXML
    public void showIssue() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass()
                    .getResource("/org/example/demo/view/components/modal/create-issue.fxml"));
            Parent root = loader.load();

            Scene scenaModale = new Scene(root);
            scenaModale.setFill(Color.TRANSPARENT);

            Stage modale = new Stage();

            modale.initStyle(StageStyle.UNDECORATED);
            modale.setScene(scenaModale);

            modale.initModality(Modality.APPLICATION_MODAL);
            modale.setOnShown(event -> {
                Rectangle2D limitiSchermo = Screen.getPrimary().getVisualBounds();

                double centroX = (limitiSchermo.getWidth() - modale.getWidth()) / 2;
                double centroY = (limitiSchermo.getHeight() - modale.getHeight()) / 2;

                modale.setX(centroX);
                modale.setY(centroY);
            });

            ColorAdjust oscuramento = new ColorAdjust();
            oscuramento.setBrightness(-0.09);
            topbar.getScene().getRoot().setEffect(oscuramento);

            modale.showAndWait();

            topbar.getScene().getRoot().setEffect(null);

        } catch (IOException e) {
            System.err.println("Errore nel caricamento del file labels.fxml");
            e.printStackTrace();
        }
    }

    @FXML
    public void close() {
        System.exit(0);
    }

    @Override
    public Parent getRoot() {
        return topbar;
    }
}
