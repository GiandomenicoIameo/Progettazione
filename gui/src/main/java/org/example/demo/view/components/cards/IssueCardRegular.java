package org.example.demo.view.components.cards;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.example.demo.view.dto.cards.IssueCardResponseDTO;
import org.example.demo.view.support.Date;

import java.io.IOException;

public class IssueCardRegular extends Card {
    @FXML private VBox issueCard;

    @FXML private Label id;
    @FXML private Label username;
    @FXML private Label title;

    @FXML private Label bug;
    @FXML private Label feature;
    @FXML private Label question;
    @FXML private Label documentation;

    @FXML private Label low;
    @FXML private Label medium;
    @FXML private Label high;

    private Label typology = null;
    private Label priority = null;

    public IssueCardRegular() {
        FXMLLoader loader = new FXMLLoader(getClass().
                getResource("issue-regular.fxml"));
        loader.setController(this);
        try {
            loader.load();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setLabels(Label type, Label value) {
        if(type != null)
            type.setVisible(false);
        type = value;
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

    public void setTitle(String title) {
        this.title.setText(title);
    }

    public void setId(Integer id) {
        this.id.setText(
                id.toString());
    }

    public void setUsername(String username) {
        this.username.setText(username);
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

    @Override
    public Parent getRoot() {
        return issueCard;
    }
}