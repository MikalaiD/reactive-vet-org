package com.vet.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class StrategyProvider {
    private Map<String, VettingAgency> vettingStrategies;

    @Autowired
    private void setup(Set<VettingAgency> parties) {
        vettingStrategies = parties.stream().collect(Collectors.toMap(VettingAgency::toString, p -> p));
    }

    public List<VettingAgency> getStrategies(final String name){
        if(name.equals("Reacher")){
            return getStrategies(List.of("CIA", "MI6", "DSS"));
        } else {
            return getStrategies(List.of("MI6"));
        }
    }

    private List<VettingAgency> getStrategies(final List<String> parties){
        return parties.stream().map(vettingStrategies::get).collect(Collectors.toList());
    }
}
