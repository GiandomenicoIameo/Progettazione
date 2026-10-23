package org.example.demo.view.components.cards;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import org.example.demo.view.core.Component;
import org.example.demo.view.support.Date;

import java.time.LocalDateTime;
import java.util.function.Consumer;

public class EmailBox extends Component {
    private static Button currentButton = null;

    @FXML private Button box;

    @FXML private Label id;
    @FXML private Label name;
    @FXML private Label title;
    @FXML private Label date;

    @FXML private Label low;
    @FXML private Label medium;
    @FXML private Label high;

    private Label current = null;

    private Consumer<ContentCard> callback;
    private ContentCard card = new ContentCard();

    public void setCallback(Consumer<ContentCard> callback) {
        this.callback = callback;
    }

    public EmailBox() {
        FXMLLoader loader = new FXMLLoader(getClass().
                getResource("email-box.fxml"));
        loader.setController(this);
        try {
            loader.load();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setLabels(Label labels) {
        if(current != null)
            current.setVisible(false);
        current = labels;
        current.setVisible(true);
    }

    public void setLow() {
        setLabels(low);
    }

    public void setMedium() {
        setLabels(medium);
    }

    public void setHigh() {
        setLabels(high);
    }

    public void setTitle(String title) {
        this.title.setText(title);
        card.setTitle(title);
    }

    public void setId(Integer id) {
        this.id.setText(
                id.toString());
    }

    public void setName(String name) {
        this.name.setText(name);
        card.setName(name);
        card.setNameCard(name);
    }

    public void setState(String state) {
        card.setState(state);
    }

    public void setDuplicate(boolean isDuplicate) {
        card.setDuplicate(isDuplicate);
    }

    public void setDescription(String description) {
        card.setDescpription(description);
    }

    public void setDate(LocalDateTime date) {
        Date d = new Date(date);
        this.date
                .setText(d.toString());
        card.setDate(date);
        card.setDateCard(date);
    }

    public void setImage(Integer idIssue) {
        card.setImage(idIssue);
    }

    public String getId() {
        return id.getText();
    }

    @FXML
    public void handleOnClick(ActionEvent event) {
        if (currentButton != null && currentButton != box)
            currentButton.getStyleClass()
                    .remove("selected");

        if (box.getStyleClass()
                .contains("selected")) {
            box.getStyleClass()
                    .remove("selected");
            currentButton = null;
        } else {
            box.getStyleClass()
                    .add("selected");
            currentButton = box;
        }

        if (callback != null)
            callback.accept(card);
    }

    @Override
    public Parent getRoot() {
        return box;
    }
}
