package org.example.demo.view.components.modal;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.example.demo.view.components.cards.Favourite;
import org.example.demo.view.core.Component;
import org.example.demo.view.dto.cards.IssueCardResponseDTO;
import org.example.demo.view.dto.views.RegularDTO;
import org.example.demo.view.service.IssueService;
import org.example.demo.view.service.UserService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class Assignment extends Component {
    UserService userService = new UserService();
    IssueService issueService = new IssueService();

    @FXML private AnchorPane assignment;
    @FXML private VBox cardContainer;
    @FXML private ScrollPane scroll;

    private Integer bugId;
    private ToggleGroup group = new ToggleGroup();

    private Consumer<Boolean> callback;

    @FXML
    public void initialize() throws IOException,
            InterruptedException {
        importCard();
    }

    public void setCallback(Consumer<Boolean> callback) {
        this.callback = callback;
    }

    public void initData(int bugId) {
        this.bugId = bugId;
    }

    public void setNameSurname(Favourite favourite,
                            RegularDTO response) {
        favourite.setNameSurname(response.getName() +
                " " + response.getSurname());
    }

    public void setName(Favourite favourite,
                               RegularDTO response) {
        favourite.setName(response.getName());
    }

    public void setEmail(Favourite favourite,
                             RegularDTO response) {
        favourite.setEmail(response
                .getEmail());
    }

    public List<Favourite> convert() throws IOException,
            InterruptedException {
        List<Favourite> list = new ArrayList<>();

        this.userService.suggested()
                .forEach(user -> {
                    Favourite favourite = new Favourite();
                    favourite.setToggleGroup(group);
                    favourite.setUserData(user.getId());

                    setNameSurname(favourite, user);
                    setName(favourite, user);
                    setEmail(favourite, user);

                    list.add(favourite);
                }); return list;
    }

    public void importCard() throws IOException,
            InterruptedException {
        List<Favourite> list = convert();

        scroll.setVisible(true);
        list.forEach(card -> {
            cardContainer.getChildren().
                    add(card.getRoot());
        });
    }

    @FXML
    public void handleAssign() throws IOException,
            InterruptedException {
        Integer userId = null;

        if (group.getSelectedToggle() != null) {
            userId = (Integer) group
                    .getSelectedToggle().getUserData();
        }

        IssueCardResponseDTO response = issueService
                .assign(bugId, userId);

        if (callback != null)
            callback.accept(response != null);
        closeWindow();
    }

    public void closeWindow() {
        Stage stage = (Stage) assignment
                .getScene().getWindow();
        stage.close();
    }

    @FXML
    public void close(ActionEvent event) {
        Stage stage = (Stage) assignment
                .getScene().getWindow();
        stage.close();
    }
}
