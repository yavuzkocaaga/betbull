package com.betbull.social.football.dto;

import com.betbull.social.football.entity.Footballer;
import com.neovisionaries.i18n.CountryCode;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.util.ObjectUtils;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = false)
public class FootBallerViewReponse {

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
    private List<TeamViewResponse> teams = new ArrayList<>();

    public FootBallerViewReponse(Footballer footballer) {

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
    }


}
