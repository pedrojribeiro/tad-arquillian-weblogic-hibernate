package com.tad.arquillian.wls.hibernate.dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.tad.arquillian.wls.hibernate.model.BasicEntity;

@Stateless
public class BasicEntityDAO {
	public void save(BasicEntity entity) {
		em.merge(entity);
	}
	
	public List<BasicEntity> getEntities() {
		return em.createQuery("from BasicEntity").getResultList();
	}
	
	@PersistenceContext(unitName="LOCALAPPS")
	private EntityManager em;
}
