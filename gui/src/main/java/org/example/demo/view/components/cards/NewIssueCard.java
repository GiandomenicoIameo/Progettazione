package org.example.demo.view.components.cards;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.example.demo.view.access.Login;
import org.example.demo.view.dto.cards.IssueCardResponseDTO;
import org.example.demo.view.dto.cards.NewIssueCardRequestDTO;
import org.example.demo.view.dto.views.RegularDTO;
import org.example.demo.view.dto.views.UserDTO;
import org.example.demo.view.service.IssueService;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Base64;
import java.util.function.Consumer;

public class NewIssueCard extends Card {
    private IssueService issueService = new IssueService();

    @FXML private VBox root;
    @FXML private HBox boxEtichette;

    @FXML private TextField title;
    @FXML private TextArea description;
    @FXML private TextField image;

    @FXML private TextField typology;
    @FXML private TextField priority;

    @FXML private MenuButton menuButtonTypology;
    @FXML private MenuButton menuButtonPriority;

    @FXML private RadioButton assignment;
    @FXML private TextField assignmentUser;

    private File file;

    private Consumer<IssueCardAdmin> callback;

    public void setCallback(Consumer<IssueCardAdmin> callback) {
        this.callback = callback;
    }

    public void populateTitle(NewIssueCardRequestDTO request) {
        String title = this.title
                .getText();
        request.setTitle(title);
    }

    public void populateDescription(NewIssueCardRequestDTO request) {
        String description = this.description
                .getText();
        request.setDescription(description);
    }

    public void populateTypology(NewIssueCardRequestDTO request) {
        String typology = this.typology
                .getText();
        request.setTypology(typology
                .toUpperCase());
    }

    public void populatePriority(NewIssueCardRequestDTO request) {
        String priority = this.priority
                .getText();
        request.setPriority(priority
                .toUpperCase());
    }

    public void populateAssignee(NewIssueCardRequestDTO request) {
        String assignment = this.assignmentUser
                .getText();
        RegularDTO regular = new RegularDTO();
        regular.setEmail(assignment);
        request.setUserDTO(regular);
    }

    public void populateImage(NewIssueCardRequestDTO request) throws IOException {
        if (file != null) {
            byte[] bytes = Files.
                    readAllBytes(file.toPath());
            String base64Image = Base64.
                    getEncoder().encodeToString(bytes);
            request.setImage(base64Image);
        }
    }

    public NewIssueCardRequestDTO populate() throws IOException {
        NewIssueCardRequestDTO request = new
                NewIssueCardRequestDTO();
        populateTitle(request); populateDescription(request);
        populateTypology(request); populatePriority(request);
        populateImage(request);

        return request;
    }

    public void applyTitle(IssueCardAdmin card,
                           IssueCardResponseDTO response) {
        card.setTitle(response
                .getTitle());
    }

    public void applyId(IssueCardAdmin card,
                        IssueCardResponseDTO response) {
        card.setId(response.getId());
    }

    public void applyName(IssueCardAdmin card,
                          IssueCardResponseDTO response) {
        UserDTO user = response.getCreator();
        card.setName(user.getName());
    }

    public void applyTypology(IssueCardAdmin card,
                              IssueCardResponseDTO response) {
        if(response.getTypology()
                .equals("BUG"))
            card.setBug();
        else if(response.getTypology()
                .equals("FEATURE"))
            card.setFeature();
        else if(response.getTypology()
                .equals("QUESTION"))
            card.setQuestion();
        else
            card.setDocumentation();
    }

    public void applyPriority(IssueCardAdmin card,
                              IssueCardResponseDTO response) {
        if(response.getTypology()
                .equals("BUG")) {
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
    }

    public void applyAssignment(IssueCardAdmin card,
                                IssueCardResponseDTO response) {
        if(response.getCreator() != null)
            card.setAssigned(true);
    }

    public IssueCardAdmin apply(IssueCardResponseDTO response) {
        IssueCardAdmin card = new IssueCardAdmin();

        applyId(card, response);
        applyTitle(card, response);
        applyName(card, response);
        applyTypology(card, response);
        applyPriority(card, response);

        if(Login.getRoleLogged()
                .equals("NORMALE")) {
            card.setInvisibleCloseIssue();
            card.setInvisibleAssigned();
        }

        return card;
    }

    public IssueCardResponseDTO createCard() throws IOException,
            InterruptedException {
        NewIssueCardRequestDTO request = populate();
        IssueCardResponseDTO response = issueService
                .createIssue(request);
        return response;
    }

    @FXML
    public void handleAdd() throws IOException,
            InterruptedException {
        IssueCardResponseDTO response = createCard();
        IssueCardAdmin card = apply(response);

        if (callback != null)
            callback.accept(card);
        closeWindow();
    }

    @FXML
    public void onMenuTypologySelected(ActionEvent event) {
        MenuItem voce = (MenuItem) event.getSource();
        typology.setText(voce.getText());
    }

    @FXML
    public void onMenuPrioritySelected(ActionEvent event) {
        MenuItem voce = (MenuItem) event.getSource();
        priority.setText(voce.getText());
    }

    @FXML
    public void close(ActionEvent event) {
        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene()
                .getWindow();
        stage.close();
    }

    @FXML
    public void addImage() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Seleziona un'immagine");

        // Filtro per far selezionare SOLO immagini (evita che l'utente carichi PDF o documenti)
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter(
                "File Immagine (*.png, *.jpg, *.jpeg)", "*.png", "*.jpg", "*.jpeg"
        );
        fileChooser.getExtensionFilters()
                .add(extFilter);
        // Recuperiamo la finestra (Stage) corrente per dirgli dove aprire il popup
        // Usiamo imageViewFoto per risalire alla scena e poi alla finestra
        Stage stageCorrente = (Stage) image
                .getScene().getWindow();
        // 4. Mostriamo la finestra e aspettiamo che l'utente scelga il file
        file = fileChooser
                .showOpenDialog(stageCorrente);

        if (file != null) {
            String path = file.getName();
            image.setText(path);
        }
    }

    @FXML
    public void onRadioClicked() {
        boolean isSelected = assignment.isSelected();
        assignmentUser.setDisable(!isSelected);
    }

    public void closeWindow() {
        Stage stage = (Stage) root.getScene()
                .getWindow();
        stage.close();
    }

    @Override
    public Parent getRoot() {
        return root;
    }
}
