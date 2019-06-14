package com.betbull.social.football.repository;

import com.betbull.social.football.entity.Footballer;
import com.betbull.social.football.entity.FootballerTeam;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface FootballerTeamRepository extends JpaRepository<FootballerTeam, Long> {

    FootballerTeam findFirstByContractEndDateGreaterThanAndFootballer(LocalDate contractStartDate, Footballer footballer);

    List<FootballerTeam> findByFootballer(Footballer footballer);

}
