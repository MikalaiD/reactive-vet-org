package com.vet.services;

import com.vet.model.VettingResults;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
@Slf4j
public class DSS implements VettingAgency {

    //https://en.wikipedia.org/wiki/State_Security_Service_(Nigeria)

    @Override
    @SneakyThrows
    public VettingResults vet(String name) {
        log.info("<> " + this.toString() + " starting collecting data...");
        Thread.sleep(1000L * new Random().nextInt(10));
        log.info("<> " + this.toString() + " has the report on the subject");
        return VettingResults.builder().summary(Math.random() > 0.8d ? VettingStatus.MOST_WANTED : VettingStatus.CLEAN)
                .agency(this.toString())
                .build();
    }

    @Override
    public String toString() {
        return "DSS";
    }
}
