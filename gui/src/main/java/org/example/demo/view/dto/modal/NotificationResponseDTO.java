package org.example.demo.view.dto.modal;

import com.google.gson.annotations.SerializedName;

import java.time.LocalDateTime;

public class NotificationResponseDTO {
    @SerializedName("id")
    private Integer id;

    @SerializedName("testo")
    private String description;

    @SerializedName("data")
    private LocalDateTime date;

    @SerializedName("isLetta")
    private boolean isRead;

    public Integer getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public boolean isRead() {
        return isRead;
    }
}
