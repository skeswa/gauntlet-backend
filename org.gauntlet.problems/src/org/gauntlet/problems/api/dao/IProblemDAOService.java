package org.gauntlet.problems.api.dao;

import java.util.List;

import org.gauntlet.core.api.ApplicationException;
import org.gauntlet.core.api.dao.IBaseDAO;
import org.gauntlet.problems.api.model.Problem;

public interface IProblemDAOService extends IBaseDAO<Problem> {
	public Problem provide(Problem record) throws ApplicationException;

	public Problem getByCode(String code) throws ApplicationException;

	public Problem getByName(String name) throws ApplicationException;
	
	//Difficulty
	public List<Problem> findByDifficulty(Long difficultyId, int start, int end) throws ApplicationException;
	
	public int countByDifficulty(Long difficultyId) throws ApplicationException;
}
