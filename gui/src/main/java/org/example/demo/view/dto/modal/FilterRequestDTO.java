package org.example.demo.view.dto.modal;

import com.google.gson.annotations.SerializedName;

public class FilterRequestDTO {
    @SerializedName("")
    private String priority;

    @SerializedName("")
    private String state;

    @SerializedName("")
    private String typology;

    @SerializedName("")
    private String order;

    @SerializedName("")
    private boolean assigned;

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public void setState(String state) {
        this.state = state;
    }

    public void setTypology(String typology) {
        this.typology = typology;
    }

    public void setOrder(String order ) {
        this.order = order;
    }

    public void setAssigned(boolean assigned) {
        this.assigned = assigned;
    }

    public String getPriority() {
        return priority;
    }

    public String getState() {
        return state;
    }

    public String getTypology() {
        return typology;
    }

    public String getOrder() {
        return order;
    }

    public boolean getAssigned() {
        return assigned;
    }
}
