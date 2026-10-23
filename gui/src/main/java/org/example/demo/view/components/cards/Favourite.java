package org.example.demo.view.components.cards;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;

public class Favourite extends Card {
    @FXML private HBox favourite;

    @FXML private Label nameSurname;
    @FXML private Label name;
    @FXML private Label email;
    @FXML private Label id;

    @FXML private RadioButton radio;

    public Favourite() {
        FXMLLoader loader = new FXMLLoader(getClass().
                getResource("favourite.fxml"));
        loader.setController(this);
        try {
            loader.load();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setUserData(Integer id) {
        if (radio != null)
            radio.setUserData(id);
    }

    public Object getUserData() {
        return radio != null ? radio
                .getUserData() : null;
    }

    public void setToggleGroup(ToggleGroup group) {
        if (radio != null)
            radio.setToggleGroup(group);
    }

    public void setNameSurname(String nameSurname) {
        this.nameSurname
                .setText(nameSurname);
    }

    public void setName(String name) {
        this.name.setText(name);
    }

    public void setEmail(String email) {
        this.email.setText(email);
    }

    @Override
    public Parent getRoot() {
        return favourite;
    }
}
