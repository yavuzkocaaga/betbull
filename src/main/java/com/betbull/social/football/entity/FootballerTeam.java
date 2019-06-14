package com.betbull.social.football.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Data
@Entity
public class FootballerTeam extends BaseEntityModel {

    @Id
    @SequenceGenerator(name = "FOOT_TEAM_GENERATOR", sequenceName = "FOOT_TEAM_SEQ", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "FOOT_TEAM_GENERATOR")
    private Long footballerTeamId;

    @Column
    @NotNull
    private LocalDate contractStartDate;

    @Column
    @NotNull
    private LocalDate contractEndDate;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id",referencedColumnName = "team_id")
    private Team team;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "footballer_id",referencedColumnName = "footballer_id")
    private Footballer footballer;


}
