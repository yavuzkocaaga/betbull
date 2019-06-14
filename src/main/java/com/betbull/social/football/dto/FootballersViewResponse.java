package com.betbull.social.football.dto;

import com.betbull.social.football.entity.Footballer;
import com.betbull.social.football.entity.FootballerTeam;
import com.neovisionaries.i18n.CountryCode;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = false)
public class FootballersViewResponse {

    private Long footballerId;
    private String fullName;
    private String nationality;
    private String position;
    private String contractCost;
    private String teamCommision;
    private LocalDate dateOfBirth;
    private Integer age;
    private Integer weight;
    private Integer height;
    private Integer shirtNumber;
    private Integer experienceMonth;
    private String transferCost;
    private LocalDate contractStartDate;
    private LocalDate contractEndDate;

    public void convertFootballerToTeamFootballersViewResponse(FootballerTeam footballerTeam) {
        final Footballer footballer = footballerTeam.getFootballer();
        final CountryCode countryCode = CountryCode.getByCode(footballer.getNationality());
        this.setFootballerId(footballer.getFootballerId());
        this.setFullName(footballer.getFullName());
        this.setDateOfBirth(footballer.getDateOfBirth());
        this.setNationality(countryCode.getName());
        this.setAge(footballer.getAge());
        this.setWeight(footballer.getWeight());
        this.setHeight(footballer.getHeight());
        this.setShirtNumber(footballer.getShirtNumber());
        this.setPosition(footballer.getPosition());
        this.setExperienceMonth(footballer.getExperienceMonth());
        this.setTransferCost(footballer.getTransferCost() + " " + countryCode.getCurrency());
        this.setContractCost(footballer.getContractCost() + " " + countryCode.getCurrency());
        this.setTeamCommision(footballer.getTeamCommision() + " " + countryCode.getCurrency());
        this.setContractEndDate(footballerTeam.getContractEndDate());
        this.setContractStartDate(footballerTeam.getContractStartDate());

    }
}
