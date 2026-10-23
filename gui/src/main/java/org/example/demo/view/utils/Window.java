package org.example.demo.view.utils;

import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.example.demo.view.core.Sequence;

public abstract class Window {
    @FXML
    public void close() {
        System.exit(0);
    }

    @FXML
    public void minimize() {
        Sequence.getCurrStage()
                .setIconified(true);
    }

    @FXML
    public void configureMaximize(Parent root, Stage stage) {
        root.setOnMouseClicked(
                event -> {
            if (event.getClickCount() == 2) {
                stage.setMaximized(!stage
                        .isMaximized());
            }
        });
    }

    public void fade(Parent root) {
        FadeTransition fadeIn = new
                FadeTransition(Duration
                .millis(1000), root);
        fadeIn.setToValue(1.0);
        fadeIn.play();
    }
}
