package com.vet.model;

import com.vet.services.VettingStatus;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class VettingResults {
    private final String agency;
    private final VettingStatus summary;
}
