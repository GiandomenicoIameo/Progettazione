package org.example.demo.view.components.bars.admin;

import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import org.example.demo.view.core.Component;
import org.example.demo.view.dto.views.UserDTO;
import org.example.demo.view.service.UserService;

import java.io.IOException;

public class Settingsbar extends Component {
    UserService userService = new UserService();

    @FXML private VBox settingsbar;
    @FXML private Pane avatarPane;

    @FXML private Label name;
    @FXML private Label email;
    @FXML private Label role;

    @FXML private Label admin;
    @FXML private Label user;

    private Label current = null;

    @FXML
    public void initialize() throws IOException,
            InterruptedException {
        Circle clip = new Circle(80, 80, 80);
        avatarPane.setClip(clip);
        settingsbar.setOnMouseClicked((MouseEvent event) -> {
            if (event.getClickCount() == 2) {
                Stage stage = (Stage) settingsbar
                        .getScene().getWindow();
                stage.setMaximized(!stage.isMaximized());
            }
        });
        populate();
    }

    @FXML
    public void close() {
        System.exit(0);
    }

    @FXML
    public void minimize() {
        Stage stage = (Stage) settingsbar.
                getScene().getWindow();
        stage.setIconified(true);
    }

    public void setName(String name) {
        this.name.setText(name);
    }

    public void setEmail(String email) {
        this.email.setText(email);
    }

    public void setRole(String role) {
        if(role.equals("AMMINISTRATORE"))
            setAdmin();
        else
            setUser();
    }

    public void populate() throws IOException,
            InterruptedException {
        UserDTO user = userService.me();

        setName(user.getName());
        setEmail(user.getEmail());
        setRole(user.getRole());
    }

    private void setLabels(Label labels) {
        if(current != null) {
            current.setManaged(false);
            current.setVisible(false);
        }
        current = labels;
        current.setManaged(true);
        current.setVisible(true);
    }

    public void setAdmin() {
        setLabels(admin);
    }

    public void setUser() {
        setLabels(user);
    }

    @Override
    public Parent getRoot() {
        return settingsbar;
    }
}
