package org.example.demo.view.access;

import javafx.application.Application;
import javafx.stage.Stage;
import org.example.demo.view.access.factory.AdministratorFactory;
import org.example.demo.view.access.factory.DockFactory;
import org.example.demo.view.access.factory.RegularFactory;
import org.example.demo.view.core.Sequence;

import java.util.HashMap;
import java.util.Map;

/* La seguente classe è stata creata con il solo
   scopo di testare le varie interfacce di accesso.
 */

public class Main extends Application {
    @Override
    public void start(Stage stage) {
        Map<String, DockFactory> map = new HashMap<>();

        // Registrazione degli utenti.
        map.put("AMMINISTRATORE",
                new AdministratorFactory());
        map.put("NORMALE",
                new RegularFactory());

        Login access = new Login(map);
        Sequence.setCurrStage(stage);
        access.show("login.fxml");

        // Quando il programma entra nel metodo start, Java fa due cose:
        //
        // 1) Crea la variabile locale access nello Stack di attivazione.
        // 2) Crea l'oggetto vero e proprio Register nell'Heap.
        //    La variabile access fa da "guinzaglio" a questo oggetto.
        //
        // Successivamente, viene passata l'interfaccia a Sequence
        // tramite access.show().
        // Ora Sequence ha un secondo "guinzaglio" che tiene in
        // vita l'interfaccia grafica.
        //
        //
        // Quando l'esecuzione arriva all'ultima parentesi graffa, il metodo
        // start termina il suo compito.
        // In questo esatto istante, Java fa pulizia automatica
        // dello Stack: la variabile access viene distrutta.
        //
        //
        // Il primo "guinzaglio" non c'è più. Tuttavia, l'oggetto Register
        // non viene rimosso dal Garbage Collector perché Sequence lo sta ancora
        // tenendo in vita per mostrarlo a schermo.
        //
        //
        // Quando l'utente innesca il cambio ed esegue il metodo skip(), Sequence lascia andare
        // il vecchio oggetto (l'interfaccia del Register) per agganciarsi alla nuova view.
        // La vecchia interfaccia Register rimane completamente isolata e senza alcun riferimento.
        //
        //
        // Il Garbage Collector, al suo prossimo ciclo di pulizia,
        // la eliminerà definitivamente liberando la RAM.
    }

    public static void main(String[] args) {
        launch();
    }
}
