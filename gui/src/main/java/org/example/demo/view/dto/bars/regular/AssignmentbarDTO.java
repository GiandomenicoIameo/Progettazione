package org.example.demo.view.dto.bars.regular;

import org.example.demo.view.dto.cards.IssueCardResponseDTO;

import java.util.Map;
import java.util.Set;

public class AssignmentbarDTO {
    private Map<IssueCardResponseDTO,String> map;

    public void setDescription(IssueCardResponseDTO card, String description) {
        map.put(card,description);
    }

    public String getDescription(IssueCardResponseDTO card) {
        return map.get(card);
    }

    public Set<IssueCardResponseDTO> getListCards() {
        return map.keySet();
    }
}
