package com.vet.services;

import com.vet.model.VettingResults;
import reactor.core.publisher.Mono;

import java.util.function.Function;

public interface VettingAgency {

    Mono<VettingResults> vet(String name);

}
