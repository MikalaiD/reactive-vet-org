package com.vet.services;

import com.vet.model.VettingResults;
import reactor.core.publisher.Mono;

public interface VettingAgency {

    Mono<VettingResults> vet(String name);

}
