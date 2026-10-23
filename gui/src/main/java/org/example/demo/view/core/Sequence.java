package org.example.demo.view.core;

import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Sequence {
    private static Stage current  = null;
    private static Stage previous = null;

    public static void setCurrStage(Stage stage) {
        current = stage;
    }

    public static void setPrevStage(Stage stage) {
        previous = stage;
    }

    public static Stage getCurrStage() {
        return current;
    }

    public static Stage getPrevStage() {
        return previous;
    }

    private static void setTransparent(Scene scene) {
        setTransparentScene(scene);
        setTransparentStage();
    }

    private static void setTransparentStage() {
        current.initStyle(StageStyle.TRANSPARENT);
    }

    private static void setTransparentScene(Scene scene) {
        scene.setFill(Color.TRANSPARENT);
    }

    public static void viewCurrStage(Scene scene) {
        current.setScene(scene);
        setTransparent(scene);
        current.show();
    }

    public static void view() {
        if(current != null && current
                .getScene() != null)
            current.show();
    }

    public static void skip() {
        Stage oldStage = getCurrStage();
        setPrevStage(oldStage);

        oldStage.close();

        Stage newStage = new Stage();
        Sequence.setCurrStage(newStage);
    }
}
