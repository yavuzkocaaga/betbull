package com.betbull.social.football.repository;

import com.betbull.social.football.entity.FootballerTeam;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import java.time.LocalDate;
import java.util.List;

@Repository
public class FootballerTeamDao extends AbstractDao {

    public FootballerTeamDao(EntityManager entityManager, EntityManagerFactory entityManagerFactory) {
        super(entityManager, entityManagerFactory);
    }

    //@formatter:off
    @Query("SELECT count(ft) " +
            "FROM FootballerTeam ft " +
            " WHERE ft.footballer.footballerId = :p_footballerId and ft.contractEndDate >= :p_contractStartDate ")

    //@formatter:on
    public Boolean hasContract(Long footballerId, LocalDate contractStartDate) {

        final TypedQuery<Long> query = getQuery("hasContract", Long.class)
                .setParameter("p_footballerId", footballerId)
                .setParameter("p_contractStartDate", contractStartDate);

        return query.getSingleResult() > 0;
    }

    //@formatter:off
    @Query("SELECT ft FROM FootballerTeam ft " +
            "WHERE :p_year between ft.contractStartDate and ft.contractEndDate and ft.team.teamId = :p_teamId")
    //@formatter:on
    public List<FootballerTeam> getFootballersByTeamAndHasContractYear(Long teamId, LocalDate year) {

        final TypedQuery<FootballerTeam> query = getQuery("getFootballersByTeamAndHasContractYear", FootballerTeam.class)
                .setParameter("p_teamId", teamId)
                .setParameter("p_year", year);

        return query.getResultList();
    }
}
