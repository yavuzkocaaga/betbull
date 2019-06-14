package com.betbull.social.football.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@Entity
@EqualsAndHashCode(callSuper = false)
public class Footballer extends BaseEntityModel {

    @Id
    @SequenceGenerator(name = "FOOTBALLER_GENERATOR", sequenceName = "FOOTBOLLER_SEQ", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "FOOTBALLER_GENERATOR")
    @Column(name = "footballer_id")
    public Long footballerId;

    @Column(unique = true)
    @NotNull
    @Size(max = 100)
    public String fullName;

    @NotNull
    @Column(unique = true)
    public LocalDate dateOfBirth;

    @Column
    @NotNull
    public String nationality;

    @Column
    @NotNull
    public Integer age;

    @Column
    @NotNull
    public Integer weight;

    @Column
    @NotNull
    public Integer height;

    @Column
    @NotNull
    public Integer shirtNumber;

    @Column
    @NotNull
    @Size(max = 20)
    public String position;

    @Column
    @NotNull
    public Integer experienceMonth;

    @Column
    @NotNull
    public Integer transferCost;

    @Column
    @NotNull
    public Double contractCost;

    @Column
    @NotNull
    private Double teamCommision;

    @OneToMany(cascade = CascadeType.ALL , fetch = FetchType.LAZY,mappedBy="team")
    private Set<FootballerTeam> teams = new HashSet<>();




}
