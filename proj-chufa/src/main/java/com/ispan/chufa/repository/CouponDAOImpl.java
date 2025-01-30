package com.ispan.chufa.repository;

import java.util.ArrayList;


import java.util.List;

import org.springframework.stereotype.Repository;

import com.ispan.chufa.domain.CouponBean;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

@Repository
public class CouponDAOImpl implements CouponDAO {

	@PersistenceContext
    private EntityManager entityManager;
	
	@Override
	public List<CouponBean> findByTitleAndRemaining(String title, String couponCode) {
		
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<CouponBean> cq = cb.createQuery(CouponBean.class);
        Root<CouponBean> root = cq.from(CouponBean.class);
        List<Predicate> predicates = new ArrayList<>();
        
        if (couponCode != null) {
            predicates.add(cb.equal(root.get("couponCode"), couponCode));
        }
        if (title != null && !title.isBlank()) {
            predicates.add(cb.equal(root.get("title"), title));
        }
        
        cq.select(root);
        if (!predicates.isEmpty()) {
            cq.where(cb.and(predicates.toArray(new Predicate[0])));
        }
        
        return entityManager.createQuery(cq).getResultList();}
}