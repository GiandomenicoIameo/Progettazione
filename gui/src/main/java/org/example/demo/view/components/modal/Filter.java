package org.example.demo.view.components.modal;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.example.demo.view.components.cards.IssueCardAdmin;
import org.example.demo.view.core.Component;
import javafx.event.ActionEvent;
import org.example.demo.view.dto.cards.IssueCardResponseDTO;
import org.example.demo.view.service.IssueService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class Filter extends Component {
    private IssueService issueService = new IssueService();

    @FXML private AnchorPane cardFilter;

    @FXML private RadioButton high;
    @FXML private RadioButton medium;
    @FXML private RadioButton low;

    @FXML private RadioButton todo;
    @FXML private RadioButton closed;

    @FXML private RadioButton bug;
    @FXML private RadioButton documentation;
    @FXML private RadioButton feature;
    @FXML private RadioButton question;

    @FXML private ToggleGroup priorityGroup;
    @FXML private ToggleGroup stateGroup;
    @FXML private ToggleGroup typeGroup;

    private Consumer<List<IssueCardResponseDTO>> callback;

    @FXML
    public void close(ActionEvent event) {
        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene()
                .getWindow();
        stage.close();
    }

    public void setCallback(Consumer<List<IssueCardResponseDTO>> callback) {
        this.callback = callback;
    }

    @FXML
    public void search()  throws IOException,
            InterruptedException {
        String priority, state, type;

        priority = state
                 = type
                 = null;

        Toggle priorityToggle = priorityGroup
                .getSelectedToggle();
        if (priorityToggle != null) {
            priority = priorityToggle.getUserData()
                    .toString();
        }

        Toggle stateToggle = stateGroup
                .getSelectedToggle();
        if (stateToggle != null) {
            state = stateToggle.getUserData()
                    .toString();
        }

        Toggle typeToggle = typeGroup
                .getSelectedToggle();
        if (typeToggle != null) {
            type = typeToggle.getUserData()
                    .toString();
        }

        List<IssueCardResponseDTO> list = issueService
                .searchByFilter(type, state, priority);
        if(callback != null) {
            callback.accept(list);
        } closeWindow();
    }

    public void closeWindow() {
        Stage stage = (Stage) cardFilter.getScene()
                .getWindow();
        stage.close();
    }
}
