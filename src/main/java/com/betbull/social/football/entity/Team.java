package com.betbull.social.football.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
public class Team extends BaseEntityModel {

    @Id
    @SequenceGenerator(name = "TEAM_GENERATOR", sequenceName = "TEAM_SEQ", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TEAM_GENERATOR")
    @Column(name = "team_id")
    public Long teamId;

    @NotNull
    @Size(max = 65)
    @Column(name = "name", unique = true)
    private String name;

    @NotNull
    @Size(max = 5)
    @Column(name = "country_code")
    private String countryCode;

    @Column(name = "status")
    private Integer status = 1;

    @OneToMany(cascade = CascadeType.ALL , fetch = FetchType.LAZY,mappedBy="team")
    private Set<FootballerTeam> footballers = new HashSet<>();

}
