package org.example.demo.view.components.cards;

import javafx.scene.Parent;
import org.example.demo.view.core.Component;

public abstract class Card extends Component {
    public static Card createIssueCard() {
        return new IssueCardAdmin();
    }

    public abstract Parent getRoot();
}
