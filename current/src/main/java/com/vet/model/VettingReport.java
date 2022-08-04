package com.vet.model;

import com.vet.services.VettingStatus;
import lombok.Builder;
import lombok.Data;

import java.util.Map;

@Data
@Builder
public class VettingReport {
    private final VettingStatus summary;
    private final Map<String, VettingStatus> sourcingDetails;

}
