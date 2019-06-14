package com.betbull.social.football.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;

@Data
@EqualsAndHashCode(callSuper = false)
public class TeamFootballersViewRequest {

    @NotNull
    private Long teamId;

    @NotNull
    private int year;


}
