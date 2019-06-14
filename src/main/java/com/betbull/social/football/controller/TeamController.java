package com.betbull.social.football.controller;

import com.betbull.social.football.dto.TeamCreateRequest;
import com.betbull.social.football.dto.FootballersViewResponse;
import com.betbull.social.football.dto.TeamUpdateRequest;
import com.betbull.social.football.dto.TeamViewResponse;
import com.betbull.social.football.service.FootballerService;
import com.betbull.social.football.service.TeamService;
import com.betbull.social.football.validator.TeamCreateRequestValidator;
import com.betbull.social.football.validator.TeamUpdateRequestValidator;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.Collection;

@RestController
@RequestMapping(path = "/api/v1/team", produces = {MediaType.APPLICATION_JSON_VALUE})
@AllArgsConstructor
public class TeamController {

    private TeamService teamService;

    private final FootballerService footballerService;

    private final TeamCreateRequestValidator teamCreateRequestValidator;

    private final TeamUpdateRequestValidator teamUpdateRequestValidator;

    @InitBinder("teamCreateRequest")
    protected void initBinder(WebDataBinder binder) {
        binder.addValidators(teamCreateRequestValidator);

    }

    @InitBinder("teamUpdateRequest")
    protected void initTeamUpdateRequestBinder(WebDataBinder binder) {
        binder.addValidators(teamUpdateRequestValidator);
    }


    @PostMapping(path = "/create", consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Boolean> create(@Validated @RequestBody TeamCreateRequest teamCreateRequest) {

        return new ResponseEntity<>(teamService.insertTeam(teamCreateRequest), HttpStatus.OK);

    }

    @PostMapping(path = "/update", consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<TeamViewResponse> update(@Validated @RequestBody TeamUpdateRequest teamUpdateRequest) {

        return new ResponseEntity<>(teamService.updateTeam(teamUpdateRequest), HttpStatus.OK);

    }

    @DeleteMapping(path = "/delete")
    public ResponseEntity<Boolean> delete(@Validated @NotNull @RequestParam Long teamId) {

        return new ResponseEntity<>(teamService.deleteTeam(teamId), HttpStatus.OK);

    }

    @GetMapping(path = "/findByTeamId")
    public ResponseEntity<TeamViewResponse> findByTeamId(@Validated @NotNull @RequestParam Long teamId) {

        return new ResponseEntity<>(teamService.findById(teamId), HttpStatus.OK);

    }

    @GetMapping(path = "/allTeams")
    public ResponseEntity<Collection<TeamViewResponse>> allTeams() {

        return new ResponseEntity<>(teamService.getTeams(), HttpStatus.OK);

    }


    @GetMapping(path = "/teamFootballersHasContract")
    public ResponseEntity<Collection<FootballersViewResponse>> getTeamFootballersHasContract(@Validated @NotNull @RequestParam("teamId") int teamId, @RequestParam("year") int year) {

        return new ResponseEntity<>(footballerService.getTeamFootballersHasContract(teamId, year), HttpStatus.OK);
    }


}
