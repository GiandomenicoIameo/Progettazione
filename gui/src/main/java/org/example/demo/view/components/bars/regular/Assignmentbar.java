package org.example.demo.view.components.bars.regular;

import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.example.demo.view.components.cards.ContentCard;
import org.example.demo.view.components.cards.EmailBox;
import org.example.demo.view.core.Component;
import org.example.demo.view.dto.bars.regular.AssignmentbarDTO;
import org.example.demo.view.dto.cards.IssueCardResponseDTO;
import org.example.demo.view.dto.views.UserDTO;
import org.example.demo.view.service.IssueService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Assignmentbar extends Component {
    IssueService issueService  = new IssueService();

    @FXML private AnchorPane assignmentsbar;
    @FXML private TextField search;
    @FXML private VBox cardContainer;
    @FXML private VBox boxContainer;
    @FXML private HBox searchbar;
    @FXML private VBox emailBox;

    @FXML private Label numIssue;
    @FXML private Label id;
    private String state;

    @FXML
    public void initialize() throws IOException, InterruptedException {
        // Serve per far comparire l'HBox sotto l'achorPane quando
        // si vuole cercare una card.
        searchbar.setViewOrder(1.0);

        assignmentsbar.setOnMouseClicked((MouseEvent event) -> {
            if (event.getClickCount() == 2) {
                Stage stage = (Stage) assignmentsbar.getScene().getWindow();

                // Se è massimizzata la rimpicciolisce, se è piccola la massimizza
                stage.setMaximized(!stage.isMaximized());
            }
        });
        importCard();
    }

    @FXML
    public void refresh() throws IOException,
            InterruptedException {
        emailBox.setOpacity(0.0);
        cardContainer.getChildren()
                .clear();
        fade();
        importCard();
    }

    private void fade() {
        FadeTransition fadeIn = new FadeTransition(Duration
                .millis(400), emailBox);
        fadeIn.setToValue(1.0);
        fadeIn.play();
    }

    @Override
    public Parent getRoot() {
        return assignmentsbar;
    }

    @FXML
    public void close() {
        System.exit(0);
    }

    @FXML
    public void minimize() {
        Stage stage = (Stage) assignmentsbar.
                getScene().getWindow();
        stage.setIconified(true);
    }

    public void setTitle(EmailBox card,
                         IssueCardResponseDTO response) {
        card.setTitle(response
                .getTitle());
    }

    public void setId(EmailBox card,
                      IssueCardResponseDTO response) {
        card.setId(response.getId());
    }

    public void setDate(EmailBox card,
                        IssueCardResponseDTO response) {
        card.setDate(response.getDate());
    }

    public void setName(EmailBox card,
                        IssueCardResponseDTO response) {
        UserDTO user = response.getCreator();
        card.setName(user.getName());
    }

    public void setState(EmailBox card,
                         IssueCardResponseDTO response) {
        card.setState(response.getState());
    }

    public void setDuplicate(EmailBox card,
                             IssueCardResponseDTO response) {
        if(response.getDuplicated() != null)
            card.setDuplicate(response
                    .getDuplicated());
    }

    public void setDescription(EmailBox card,
                               IssueCardResponseDTO response) {
        card.setDescription(response.getDescription());
    }

    public void setImage(EmailBox card, IssueCardResponseDTO  response) {
        card.setImage(response.getId());
    }

    public void setPriority(EmailBox card,
                            IssueCardResponseDTO response) {
        if(response.getPriority()
                .equals("BASSA"))
            card.setLow();
        else if(response.getPriority()
                .equals("MEDIA"))
            card.setMedium();
        else if(response.getPriority()
                .equals("ALTA"))
            card.setHigh();
    }

    public List<EmailBox> convert() throws IOException,
            InterruptedException {
        List<EmailBox> list = new ArrayList<>();
        this.issueService.assigned()
                .forEach(issue -> {
                    EmailBox card = new EmailBox();
                    setTitle(card, issue); setId(card, issue);
                    setName(card, issue); setPriority(card, issue);
                    setDescription(card, issue); setState(card, issue);
                    setDate(card, issue); setImage(card, issue);
                    setDuplicate(card, issue);

                    list.add(card);
                }); return list;
    }

    public void loadCard(ContentCard card) {
        boxContainer.getChildren()
                .clear();
        boxContainer.getChildren()
                .add(card.getRoot());
    }

    public void importCard() throws IOException,
            InterruptedException {
        List<EmailBox> list = convert();

        list.forEach(card -> {
            card.setCallback( box -> {
                loadCard(box); id.setText(card
                        .getId());
            });
            cardContainer.getChildren().
                    add(card.getRoot());
        }); countIssues(list.size());
    }

    private void countIssues(int size) {
        Integer counter = size;
        numIssue.setText(counter
                .toString());
    }

    @FXML
    public void searchBug() {
        String target = search.getText()
                .toLowerCase();
        boolean match = true;

        for (Node nodo : cardContainer.getChildren()) {
            if (nodo instanceof Button pulsante) {
                Label username = (Label)
                        pulsante.lookup("#name");

                if(!target.isEmpty()) {
                    match = username.getText()
                            .toLowerCase()
                            .equals(target);
                }

                pulsante.setVisible(match);
                pulsante.setManaged(match);
            }
        }
    }

    public void load(AssignmentbarDTO assignmentbarDTO) {
        // List<String> list = assignmentbarDTO.getListCards();
        // list.stream()
        //     .forEach(card -> { addComponent(card);
        //          card.applyTitle(assignmentbarDTO.populateTitle();
        //          card.applyId(assignmentDTO.getId();
        //          card.setUsername(assignmentsDTO.getName();
        //          card.setDate(assignmentsDTO.getDate());
        //                    cardContainer.getChildren().
        //                            add(card.getRoot());
        //                });
    }

    public void viewSearchbar() {
        if (!searchbar.isVisible()) {
            searchbar.setVisible(true);
            searchbar.setManaged(true);

            double altezzaFissa = searchbar.getPrefHeight();
            if (altezzaFissa <= 0) altezzaFissa = 60; // Fallback di sicurezza

            TranslateTransition scendi = new TranslateTransition(Duration.seconds(0.3), searchbar);
            scendi.setFromY(-altezzaFissa); // Parte esplicitamente da sopra
            scendi.setToY(0);               // Arriva alla posizione normale
            scendi.play();

        } else {
            double altezzaFissa = searchbar.getPrefHeight();
            if (altezzaFissa <= 0) altezzaFissa = 60;

            TranslateTransition sali = new TranslateTransition(Duration.seconds(0.3), searchbar);
            sali.setFromY(0);               // Parte dalla posizione normale
            sali.setToY(-altezzaFissa);     // Risale fuori dallo schermo

            sali.setOnFinished(event -> {
                searchbar.setVisible(false);
                searchbar.setManaged(false);
                searchbar.setTranslateY(0); // Resetta lo stato per il prossimo giro
            });

            sali.play();
        }
    }
    private TranslateTransition translate(int src, int dst) {
        TranslateTransition move = new TranslateTransition(
                Duration.seconds(0.3), searchbar);
        move.setFromY(src);
        move.setToY(dst);

        return move;
    }
}
