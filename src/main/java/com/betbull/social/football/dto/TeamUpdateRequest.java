package com.betbull.social.football.dto;

import com.betbull.social.football.entity.Team;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;

@Data
@EqualsAndHashCode(callSuper = false)
public class TeamUpdateRequest {


    @NotNull
    private String name;

    @NotNull
    private Long teamId;

    @NotNull
    private String countryCode;


    public Team convertToTeam() {
        final Team team = new Team();
        team.setName(name);
        team.setTeamId(teamId);
        this.setCountryCode(countryCode);
        return team;
    }


}
