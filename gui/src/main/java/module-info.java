module org.example.demo {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;

    requires com.google.gson;
    requires java.net.http;
    // 1. Apriamo i pacchetti per permettere di leggere i file .fxml e i @FXML
    opens org.example.demo.view.dock to javafx.fxml;
    opens org.example.demo.view.access to javafx.fxml;
    opens org.example.demo.view.components.cards to javafx.fxml;
    opens org.example.demo.view.dto.modal to com.google.gson;
    opens org.example.demo.view.dto.cards to com.google.gson;
    opens org.example.demo.view.utils to javafx.fxml;

    // 2. Esportiamo a javafx.graphics tutti i pacchetti in cui hai una classe Main da poter avviare
    exports org.example.demo.view.dock to javafx.graphics;
    exports org.example.demo.view.access to javafx.graphics;
    exports org.example.demo.view.components.cards to javafx.graphics;
    exports org.example.demo.view.dto.views;

    exports org.example.demo.view.core to javafx.graphics;
    exports org.example.demo.view.components.bars.admin to javafx.graphics;
    opens org.example.demo.view.components.bars.admin to javafx.fxml;
    exports org.example.demo.view.components.bars.regular to javafx.graphics;
    opens org.example.demo.view.components.bars.regular to javafx.fxml;
    exports org.example.demo.view.listeners to javafx.graphics;
    opens org.example.demo.view.listeners to javafx.fxml;
    exports org.example.demo.view.components.bars to javafx.graphics;
    opens org.example.demo.view.components.bars to javafx.fxml;
    opens org.example.demo.view.dto.views to com.google.gson;
    exports org.example.demo.view.access.factory to javafx.graphics;
    opens org.example.demo.view.access.factory to javafx.fxml;
    exports org.example.demo.view.components.modal to javafx.graphics;
    opens org.example.demo.view.components.modal to javafx.fxml;
}