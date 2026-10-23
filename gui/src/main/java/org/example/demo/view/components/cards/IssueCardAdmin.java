package org.example.demo.view.components.cards;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.example.demo.view.components.modal.Assignment;
import org.example.demo.view.dto.cards.IssueCardResponseDTO;
import org.example.demo.view.service.IssueService;

import java.io.IOException;

public class IssueCardAdmin extends Card {
    IssueService issueService = new IssueService();

    @FXML private VBox issueCard;

    @FXML private Label id;
    @FXML private Label name;
    @FXML private Label title;

    @FXML private Label bug;
    @FXML private Label feature;
    @FXML private Label question;
    @FXML private Label documentation;

    @FXML private Label low;
    @FXML private Label medium;
    @FXML private Label high;

    @FXML private Label assigned;
    @FXML private Label duplicate;
    @FXML private MenuButton options;
    @FXML private MenuButton issueClose;

    @FXML private Region state;
    @FXML private MenuItem close;

    private Label typology = null;
    private Label priority = null;

    public IssueCardAdmin() {
        FXMLLoader loader = new FXMLLoader(getClass().
                getResource("issue-admin.fxml"));
        loader.setController(this);
        try {
            loader.load();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void handleAssign() throws IOException,
            InterruptedException {
        issueService.assign(Integer.parseInt(this.id
                .getText()), 7);
    }

    private void setLabels(Label type, Label value) {
        if(type != null) {
            type.setManaged(false);
            type.setVisible(false);
        }
        type = value;
        type.setManaged(true);
        type.setVisible(true);
    }

    public void setBug() {
        setLabels(typology,
                bug);
    }

    public void setFeature() {
        setLabels(typology,
                feature);
    }

    public void setDocumentation() {
        setLabels(typology,
                documentation);
    }

    public void setQuestion() {
        setLabels(typology,
                question);
    }

    public void setLow() {
        setLabels(priority,
                low);
    }

    public void setMedium() {
        setLabels(priority,
                medium);
    }

    public void setHigh() {
        setLabels(priority,
                high);
    }

    public void setAssigned(boolean assign) {
        assigned.setManaged(assign);
        assigned.setVisible(assign);
    }

    public void assignable(boolean sign) {
        setAssigned(sign);
    }

    public void setDuplicated(boolean isDuplicated) {
        if(isDuplicated)
           setStateClosed();
        duplicate.setManaged(isDuplicated);
        duplicate.setVisible(isDuplicated);
    }

    public void setVisibleMenu() {
        options.setVisible(true);
    }

    public void setInvisibleCloseIssue() {
        issueClose.setVisible(false);
    }

    public void setInvisibleMenu() {
        options.setVisible(false);
    }

    public void setInvisibleAssigned() {
        assigned.setVisible(false);
    }

    public void setTitle(String title) {
        this.title.setText(title);
    }

    public void setId(Integer id) {
        close.setUserData(id);
        duplicate.setUserData(id);
        this.id.setText(
                id.toString());
    }

    public void setName(String name) {
        this.name.setText(name);
    }

    public void setStateTodo() {
        state.getStyleClass().
                add("icon-bell-card-issue");
    }

    public void setStateClosed() {
        state.getStyleClass()
                .add("icon-bell-close-issue");
        setInvisibleMenu(); setInvisibleCloseIssue();
    }

    public void setCardDisabled() {
        issueCard.setDisable(true);
    }

//    public void setDate(String date) {
//        Date d = new Date(date);
//        this.date
//                .setText(d.toString());
//    }

    public void load(IssueCardResponseDTO issueCardRequestDTO) {
        // image.setText(issueCardDTO.getImage());
        // username.setText(issueCardDTO.getUserDTO().getName());
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

        Parent mainRoot = issueCard
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
    public void showAssignment() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass()
                    .getResource("/org/example/demo/" +
                            "view/components/modal/assignment.fxml"));
            Parent root = loader.load();

            Assignment assignment = loader.getController();
            assignment.initData(Integer.
                    parseInt(id.getText()));
            assignment.setCallback(this::assignable);

            showModal(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void closed() throws IOException,
            InterruptedException {
        if(issueService.close((Integer) close.
                getUserData()) != null) {
            setStateClosed(); setCardDisabled();
        }
    }

    @FXML
    public void duplicated() throws IOException,
            InterruptedException {
        if(issueService.duplicate((Integer) duplicate.
                getUserData()) != null) {
            setDuplicated(true); setStateClosed();
            setCardDisabled();
        }
    }

    @Override
    public Parent getRoot() {
        return issueCard;
    }
}