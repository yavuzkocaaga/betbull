package com.betbull.social.football.dto;

import com.betbull.social.football.entity.Team;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class TeamViewResponse {

    private Long teamId;
    private String name;
    private String countryCode;


    public TeamViewResponse(Team team){
        this.setName(team.getName());
        this.setTeamId(team.getTeamId());
        this.setCountryCode(team.getCountryCode());
    }

    public Team convertToTeam() {
        final Team team = new Team();
        team.setName(name);
        team.setTeamId(teamId);
        team.setCountryCode(countryCode);
        return team;
    }



}
