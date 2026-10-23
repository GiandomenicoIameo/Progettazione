package org.example.demo.view.access;

import javafx.application.Platform;
import javafx.fxml.FXML;

import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import org.example.demo.view.access.factory.DockFactory;
import org.example.demo.view.core.Sequence;
import org.example.demo.view.core.View;
import org.example.demo.view.dto.views.LoginRequestDTO;
import org.example.demo.view.dto.views.LoginResponseDTO;
import org.example.demo.view.service.Authentication;

import java.io.IOException;
import java.util.Map;

public class Login extends View {
    private final Authentication auth =
            new Authentication();

    private final Map<String, DockFactory> map;
    public static LoginResponseDTO response;

    @FXML private AnchorPane    login;
    @FXML private TextField     email;
    @FXML private PasswordField password;

    @FXML
    public void initialize() {
        configureMaximize(login, Sequence
                .getCurrStage());
    }

    public Login(Map<String,DockFactory> map) {
        this.map = map;
    }

    public static String getRoleLogged() {
        return response.getRole();
    }

    private LoginRequestDTO request() {
        LoginRequestDTO request = new LoginRequestDTO();
        request.setEmail(email
                .getText());
        request.setPassword(password
                .getText());
        return request;
    }

    private LoginResponseDTO response() {
        try {
            LoginRequestDTO request  = request();
            return auth.toLogin(request);
        } catch (IOException e) {
            // Impossibile connettersi al server.
            //  Riprova più tardi"
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            // Il processo di login è stato interrotto inaspettatamente.
            throw new RuntimeException(e);
        }
    }

    @FXML
    public void signIn() {
        LoginResponseDTO response = response();
        Login.response = response;

        DockFactory dockFactory = map
                .get(response.getRole());

        if (dockFactory != null) {
            Platform.runLater(() -> {
                Sequence.skip();
                dockFactory
                        .createDock();
            });
       }
        // else {
        // non esiste quel ruolo!
        // }
    }
}
