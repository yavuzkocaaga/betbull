package com.betbull.social.football.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class TransferCalculationService {


    public int calculateCostBase(Integer experienceMonth, Integer age) {
        return (experienceMonth * 100000) / age;

    }

    public int calculateTransferCost(Integer experienceMonth, Integer age) {
        return calculateCostBase(experienceMonth, age);

    }

    public double calculateTeamCommission(int transferCost) {

        return transferCost * 0.1;
    }

    public double calculateContractCost(int transferCost, double teamCommision) {

        return transferCost + teamCommision;
    }

}
