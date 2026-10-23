package org.example.demo.view.components.modal;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.example.demo.view.components.cards.IssueCardAdmin;
import org.example.demo.view.components.cards.Notice;
import org.example.demo.view.core.Component;
import org.example.demo.view.dto.cards.IssueCardResponseDTO;
import org.example.demo.view.dto.modal.NotificationResponseDTO;
import org.example.demo.view.dto.views.UserDTO;
import org.example.demo.view.service.NoticeService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Notification extends Component {
    NoticeService noticeService = new NoticeService();

    @FXML private VBox notifybar;
    @FXML private VBox cardContainer;

    @FXML
    public void initialize() throws IOException,
            InterruptedException {
        importCard();
    }

    @FXML
    public void close(ActionEvent event) {
        Stage stage = (Stage) notifybar.getScene()
                .getWindow();
        stage.close();
    }

    public void setId(Notice card,
                      NotificationResponseDTO response) {
        card.setId(response.getId());
    }

    public void setDate(Notice card,
                        NotificationResponseDTO response) {
        card.setDate(response.getDate());
    }

    public List<Notice> convert() throws IOException,
            InterruptedException {
        List<Notice> list = new ArrayList<>();
        this.noticeService.listNotice()
                .forEach(notice -> {
                    Notice card = new Notice();
                    setId(card, notice); setDate(card, notice);
                    list.add(card);
                }); return list;
    }

    public void importCard() throws IOException,
            InterruptedException {
        List<Notice> list = convert();

        list.forEach(card -> {
            cardContainer.getChildren().
                    add(card.getRoot());
        });
    }
}
