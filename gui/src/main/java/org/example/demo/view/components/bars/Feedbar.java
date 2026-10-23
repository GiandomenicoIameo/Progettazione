package org.example.demo.view.components.bars;

import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import org.example.demo.view.access.Login;
import org.example.demo.view.components.cards.IssueCardAdmin;
import org.example.demo.view.components.cards.NewIssueCard;
import org.example.demo.view.components.modal.Filter;
import org.example.demo.view.components.modal.Modal;
import org.example.demo.view.core.Component;
import org.example.demo.view.core.Sequence;
import org.example.demo.view.dto.cards.IssueCardResponseDTO;
import org.example.demo.view.dto.views.UserDTO;
import org.example.demo.view.service.IssueService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Feedbar extends Component  {
    private final IssueService issueService
            = new IssueService();

    @FXML private AnchorPane feedbar;
    @FXML private VBox cardContainer;
    @FXML private HBox searchbar;
    @FXML private Label issues;
    @FXML private TextField search;

    private List<IssueCardAdmin> list;

    @FXML
    public void initialize() throws IOException,
            InterruptedException {
        setDefaultButton();
        enableMaximize();
        importCard();
    }

    public void setDefaultButton() {
        search.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                try {
                    searchByKeyword();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    public void enableMaximize() {
        configureMaximize(feedbar,
                Sequence.getCurrStage());
    }

    @FXML
    public void refresh() throws IOException,
            InterruptedException {
        feedbar.setOpacity(0.0);
        cardContainer.getChildren()
                .clear();
        fade(feedbar);
        importCard();
    }

    @FXML
    private void handleSceltaMenu(ActionEvent event) {
        MenuItem options = (MenuItem)
                event.getSource();
        String testo = options.getText();

        if (testo.equals("Latest"))
            latest();
        else
            oldest();
    }

    public void mapperTitle(IssueCardAdmin card,
                            IssueCardResponseDTO response) {
        card.setTitle(response
                .getTitle());
    }

    public void mapperId(IssueCardAdmin card,
                         IssueCardResponseDTO response) {
        card.setId(response.getId());
    }

    public void mapperName(IssueCardAdmin card,
                           IssueCardResponseDTO response) {
        UserDTO user = response.getCreator();
        card.setName(user.getName());
    }

    public void mapperTypology(IssueCardAdmin card,
                               IssueCardResponseDTO response) {
        if(response.getTypology()
                .equals("BUG")) {
            card.setBug();
            card.setVisibleMenu();
        }
        else if(response.getTypology()
                .equals("FEATURE"))
            card.setFeature();
        else if(response.getTypology()
                .equals("QUESTION"))
            card.setQuestion();
        else
            card.setDocumentation();
    }

    public void mapperPriority(IssueCardAdmin card,
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

    public void mapperAssegnee(IssueCardAdmin card,
                               IssueCardResponseDTO response) {
        if(response.getAssignee() != null)
            card.setAssigned(true);
        else
            card.setAssigned(false);
    }

    public void mapperState(IssueCardAdmin card,
                            IssueCardResponseDTO response) {
        if(response.getState().equals("TODO"))
            card.setStateTodo();
        else {
            if(getRoleLogged().equals("AMMINISTRATORE"))
                card.setCardDisabled();
            card.setStateClosed();
        }
    }

    public void mapperDuplicated(IssueCardAdmin card,
                                 IssueCardResponseDTO response) {
        if(response.getDuplicated() != null) {
            if(response.getState().equals("CHIUSO") && getRoleLogged()
                    .equals("AMMINISTRATORE"))
                card.setCardDisabled();
            card.setDuplicated(response
                    .getDuplicated());
        }
    }

    public String getRoleLogged() {
        return Login.getRoleLogged();
    }

    public List<IssueCardAdmin> convert() throws IOException,
            InterruptedException {
        List<IssueCardAdmin> list = new ArrayList<>();
        this.issueService.listIssues()
                .forEach(issue -> {
                    IssueCardAdmin card = new IssueCardAdmin();
                    mapperTitle(card, issue); mapperId(card, issue);
                    mapperName(card, issue); mapperTypology(card, issue);
                    mapperPriority(card, issue); mapperState(card, issue);
                    mapperAssegnee(card, issue);
                    mapperDuplicated(card, issue);

                    list.add(card);
                    this.list = list;
                }); return list;
    }

    public List<IssueCardAdmin> convert(List<IssueCardResponseDTO> list) throws IOException,
            InterruptedException {
        List<IssueCardAdmin> listCard = new ArrayList<>();
        list.forEach(issue -> {
                    IssueCardAdmin card = new IssueCardAdmin();
                    mapperTitle(card, issue); mapperId(card, issue);
                    mapperName(card, issue); mapperTypology(card, issue);
                    mapperPriority(card, issue); mapperState(card, issue);
                    mapperAssegnee(card, issue); mapperDuplicated(card, issue);
            listCard.add(card);
                }); return listCard;
    }

    public void importCard() throws IOException,
            InterruptedException {
        List<IssueCardAdmin> list = convert();

        list.forEach(card -> {
            if(getRoleLogged().equals("NORMALE")) {
                card.setInvisibleAssigned();
                card.setInvisibleCloseIssue();
                card.setInvisibleMenu();
            }
            cardContainer.getChildren().
                    add(card.getRoot());
        }); countIssues(list.size());
    }

    public void importCard(List<IssueCardResponseDTO> list) throws IOException,
            InterruptedException {
        clear();
        List<IssueCardAdmin> listCard = convert(list);
        listCard.forEach(card -> {
            if(getRoleLogged().equals("NORMALE")) {
                card.setInvisibleAssigned();
                card.setInvisibleCloseIssue();
                card.setInvisibleMenu();
            }
            cardContainer.getChildren().
                    add(card.getRoot());
        }); countIssues(list.size());
    }

    private void loadCard(IssueCardAdmin issue) {
        cardContainer.getChildren().
                add(issue.getRoot());
        list.add(issue);
        countIssues(list.size());
    }

    public void latest() {
        clear();
        list.forEach(card -> {
            if(getRoleLogged().equals("NORMALE")) {
                card.setInvisibleAssigned();
                card.setInvisibleCloseIssue();
                card.setInvisibleMenu();
            }
            cardContainer.getChildren().
                    add(card.getRoot());
        });
    }

    public void clear() {
        cardContainer.getChildren()
                .clear();
    }

    public void oldest() {
        cardContainer.getChildren()
                .clear();
        List<IssueCardAdmin> list = this.list
                .reversed();
        list.forEach(card -> {
            if(getRoleLogged().equals("NORMALE")) {
                card.setInvisibleAssigned();
                card.setInvisibleCloseIssue();
                card.setInvisibleMenu();
            }
            cardContainer.getChildren().
                    add(card.getRoot());
        });
    }

    public void countIssues(int size) {
        issues.setText(Integer
                .toString(size));
    }

    public void viewSearchbar() {
        if (!searchbar.isVisible()) {
            searchbar.setVisible(true);
            searchbar.setManaged(true);
            searchbar.toBack();

            // 1. FORZA IL LAYOUT: dice a JavaFX di calcolare subito dimensioni e posizione
            searchbar.applyCss();
            searchbar.layout();
            searchbar.layout();

            // 2. Ora l'altezza è reale e precisa al pixel
            double altezza = searchbar.getHeight();
            if (altezza == 0) {
                altezza = searchbar.prefHeight(-1); // Fallback sicuro basato sul preferred height
            }

            final double finalAltezza =altezza; // Necessario per usarla dentro la lambda

            Platform.runLater(() -> {
                // Partiamo da -finalAltezza e arriviamo a 0 (scende)
                TranslateTransition scendi = translate((int) -finalAltezza, 0);
                scendi.setOnFinished(event -> {
                    searchbar.toFront();
                });
                scendi.play();
            });

        } else {
            searchbar.toBack();

            // Anche in chiusura ci assicuriamo di prendere l'altezza corretta
            searchbar.applyCss();
            searchbar.layout();

            double altezza = searchbar.getHeight();
            if (altezza == 0) altezza = searchbar.prefHeight(-1);

            // Da 0 risale a -altezza
            TranslateTransition sali = translate(0, (int) -altezza);
            sali.setOnFinished(event -> {
                searchbar.setVisible(false);
                searchbar.setManaged(false);
                // Opzionale ma consigliato: resetta la traslazione per pulire lo stato
                searchbar.setTranslateY(0);
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

    @FXML
    public void searchByKeyword() throws IOException,
            InterruptedException {
        String target = search.getText()
                .toUpperCase();
        String[] part = target
                .split("/", -1);
        List<IssueCardResponseDTO> list = issueService
                .searchByKeyword(part[0], part[1],
                        part[2]);
        importCard(list);
    }

    @FXML
    public void showFilter() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass()
                    .getResource("/org/example/demo/" +
                            "view/components/modal/filter.fxml"));
            Parent root = loader.load();
            Filter controller = loader.getController();
            addComponent(controller);
            controller.setCallback(listIssue -> {
                try {
                    importCard(listIssue);
                } catch (IOException | InterruptedException e) {
                    throw new RuntimeException(e);
                }
            });
            Modal.showModal(root, feedbar);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void showIssue() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass()
                    .getResource("/org/example/demo/view" +
                            "/components/modal/create-issue.fxml"));
            Parent root = loader.load();

            NewIssueCard controller = loader.getController();
            addComponent(controller);
            controller.setCallback(this::loadCard);

            Modal.showModal(root, feedbar);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Parent getRoot() {
        return feedbar;
    }
}
