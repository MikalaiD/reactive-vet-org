package com.vet.services;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.vet.model.VettingReport;
import com.vet.model.VettingResults;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import org.springframework.stereotype.Service;

import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
@Slf4j
public class VettingService {

    private final StrategyProvider strategyProvider;

    /**POINT_OF_INTEREST - Assuming we have several independent services
     * from which we need aggregated data before we could to proceed next (e.g. to render a summary and return it to a user).
     *
     * Is it justified to use zip of iterable monos in a situation like that? Or rather some solution with Flux must be used?
     * If it is not justified - what should happen to do so? What are the cases similar approach would be ok?
     *
     *
     */
    public Mono<VettingReport> vet(final String name) {
        List<Mono<VettingResults>> asyncResults = strategyProvider.getStrategies(name).stream().map(s -> s.vet(name)).collect(toList());
        return Flux
                .merge(asyncResults)
                .collectList()
                .map(this::resultsCombinator)
                .doOnSuccess(report -> log.info("<> Vetting report is ready"));
    }

    private VettingReport resultsCombinator(List<VettingResults> vettingResults) {
        final Map<String, VettingStatus> agenciesVettingResults = vettingResults
                .stream()
                .collect(Collectors.toMap(VettingResults::getAgency, VettingResults::getSummary));
        final VettingStatus summary = summarize(agenciesVettingResults);
        return VettingReport.builder().sourcingDetails(agenciesVettingResults).summary(summary).build();
    }

    private VettingStatus summarize(final Map<String, VettingStatus> agenciesVettingResults) {
        return agenciesVettingResults
                .values()
                .stream()
                .filter(results -> !results.equals(VettingStatus.CLEAN))
                .findAny()
                .map(s -> VettingStatus.SUSPICIOUS)
                .orElse(VettingStatus.CLEAN);
    }


}
