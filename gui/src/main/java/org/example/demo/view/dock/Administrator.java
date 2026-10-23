package org.example.demo.view.dock;

import javafx.fxml.FXML;
import org.example.demo.view.components.bars.admin.*;
import org.example.demo.view.components.bars.Feedbar;
import org.example.demo.view.listeners.SidebarAdminListener;

public class Administrator extends Dock implements SidebarAdminListener {
    @FXML private SidebarAdmin sidebarController;
    @FXML private Feedbar feedbarController;
    @FXML private Groupbar groupbarController;
    @FXML private Settingsbar settingsbarController;

    public Administrator() {
        super();
    }

    @FXML
    public void initialize() {
        config();
        setListeners();
    }

    private void setListeners() {
        sidebarController.setAdminListener(this);
    }

    private void config() {
        // Le seguenti istruzioni sono opzionali, in quanto vengono
        // utilizzate solo per mantenere la relazione di composizione.

        addComponent(
                sidebarController);
        addComponent(
                feedbarController);
        addComponent(
                groupbarController);
        addComponent(
                settingsbarController);

        current = feedbarController;
    }

    @Override
    public void viewFeed() {
        current(feedbarController);
    }

    @Override
    public void viewGroup() {
        current(groupbarController);
    }

    @Override
    public void viewSettings() {
        current(settingsbarController);
    }
}
