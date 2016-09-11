package org.gauntlet.problems.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.ParameterExpression;
import javax.persistence.criteria.Root;

import org.amdatu.jta.Transactional;
import org.gauntlet.core.api.ApplicationException;
import org.gauntlet.core.commons.util.Validator;
import org.osgi.service.log.LogService;
import org.gauntlet.problems.api.dao.IProblemDAOService;
import org.gauntlet.problems.api.model.Problem;
import org.gauntlet.problems.model.jpa.JPAProblem;


@SuppressWarnings("restriction")
@Transactional
public class ProblemDAOImpl extends BaseDAOImpl<Problem> implements IProblemDAOService {
	
	private volatile LogService logger;
	
	private volatile EntityManager em;
	
	@Override
	public LogService getLogger() {
		return logger;
	}

	public void setLogger(LogService logger) {
		this.logger = logger;
	}

	@Override
	public EntityManager getEm() {
		return em;
	}	


	@Override
	public Problem provide(Problem record)
			  throws ApplicationException {
		Problem existing = getByCode(record.getCode());
		if (Validator.isNull(existing))
		{
			existing = add(record);
		}
		return existing;
	}
	
	@Override
	public Problem getByCode(String code) throws ApplicationException {
		return super.findWithAttribute(Problem.class, String.class,"code", code);
	}


	@Override
	public Problem getByName(String name) throws ApplicationException {
		return super.findWithAttribute(Problem.class, String.class,"name", name);
	}
	
	@Override 
	public List<Problem> findByDifficulty(Long difficultyId, int start, int end) throws ApplicationException {
		List<Problem> resultList = null;
		try {
			CriteriaBuilder builder = getEm().getCriteriaBuilder();
			CriteriaQuery<JPAProblem> query = builder.createQuery(JPAProblem.class);
			Root<JPAProblem> rootEntity = query.from(JPAProblem.class);
			
			ParameterExpression<Long> p = builder.parameter(Long.class);
			query.select(rootEntity).where(builder.gt(rootEntity.get("difficulty").get("id"),p));
			query.select(rootEntity);
			
			Map<ParameterExpression,Object> pes = new HashMap<>();
			pes.put(p, difficultyId);
			
			resultList = findWithDynamicQueryAndParams(query,pes,start,end);
		}
		catch (Exception e) {
			throw processException(e);
		}
		return resultList;		
	}
	
	@Override 
	public int countByDifficulty(Long difficultyId) throws ApplicationException {
		int count = 0;
		try {
			CriteriaBuilder builder = getEm().getCriteriaBuilder();
			CriteriaQuery<JPAProblem> query = builder.createQuery(JPAProblem.class);
			Root<JPAProblem> rootEntity = query.from(JPAProblem.class);
			
			ParameterExpression<Long> p = builder.parameter(Long.class);
			query.select(rootEntity).where(builder.gt(rootEntity.get("difficulty").get("id"),p));
			query.select(rootEntity);
			
			Map<ParameterExpression,Object> pes = new HashMap<>();
			pes.put(p, difficultyId);
			
			count = countWithDynamicQueryAndParams(query,pes);
		}
		catch (Exception e) {
			throw processException(e);
		}
		return count;		
	}	
}