package com.betbull.social.football.dto;

import com.betbull.social.football.entity.Team;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;

@Data
@EqualsAndHashCode(callSuper = false)
public class TeamDeleteRequest {

    @NotNull
    private Long teamId;


    public Team convertToTeam() {
        final Team team = new Team();
        team.setTeamId(teamId);
        return team;
    }


}
