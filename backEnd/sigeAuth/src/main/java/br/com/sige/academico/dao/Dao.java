package br.com.sige.academico.dao;

import javax.inject.Inject;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.io.Serializable;

public class Dao<T> implements Serializable {

    @Inject
    protected EntityManager em;

    @Transactional
    public void persist(T entity){
        this.em.persist(entity);
    }

    @Transactional
    public void merge(T entity){
        this.em.merge(entity);
    }
}
