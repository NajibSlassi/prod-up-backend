package com.perso.back.task_planner.infra.repository;

import com.perso.back.task_planner.infra.dto.Privilege;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;


@Repository
public class PrivilegeRepository {

    private static final String QUERY_FIND_PRIVILEGE_BY_NAME =
            "FROM Privilege as privilege "
                    + "where name = :name";

    @PersistenceContext
    private final EntityManager entityManager;


    public PrivilegeRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }


    public Privilege getByName(String name) {
        Session session = entityManager.unwrap(Session.class);
        Query<Privilege> query = session.createQuery(QUERY_FIND_PRIVILEGE_BY_NAME, Privilege.class);
        query.setParameter("name", name);
        boolean doEvict = true;
        Privilege privilege = null;
        try {
            privilege = query.getSingleResult();
        } catch (NoResultException e) {
            doEvict = false;
        }
        if (doEvict) session.evict(privilege);
        return privilege;
    }

    public Long save(Privilege privilege) {

        Session session = entityManager.unwrap(Session.class);

        session.save(privilege);

        return privilege.getId();
    }
}

