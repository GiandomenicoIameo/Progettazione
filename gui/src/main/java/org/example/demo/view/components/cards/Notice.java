package org.example.demo.view.components.cards;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import org.example.demo.view.support.Date;
import java.time.LocalDateTime;

public class Notice extends Card {
    @FXML private HBox notice;

    @FXML private Label id;
    @FXML private Label date;

    public Notice() {
        FXMLLoader loader = new FXMLLoader(getClass().
                getResource("notice.fxml"));
        loader.setController(this);
        try {
            loader.load();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setId(Integer id) {
        this.id.setText(id.toString());
    }

    public void setDate(LocalDateTime date) {
        Date d = new Date(date);
        this.date.setText(d.toString());
    }

    @Override
    public Parent getRoot() {
        return notice;
    }
}
