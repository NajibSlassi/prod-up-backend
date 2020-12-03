package com.perso.back.task_planner.infra.repository;

import com.perso.back.task_planner.infra.dto.Privilege;
import com.perso.back.task_planner.infra.dto.Role;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

@Repository
public class RoleRepository {

    private static final String QUERY_FIND_ROLE_BY_NAME =
            "FROM Role as role "
                    + "where name = :name";

    @PersistenceContext
    private final EntityManager entityManager;

    public RoleRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }


    public Role getByName(String name) {
        Session session = entityManager.unwrap(Session.class);
        Query<Role> query = session.createQuery(QUERY_FIND_ROLE_BY_NAME, Role.class);
        query.setParameter("name", name);

        boolean doEvict = true;
        Role role = null;
        try {
            role = query.getSingleResult();
        } catch (NoResultException e) {
            doEvict = false;
        }
        if (doEvict) session.evict(role);
        return role;
    }

    public Long save(Role role) {

        Session session = entityManager.unwrap(Session.class);

            session.save(role);

            return role.getId();
    }
}

