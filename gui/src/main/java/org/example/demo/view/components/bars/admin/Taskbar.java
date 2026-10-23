package org.example.demo.view.components.bars.admin;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.example.demo.view.core.Component;
import org.example.demo.view.dto.bars.admin.TaskbarDTO;

public class Taskbar extends Component {
    @FXML private VBox taskbar;
    @FXML private VBox cardContainer;
    @FXML private TextField search;

    @FXML
    public void initialize() {
        taskbar.setOnMouseClicked((MouseEvent event) -> {
            if (event.getClickCount() == 2) {
                Stage stage = (Stage) taskbar
                        .getScene().getWindow();
                stage.setMaximized(!stage.isMaximized());
            }
        });
    }

    @Override
    public Parent getRoot() {
        return taskbar;
    }

    @FXML
    public void close() {
        System.exit(0);
    }

    @FXML
    public void minimize() {
        Stage stage = (Stage) taskbar.
                getScene().getWindow();
        stage.setIconified(true);
    }

    @FXML
    public void viewAssignments() {
        String target = search.getText().toLowerCase();
        String[] parti = target.split("/");

        boolean[] match = {true,true};

        for (Node nodo : cardContainer.getChildren()) {
            if (nodo instanceof Button pulsante) {

                Node[] arrayLabel = pulsante.lookupAll(".label")
                        .toArray(new Node[0]);

                if(!parti[0].isEmpty()) {
                    String first = ((Label) arrayLabel[0])
                            .getText()
                            .toLowerCase();
                    match[0] = first
                            .contains(parti[0]);
                    match[1] = false;

                    if(parti.length > 1) {
                        String second = ((Label) arrayLabel[1])
                                .getText()
                                .toLowerCase();
                        match[1] = second
                                .contains(parti[1]);
                    }
                }

                pulsante.setVisible(match[0] && match[1]);
                pulsante.setManaged(match[0] && match[1]);
            }

            else if (nodo instanceof Separator separatore) {
                separatore.setVisible(match[0] && match[1]);
                separatore.setManaged(match[0] && match[1]);
            }
        }
    }

    public void load(TaskbarDTO taskbarDTO) {
        // cardContainer.setText(regularDTO.getListCards());
    }
}
//
//match[index1] = parti[index1].isEmpty() || pulsante.lookupAll(".label")
//                        .stream()
//                        .map(n -> ((Label) n).getText())
//        .anyMatch(testo -> testo.toLowerCase()
//                                .contains(parti[0]));