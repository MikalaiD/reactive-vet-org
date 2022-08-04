package com.vet.services;

import com.vet.model.VettingResults;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;

import java.time.Duration;
import java.util.Random;

@Service
@Slf4j
public class MI6 implements VettingAgency {

    @Override
    @SneakyThrows
    public Mono<VettingResults> vet(String name) {
        return Mono.fromCallable(() -> VettingStatus.CLEAN)
                .doOnNext(status -> log.info("<> " + this + " starting collecting data..."))
                .delayElement(Duration.ofMillis(500L * new Random().nextInt(10)))
                .map(status -> VettingResults.builder().agency(this.toString()).summary(status).build())
                .subscribeOn(Schedulers.boundedElastic())
                .doOnSuccess(results -> log.info("<> " + this + " results are ready"));
    }

    @Override
    public String toString() {
        return "MI6";
    }
}
