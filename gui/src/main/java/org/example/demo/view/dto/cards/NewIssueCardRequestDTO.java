package org.example.demo.view.dto.cards;

import com.google.gson.annotations.SerializedName;
import org.example.demo.view.dto.views.UserDTO;

// Classe DTO in uscita
public class NewIssueCardRequestDTO {
    @SerializedName("titolo")
    private String title;

    @SerializedName("descrizione")
    private String description;

    @SerializedName("immagine")
    private String image;

    @SerializedName("tipo")
    private String typology;

    @SerializedName("priorita")
    private String priority;

    @SerializedName("utenteCreatore")
    private UserDTO userDTO;

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setTypology(String typology) {
        this.typology = typology;
    }

    public void setUserDTO(UserDTO userDTO) {
        this.userDTO = this.userDTO;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getImage() {
        return image;
    }

    public String getPriority() {
        return priority;
    }
}
