package org.example.demo.view.dock;

import javafx.fxml.FXML;
import org.example.demo.view.components.bars.regular.Settingsbar;
import org.example.demo.view.components.bars.regular.Assignmentbar;
import org.example.demo.view.components.bars.Feedbar;
import org.example.demo.view.components.bars.regular.SidebarRegular;
import org.example.demo.view.listeners.SidebarRegularListener;

public class Regular extends Dock implements SidebarRegularListener {
    @FXML private SidebarRegular sidebarController;
    @FXML private Assignmentbar assignmentsbarController;
    @FXML private Feedbar feedbarController;
    @FXML private Settingsbar settingsbarController;

    public Regular() {
        super();
    }

    @FXML
    public void initialize() {
        config();
        setListeners();
    }

    private void config() {
        // Le seguenti istruzioni sono opzionali, in quanto vengono
        // utilizzate solo per mantenere la relazione di composizione.

        addComponent(
                sidebarController);
        addComponent(
                feedbarController);
        addComponent(
                assignmentsbarController);
        addComponent(
                settingsbarController);

        current = feedbarController;
    }

    private void setListeners() {
        sidebarController.setRegularListener(this);
    }

    @Override
    public void viewFeed() {
        current(feedbarController);
    }

    @Override
    public void viewAssignments() {
        current(assignmentsbarController);
    }

    @Override
    public void viewSettings() {
        current(settingsbarController);
    }
}
