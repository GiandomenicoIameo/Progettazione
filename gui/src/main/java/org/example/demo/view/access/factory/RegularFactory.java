package org.example.demo.view.access.factory;

import org.example.demo.view.dock.Dock;
import org.example.demo.view.dock.Regular;

public class RegularFactory implements DockFactory {
    @Override
    public void createDock() {
        Dock dock = new Regular();
        dock.show("regular.fxml");
    }
}
