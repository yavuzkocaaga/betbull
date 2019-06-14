package com.betbull.social.football.service;

import com.betbull.social.football.dto.TeamCreateRequest;
import com.betbull.social.football.dto.TeamUpdateRequest;
import com.betbull.social.football.dto.TeamViewResponse;
import com.betbull.social.football.entity.Team;
import com.betbull.social.football.exception.BusinessException;
import com.betbull.social.football.repository.TeamRepository;
import lombok.AllArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@AllArgsConstructor
public class TeamService {

    private TeamRepository teamRepository;

    private MessageSource messageSource;

    public Boolean insertTeam(TeamCreateRequest request) {

        final Team team = request.convertToTeam();
        final Team teamCreated = teamRepository.save(team);
        return teamCreated.getTeamId() != null;

    }

    public TeamViewResponse updateTeam(TeamUpdateRequest request) {

        final Team updateTeam = teamRepository.findById(request.getTeamId()).orElse(null);
        if (updateTeam != null) {
            updateTeam.setName(request.getName());
            updateTeam.setCountryCode(request.getCountryCode());
            Team updatedTeam = teamRepository.save(updateTeam);
            return new TeamViewResponse(updatedTeam);
        } else {
            throw new BusinessException(messageSource.getMessage("app.error.team.not.found",null, Locale.getDefault()));
        }

    }

    public boolean deleteTeam(Long teamId) {

        final Team deleteTeam = teamRepository.findById(teamId).orElse(null);
        if (deleteTeam != null) {
            teamRepository.delete(deleteTeam);
        } else {
            throw new BusinessException(messageSource.getMessage("app.error.team.not.found",null,Locale.getDefault()));
        }

        return Boolean.TRUE;
    }

    public TeamViewResponse findById(Long teamId) {

        Optional<Team> optionalTeam = teamRepository.findById(teamId);

        if (optionalTeam.isPresent()) {

            return new TeamViewResponse(optionalTeam.get());

        } else {

            throw new BusinessException(messageSource.getMessage("app.error.team.not.found",null,Locale.getDefault()));

        }

    }

    public Team findEntityById(Long teamId) {

        Optional<Team> optionalTeam = teamRepository.findById(teamId);

        if (optionalTeam.isPresent()) {

            return optionalTeam.get();

        } else {

            throw new BusinessException(messageSource.getMessage("app.error.team.not.found",null,Locale.getDefault()));

        }

    }

    public List getTeams() {

        final Collection<Team> teams = teamRepository.findAll();
        final List teamViewResponses = new ArrayList();

        teams.forEach(team ->
                teamViewResponses.add(new TeamViewResponse(team)));

        return teamViewResponses;
    }
}
