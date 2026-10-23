package org.example.demo.view.components.cards;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import org.example.demo.view.service.IssueService;
import org.example.demo.view.support.Date;

import java.time.LocalDateTime;
import java.util.concurrent.CompletableFuture;

public class ContentCard extends Card {
    private IssueService issueService = new IssueService();

    @FXML private VBox contentCard;

    @FXML private Label name;
    @FXML private Label title;
    @FXML private Label date;

    @FXML private Label nameCard;
    @FXML private Label dateCard;
    @FXML private Text description;
    @FXML private ImageView image;

    @FXML private Label open;
    @FXML private Label closed;
    @FXML private Label duplicate;

    @FXML private Region iconOpen;
    @FXML private Region iconClosed;

    @FXML private VBox boxImage;

    public ContentCard() {
        FXMLLoader loader = new FXMLLoader(getClass().
                getResource("assignment-card-content.fxml"));
        loader.setController(this);
        try {
            loader.load();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setTitle(String title) {
        this.title.setText(title);
    }

    public void setName(String name) {
        this.name.setText(name);
    }

    public void setDescpription(String descpription) {
        this.description.setText(descpription);
    }

    public void setDate(LocalDateTime date) {
        Date d = new Date(date);
        this.date.setText(d.toString());
    }

    public void setImage(Integer idIssue) {
        CompletableFuture.runAsync(() -> {
            Image img = issueService.download(idIssue);

            Platform.runLater(() -> {
                if (img != null) {
                    image.setImage(img);
                    boxImage.setVisible(true);
                    boxImage.setManaged(true);
                }
                else {
                    image.setImage(null);
                    boxImage.setVisible(false);
                    boxImage.setManaged(false);
                }
            });
        });
    }

    public void setNameCard(String name) {
        this.nameCard.setText(name);
    }

    public void setDateCard(LocalDateTime date) {
        Date d = new Date(date);
        this.date.setText(d.toString());
        this.dateCard.setText(d.toString());
    }

    public void setState(String state) {
        if(state.equals("TODO"))
            setOpen();
        else
            setClose();
    }

    public void setClose() {
        closed.setManaged(true);
        closed.setVisible(true);

        open.setManaged(false);
        open.setVisible(false);
    }

    public void setOpen() {
        open.setManaged(true);
        open.setVisible(true);

        closed.setManaged(false);
        closed.setVisible(false);
    }

    public void setDuplicate(boolean isDuplicated) {
        duplicate.setManaged(isDuplicated);
        duplicate.setVisible(isDuplicated);
    }

    @Override
    public Parent getRoot() {
        return contentCard;
    }
}
