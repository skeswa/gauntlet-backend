package org.gauntlet.quizzes.api.dao;

import java.util.List;

import org.gauntlet.core.api.ApplicationException;
import org.gauntlet.core.api.dao.NoSuchModelException;
import org.gauntlet.core.api.service.IBaseService;
import org.gauntlet.quizzes.api.model.Quiz;
import org.gauntlet.quizzes.api.model.QuizType;

public interface IQuizDAOService extends IBaseService {
	// Quizzes
	public List<Quiz> findAll(int start, int end) throws ApplicationException;
	
	public long countAll() throws ApplicationException;
	
	public Quiz provide(Quiz record) throws ApplicationException;
	
	public Quiz update(Quiz record) throws ApplicationException;
	
	public Quiz delete(Long id) throws ApplicationException, NoSuchModelException;
	
	public Quiz getByPrimary(Long pk) throws ApplicationException, NoSuchModelException;

	public Quiz getByCode(String code) throws ApplicationException;

	public Quiz getByName(String name) throws ApplicationException;
	
	public List<Quiz> findByQuizType(Long difficultyId, int start, int end) throws ApplicationException;
	
	public int countByQuizType(Long difficultyId) throws ApplicationException;

	//QuizType
	public List<QuizType> findAllQuizTypes(int start, int end) throws ApplicationException;
	
	public long countAllQuizTypes() throws ApplicationException;
	
	public QuizType getQuizTypeByPrimary(Long pk)  throws ApplicationException, NoSuchModelException;
	
	public QuizType provideQuizType(QuizType record) throws ApplicationException;
	
	public QuizType getQuizTypeByCode(String code) throws ApplicationException;
	
	public QuizType getQuizTypeByName(String name) throws ApplicationException;
	
	public QuizType deleteQuizType(Long id) throws ApplicationException, NoSuchModelException;	
}
