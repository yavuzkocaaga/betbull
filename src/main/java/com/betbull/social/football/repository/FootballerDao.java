package com.betbull.social.football.repository;

import com.betbull.social.football.entity.FootballerTeam;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

@Repository
public class FootballerDao extends AbstractDao {

    public FootballerDao(EntityManager entityManager, EntityManagerFactory entityManagerFactory) {
        super(entityManager, entityManagerFactory);
    }


    //@formatter:off
    @Query("SELECT foot.footballerId FROM Footballer foot " +
            "WHERE foot.footballerId = :p_footballerId")
    //@formatter:on
    public Collection<Long> getFootballerTeamIds(Long footballerId) {

        final TypedQuery<Long> query = getQuery("getFootballerTeamIds", Long.class)
                .setParameter("p_footballerId", footballerId);

        return query.getResultList();
    }

}
