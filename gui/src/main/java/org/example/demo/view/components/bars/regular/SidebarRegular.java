package org.example.demo.view.components.bars.regular;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.CustomMenuItem;
import javafx.scene.control.MenuButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.example.demo.view.components.bars.Sidebar;
import org.example.demo.view.components.cards.Notice;
import org.example.demo.view.dto.modal.NotificationResponseDTO;
import org.example.demo.view.listeners.SidebarRegularListener;
import org.example.demo.view.service.NoticeService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SidebarRegular extends Sidebar {
    NoticeService noticeService = new NoticeService();

    private SidebarRegularListener regularListener;

    @FXML private VBox sidebar;
    @FXML private CustomMenuItem notice;
    @FXML private VBox container;
    @FXML private ScrollPane scroll;

    @FXML
    public void initialize() throws IOException, InterruptedException {
        importCard();
    }

    public void setRegularListener(SidebarRegularListener listener) {
        super.setListener(listener);
        this.regularListener = listener;
    }

    @FXML
    public void viewAssignments() throws IOException, InterruptedException {
        if(regularListener != null)
            regularListener.viewAssignments();
    }

    @FXML
    public void showNotice() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass()
                    .getResource("/org/example/demo/view/components/modal/notifybar.fxml"));
            Parent root = loader.load();

            Scene scenaModale = new Scene(root);
            scenaModale.setFill(Color.TRANSPARENT);

            Stage modale = new Stage();

            modale.initStyle(StageStyle.UNDECORATED);
            modale.setScene(scenaModale);

            modale.initModality(Modality.APPLICATION_MODAL);
            modale.setOnShown(event -> {
                Rectangle2D limitiSchermo = Screen.getPrimary().getVisualBounds();

                double centroX = (limitiSchermo.getWidth() - modale.getWidth()) / 2;
                double centroY = (limitiSchermo.getHeight() - modale.getHeight()) / 2;

                modale.setX(centroX);
                modale.setY(centroY);
            });

            ColorAdjust oscuramento = new ColorAdjust();
            oscuramento.setBrightness(-0.09);
            sidebar.getScene().getRoot().setEffect(oscuramento);

            modale.showAndWait();

            sidebar.getScene().getRoot().setEffect(null);

        } catch (IOException e) {
            System.err.println("Errore nel caricamento del file labels.fxml");
            e.printStackTrace();
        }
    }

    public void loadCard() {
        notice.setHideOnClick(false);

        Notice notice1 = new Notice();
        Notice notice2 = new Notice();

        notice.getStyleClass().add("non-interactive-item");

        container.getChildren()
                .addAll(notice1.getRoot(),
                        notice2.getRoot());

        notice.setContent(container);
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
                    setDate(card, notice);
                    list.add(card);
                }); return list;
    }

    public void importCard() throws IOException,
            InterruptedException {
        List<Notice> list = convert();

        list.forEach(card -> {
            container.getChildren().
                    addAll(card.getRoot());
        });
        notice.getStyleClass()
                .add("non-interactive-item");
        notice.setContent(scroll);
    }
}
