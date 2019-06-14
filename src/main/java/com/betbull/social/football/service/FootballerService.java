package com.betbull.social.football.service;

import com.betbull.social.football.dto.*;
import com.betbull.social.football.entity.Footballer;
import com.betbull.social.football.entity.FootballerTeam;
import com.betbull.social.football.exception.BusinessException;
import com.betbull.social.football.repository.FootballerRepository;
import com.betbull.social.football.repository.FootballerTeamDao;
import com.betbull.social.football.repository.FootballerTeamRepository;
import lombok.AllArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;

@Service
@AllArgsConstructor
public class FootballerService {
    private FootballerRepository footballerRepository;

    private FootballerTeamDao footballerTeamDao;

    private FootballerTeamRepository footballerTeamRepository;

    private TransferCalculationService transferCalculationService;

    private MessageSource messageSource;


    public Collection<FootBallerViewReponse> getFootballers() {

        final Collection<Footballer> footballers = footballerRepository.findAll();

        Collection<FootBallerViewReponse> footBallerViewReponseList = new ArrayList<>();

        footballers.forEach(footballerItem -> footBallerViewReponseList.add(new FootBallerViewReponse(footballerItem)));

        return footBallerViewReponseList;
    }

    public Collection<FootballersViewResponse> getTeamFootballersHasContract(int teamId, int year) {

        final LocalDate localDate = LocalDate.now();
        final long yearDiff = (long) year - localDate.getYear();

        final Collection<FootballerTeam> footballerTeams = footballerTeamDao.getFootballersByTeamAndHasContractYear((long) teamId, localDate.plusYears(yearDiff));

        final Collection<FootballersViewResponse> footballersViewResponsesList = new ArrayList<>();

        footballerTeams.forEach(footballerTeam -> {

            final FootballersViewResponse footballersViewResponse = new FootballersViewResponse();
            footballersViewResponse.convertFootballerToTeamFootballersViewResponse(footballerTeam);
            footballersViewResponsesList.add(footballersViewResponse);

        });
        return footballersViewResponsesList;
    }

    public FootBallerViewReponse insertFootballer(FootBallerCreateRequest footBallerCreateRequest) {

        final Footballer footballer = generateFootballer(footBallerCreateRequest);

        return new FootBallerViewReponse(footballerRepository.save(footballer));


    }

    public FootBallerViewReponse updateFootballer(FootBallerUpdateRequest footBallerUpdateRequest) {

        final Footballer footballer = generateFootballer(footBallerUpdateRequest);

        return new FootBallerViewReponse(footballerRepository.save(footballer));
    }

    public Boolean deleteFootballer(Long footballerId) {

        final Footballer footballer = footballerRepository.findById(footballerId).orElse(null);

        if (footballer == null) {

            throw new BusinessException(messageSource.getMessage("app.error.footballer.not.found", null, Locale.getDefault()));

        }

        footballerRepository.save(footballer);
        return Boolean.TRUE;

    }

    public FootBallerViewReponse findById(Long footballerId) {

        final Footballer footballer = findEntityById(footballerId);

        if (footballer == null) {

            throw new BusinessException(messageSource.getMessage("app.error.footballer.not.found", null, Locale.getDefault()));

        }

        final FootBallerViewReponse footBallerViewReponse = new FootBallerViewReponse(footballer);

        final List<FootballerTeam> footballerTeamList = footballerTeamRepository.findByFootballer(footballer);

        final List<TeamViewResponse> teamViewResponsesList = new ArrayList<>();


        footballerTeamList.forEach(footballerTeam -> {
            teamViewResponsesList.add(new TeamViewResponse(footballerTeam.getTeam()));
        });

        footBallerViewReponse.setTeams(teamViewResponsesList);

        return footBallerViewReponse;

    }

    Footballer findEntityById(Long footballerId) {

        final Footballer footballer = footballerRepository.findById(footballerId).orElse(null);

        if (footballer == null) {

            throw new BusinessException(messageSource.getMessage("app.error.footballer.not.found", null, Locale.getDefault()));

        }

        return footballer;

    }

    private Footballer generateFootballer(FootBallerRequest footBallerRequest) {

        Footballer footballerEntity;

        if (footBallerRequest instanceof FootBallerUpdateRequest) {

            final FootBallerUpdateRequest footBallerUpdateRequest = (FootBallerUpdateRequest) footBallerRequest;

            footballerEntity = this.findEntityById(footBallerUpdateRequest.getFootballerId());

            if (footballerEntity == null) {
                throw new BusinessException(messageSource.getMessage("app.error.footballer.not.found", null, Locale.getDefault()));
            }

            footballerEntity = footBallerUpdateRequest.convertToFootballer();

        } else {

            footballerEntity = footBallerRequest.convertToFootballer();


            final int age = footBallerRequest.getAge();
            final int experienceMonth = footBallerRequest.getExperienceMonth();
            final int transferCost = transferCalculationService.calculateTransferCost(experienceMonth, age);
            footballerEntity.setTransferCost(transferCost);

            final double teamCommission = transferCalculationService.calculateTeamCommission(transferCost);
            footballerEntity.setTeamCommision(teamCommission);

            final double contractCost = transferCalculationService.calculateContractCost(transferCost, teamCommission);
            footballerEntity.setContractCost(contractCost);
        }


        return footballerEntity;
    }


}
