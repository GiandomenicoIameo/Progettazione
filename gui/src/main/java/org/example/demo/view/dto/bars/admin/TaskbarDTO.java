package org.example.demo.view.dto.bars.admin;

import org.example.demo.view.dto.cards.IssueCardResponseDTO;

import java.util.List;

public class TaskbarDTO {
    private List<IssueCardResponseDTO> cards;

    public void setListCards(List<IssueCardResponseDTO> cards) {
        this.cards = cards;
    }

    public List<IssueCardResponseDTO> getListCards() {
        return cards;
    }
}
