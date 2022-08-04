package com.vet.services;

import com.vet.model.VettingReport;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class VettingService {

    private final StrategyProvider strategyProvider;

    public VettingReport vet(final String name){
        final Map<String, VettingStatus> agenciesVettingResults = strategyProvider.getStrategies(name)
                .parallelStream()
                .map(s -> Map.entry(s.toString(), s.vet(name).getSummary()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        final VettingStatus summary = summarize(agenciesVettingResults);
        log.info("<> Vetting report is ready");
        return  VettingReport.builder().sourcingDetails(agenciesVettingResults).summary(summary).build();
    }

    private VettingStatus summarize(final Map<String, VettingStatus> agenciesVettingResults) {
       return agenciesVettingResults
                .values()
                .stream()
                .filter(status -> !status.equals(VettingStatus.CLEAN))
                .findAny()
                .map(s->VettingStatus.SUSPICIOUS)
                .orElse(VettingStatus.CLEAN);
    }


}
