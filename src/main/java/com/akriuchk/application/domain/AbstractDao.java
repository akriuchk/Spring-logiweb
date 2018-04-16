package com.akriuchk.application.domain;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.ParameterExpression;
import javax.persistence.criteria.Root;
import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;

public abstract class AbstractDao<PK extends Serializable, T> {

    private final Class<T> persistentClass;

    @PersistenceContext
    private EntityManager entityManager;

    @SuppressWarnings("unchecked")
    public AbstractDao() {
        this.persistentClass =
                (Class<T>) ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[1];
    }

    @SuppressWarnings("unchecked")
    public T getByKey(PK key) {
        return (T) entityManager.find(persistentClass, key);
    }

    public long getEntityCount() {
        CriteriaBuilder criteriaBuilder = createCriteriaBuilder();
        CriteriaQuery<Long> countQuery = criteriaBuilder.createQuery(Long.class);
        countQuery.select(criteriaBuilder
                .count(countQuery.from(persistentClass)));
        return entityManager.createQuery(countQuery)
                .getSingleResult();
    }


    public List<T> getAllPaged(int offset, int pageSize) {
        CriteriaQuery<T> criteriaQuery = createCriteriaBuilder().createQuery(persistentClass);
        Root<T> from = criteriaQuery.from(persistentClass);
        CriteriaQuery<T> select = criteriaQuery.select(from);

        TypedQuery<T> typedQuery = entityManager.createQuery(select);
        typedQuery.setFirstResult(offset);
        typedQuery.setMaxResults(pageSize);
//        System.out.println("Current page: " + typedQuery.getResultList());
        return typedQuery.getResultList();
    }


    public <S> List<T> proceedTypedQ(CriteriaQuery<T> query, ParameterExpression<S> parameterExpression, S paramValue) {
        TypedQuery<T> typedQuery = entityManager.createQuery(query);
        typedQuery.setParameter(parameterExpression, paramValue);
        return typedQuery.getResultList();
    }

    protected EntityManager getEntityManager() {
        return entityManager;
    }

    public void persist(T entity) {
        entityManager.persist(entity);
    }

    public T update(T entity) {
        return entityManager.merge(entity);
    }

    public void delete(T entity) {
        entityManager.remove(entity);
    }

    public CriteriaBuilder createCriteriaBuilder() {
        return entityManager.getCriteriaBuilder();
    }
//
//    protected Criteria createEntityCriteria() {
//        entityManager.getCriteriaBuilder().cre
//        return getSession().createCriteria(persistentClass);
//    }

}
