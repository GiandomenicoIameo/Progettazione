package org.example.demo.view.access.factory;

import org.example.demo.view.dock.Dock;
import org.example.demo.view.dock.Administrator;

public class AdministratorFactory implements DockFactory {
    @Override
    public void createDock() {
        Dock dock = new Administrator();
        dock.show("administrator.fxml");
    }
}
