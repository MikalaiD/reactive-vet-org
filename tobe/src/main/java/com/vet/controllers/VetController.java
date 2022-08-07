package com.vet.controllers;

import com.vet.model.VettingReport;
import com.vet.services.VettingService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.scheduler.Schedulers;

@RestController(value = "/v2")
@RequiredArgsConstructor
public class VetController {

    private final VettingService vettingService;

    @GetMapping("/vet/{name}")
    public VettingReport vet(@PathVariable final String name){
        return vettingService.vet(name).subscribeOn(Schedulers.boundedElastic()).block();
    }
}
