package org.example.demo.view.core;

import javafx.scene.Parent;
import org.example.demo.view.utils.Window;

import java.util.ArrayList;
import java.util.List;

public abstract class Component extends Window {
    protected List<Component> list = new ArrayList<>();

    public void addComponent(Component component) {
        list.add(component);
    }

    public Parent getRoot() {
        return null;
    }
}
