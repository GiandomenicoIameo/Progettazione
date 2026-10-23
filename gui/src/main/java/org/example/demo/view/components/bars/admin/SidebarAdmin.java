package org.example.demo.view.components.bars.admin;

import javafx.fxml.FXML;
import javafx.scene.layout.VBox;
import org.example.demo.view.components.bars.Sidebar;
import org.example.demo.view.listeners.SidebarAdminListener;

public class SidebarAdmin extends Sidebar {
    private SidebarAdminListener adminListener;

    @FXML private VBox sidebar;

    public void setAdminListener(SidebarAdminListener listener) {
        super.setListener(listener);
        this.adminListener = listener;
    }

    @FXML
    public void close() {
        System.exit(0);
    }

    @FXML
    public void viewGroup() {
        if(adminListener != null)
            adminListener.viewGroup();
    }
}
