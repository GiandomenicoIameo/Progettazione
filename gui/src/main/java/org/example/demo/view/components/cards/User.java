package org.example.demo.view.components.cards;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.example.demo.view.core.Component;

public class User extends Component {
    @FXML private HBox account;
    @FXML private VBox cardContainer;

    @FXML private Label admin;
    @FXML private Label user;
    @FXML private Label userBold;
    @FXML private Label userRegular;
    @FXML private Label email;

    private Label current = null;

    public User() {
        FXMLLoader loader = new FXMLLoader(getClass().
                getResource("user.fxml"));
        loader.setController(this);
        try {
            loader.load();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setLabels(Label labels) {
        if(current != null)
            current.setVisible(false);
        current = labels;
        current.setVisible(true);
    }

    public void setAdmin() {
        setLabels(admin);
    }

    public void setUser() {
        setLabels(user);
    }

    public void setUsernameBold(String username) {
        userBold.setText(username);
    }

    public void setUsernameRegular(String username) {
        userRegular.setText(username);
    }

    public void setEmail(String email) {
        this.email
                .setText(email);
    }

    @Override
    public Parent getRoot() {
        return account;
    }
}
