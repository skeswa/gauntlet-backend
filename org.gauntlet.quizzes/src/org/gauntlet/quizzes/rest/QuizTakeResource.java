package org.gauntlet.quizzes.rest;

import java.io.IOException;
import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.gauntlet.core.api.ApplicationException;
import org.gauntlet.core.api.dao.NoSuchModelException;
import org.gauntlet.quizzes.api.dao.IQuizDAOService;
import org.gauntlet.quizzes.api.dao.IQuizTakeDAOService;
import org.gauntlet.quizzes.api.model.Quiz;
import org.gauntlet.quizzes.api.model.QuizProblemAnswer;
import org.gauntlet.quizzes.api.model.QuizTake;
import org.gauntlet.quizzes.api.model.QuizType;
import org.osgi.service.log.LogService;


@Path("quizzes/attempts")
public class QuizTakeResource {
	private volatile LogService logger;
	private volatile IQuizTakeDAOService quizTakeService;
	private volatile IQuizDAOService quizService;
	
	/**
	 * 
	 * ========= Quiz Take
	 */
	@GET
    @Produces(MediaType.APPLICATION_JSON)
	@Path("/")
    public List<QuizTake> all(@QueryParam("start") int start, @QueryParam("end") int end) throws ApplicationException {
		return quizTakeService.findAll(start, end);
    }
	
    @GET 
    @Path("{quizTakeId}") 
    @Produces(MediaType.APPLICATION_JSON) 
    public QuizTake getQuiz(@PathParam("quizTakeId") long quizTakeId) throws NoSuchModelException, ApplicationException {
		return quizTakeService.getByPrimary(quizTakeId);
    }
    
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public void updateQuizTake(QuizTake quizTake) throws ApplicationException {
		quizTakeService.update(quizTake);
	}

	@DELETE
	@Path("{quizTakeId}")
	public void delete(@PathParam("quizTakeId") long quizTakeId) throws NoSuchModelException, ApplicationException {
		quizTakeService.delete(quizTakeId);
	}
	
    
    @PUT 
    @Path("/") 
    @Consumes(MediaType.APPLICATION_JSON) 
    @Produces(MediaType.APPLICATION_JSON) 
    public QuizTake add(QuizTake quizTake) throws IOException, ApplicationException, NoSuchModelException { 
    	Long typeId = quizTake.getQuiz().getId();
    	Quiz q = quizService.getByPrimary(typeId);
    	quizTake.setQuiz(q);
    	return quizTakeService.add(quizTake);
    }

	/**
	 * 
	 * ========= Other
	 * @throws NoSuchModelException 
	 */
	@GET
    @Produces(MediaType.APPLICATION_JSON)
	@Path("answers/add/{quizTakeId}")
    public QuizTake addAnswers(@PathParam("quizTakeId") long quizTakeId, List<QuizProblemAnswer> answers) throws ApplicationException, NoSuchModelException {
		return quizTakeService.addAnswers(quizTakeId, answers);
    }
	
	@GET
    @Produces(MediaType.APPLICATION_JSON)
	@Path("answer/add/{quizTakeId}")
    public QuizTake addAnswer(@PathParam("quizTakeId") long quizTakeId, QuizProblemAnswer answer) throws ApplicationException, NoSuchModelException {
		return quizTakeService.addAnswer(quizTakeId, answer);
    }	
}
