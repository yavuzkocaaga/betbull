package com.betbull.social.football.service;

import com.betbull.social.football.dto.ContractRequest;
import com.betbull.social.football.entity.Footballer;
import com.betbull.social.football.entity.FootballerTeam;
import com.betbull.social.football.entity.Team;
import com.betbull.social.football.exception.BusinessException;
import com.betbull.social.football.repository.FootballerTeamDao;
import com.betbull.social.football.repository.FootballerTeamRepository;
import lombok.AllArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Locale;

@Service
@AllArgsConstructor
public class ContractService {

    private FootballerTeamRepository footballerTeamRepository;

    private TeamService teamService;

    private FootballerService footballerService;

    private MessageSource messageSource;

    private FootballerTeamDao footballerTeamDao;

    public Boolean createContract(ContractRequest request) {

        final Footballer footballer = footballerService.findEntityById(request.getFootballerId());
        if (footballer == null) {
            throw new BusinessException(messageSource.getMessage("app.error.footballer.not.found", null, Locale.getDefault()));
        }
        final Team team = teamService.findEntityById(request.getTeamId());

        if (team == null) {
            throw new BusinessException(messageSource.getMessage("app.error.team.not.found", null, Locale.getDefault()));
        }

        final LocalDate contractStartDate = request.getContractStartDate();
        final LocalDate contractEndDate = request.getContractEndDate();
        final Boolean hasContract = footballerTeamDao.hasContract(footballer.getFootballerId(),contractStartDate);


        if (hasContract) {
            {
                Object[] params = new Object[2];
                params[0] = footballer.getFullName();

                throw new BusinessException(messageSource.getMessage("app.error.team.has.contract.footballer", params, Locale.getDefault()));
            }
        }


        final FootballerTeam newContract = new FootballerTeam();
        newContract.setContractEndDate(contractEndDate);
        newContract.setContractStartDate(contractStartDate);
        newContract.setFootballer(footballer);
        newContract.setTeam(team);

        final FootballerTeam newContractSaved = footballerTeamRepository.save(newContract);
        if (newContractSaved != null) {
            return Boolean.TRUE;
        }


        return Boolean.FALSE;


    }


}
