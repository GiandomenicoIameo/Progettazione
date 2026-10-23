package org.example.demo.view.components.bars.admin;

import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import org.example.demo.view.components.cards.User;
import org.example.demo.view.components.modal.AddUser;
import org.example.demo.view.core.Component;
import org.example.demo.view.core.Sequence;
import org.example.demo.view.dto.modal.AddUserResponseDTO;
import org.example.demo.view.service.UserService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Groupbar extends Component {
    private UserService user = new UserService();

    @FXML private VBox groupbar;
    @FXML private VBox cardContainer;
    @FXML private Label useraccounts;
    @FXML private ScrollPane scroll;

    private int numIssues = 0;

    @FXML
    public void initialize() throws IOException,
            InterruptedException {
        groupbar.setOnMouseClicked((MouseEvent event) -> {
            if (event.getClickCount() == 2) {
                Stage stage = (Stage) groupbar
                        .getScene().getWindow();
                stage.setMaximized(!stage.isMaximized());
            }
        });
        importCard();
    }

    @FXML
    public void close() {
        System.exit(0);
    }

    @FXML
    public void refresh() throws IOException,
            InterruptedException {
        groupbar.setOpacity(0.0);
        cardContainer.getChildren()
                .clear();
        fade();
        importCard();
    }

    private void fade() {
        FadeTransition fadeIn = new FadeTransition(Duration
                .millis(400), groupbar);
        fadeIn.setToValue(1.0);
        fadeIn.play();
    }

    public void countIssues(int size) {
        useraccounts.setText(Integer
                .toString(size));
    }

    public void setUsername(User user,
                            AddUserResponseDTO response) {
        user.setUsernameBold(response.getName() +
                " " + response.getSurname());
        user.setUsernameRegular(response.getSurname());
    }

    public void setUseremail(User user,
                             AddUserResponseDTO response) {
        user.setEmail(response
                .getEmail());
    }

    public void setUserole(User user,
                           AddUserResponseDTO response) {
        if(response.getRole()
                .equals("AMMINISTRATORE"))
            user.setAdmin();
        else
            user.setUser();
    }

    public List<User> convert() throws IOException,
            InterruptedException {
        List<User> list = new ArrayList<>();

        this.user.listUsers()
                .forEach(card -> {
                    User user = new User();
            setUseremail(user, card); setUsername(user, card);
            setUserole(user, card);
            list.add(user);
        }); return list;
    }

    public void importCard() throws IOException,
            InterruptedException {
        List<User> list = convert();

        scroll.setVisible(true);
        list.forEach(card -> {
            cardContainer.getChildren().
                    add(card.getRoot());
        }); countIssues(list.size());
        numIssues = list.size();
    }

    private void loadCard(User user) {
        scroll.setVisible(true);
            cardContainer.getChildren().
                    add(user.getRoot());
            countIssues(++numIssues);
    }

    @FXML
    public void minimize() {
        Stage stage = Sequence
                .getCurrStage();
        stage.setIconified(true);
    }

    private void showModal(Parent root) {
        Scene scene = new Scene(root);
        scene.setFill(Color
                .TRANSPARENT);

        Stage modale = new Stage(StageStyle
                .UNDECORATED);
        modale.setScene(scene);
        modale.initModality(Modality
                .APPLICATION_MODAL);
        modale.setOnShown(e ->
                centerOnScreen(modale));

        Parent mainRoot = groupbar
                .getScene().getRoot();
        mainRoot.setEffect(
                dimEffect());
        modale.showAndWait();
        mainRoot.setEffect(null);
    }

    private void centerOnScreen(Stage stage) {
        Rectangle2D bounds = Screen
                .getPrimary()
                .getVisualBounds();
        stage.setX((bounds.getWidth() -
                stage.getWidth()) / 2);
        stage.setY((bounds.getHeight() -
                stage.getHeight()) / 2);
    }

    private ColorAdjust dimEffect() {
        ColorAdjust effect = new ColorAdjust();
        effect.setBrightness(-0.09);
        return effect;
    }

    @FXML
    public void addUser() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass()
                    .getResource("/org/example/demo/" +
                            "view/components/modal/add-user.fxml"));
            Parent root = loader.load();

            AddUser controller = loader.getController();
            addComponent(controller);
            controller.setOnUserCreated(this::loadCard);

            showModal(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Parent getRoot() {
        return groupbar;
    }
}
