package org.example.demo.view.dto.modal;

import com.google.gson.annotations.SerializedName;
import org.example.demo.view.dto.views.RegularDTO;

public class AssignmentRequestDTO {
    @SerializedName("")
    private RegularDTO regularDTO;

    public void setRegularDTO(RegularDTO regularDTO) {
        this.regularDTO = regularDTO;
    }

    public RegularDTO getRegularDTO() {
        return regularDTO;
    }
}
