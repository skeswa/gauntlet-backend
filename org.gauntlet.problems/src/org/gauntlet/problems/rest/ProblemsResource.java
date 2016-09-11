package org.gauntlet.problems.rest;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.gauntlet.core.api.ApplicationException;
import org.gauntlet.core.api.dao.NoSuchModelException;
import org.gauntlet.problems.api.dao.IProblemDAOService;
import org.gauntlet.problems.api.model.Problem;


@Path("problems")
public class ProblemsResource {
	private volatile IProblemDAOService problemService;
	
	@GET
    @Produces(MediaType.APPLICATION_JSON)
	@Path("all/difficulty/{difficulty}/{start}/{end}")
    public List<Problem> listProducts(@PathParam("difficulty") long difficulty, @PathParam("start") int start, @PathParam("end") int end ) throws ApplicationException {
		return problemService.findByDifficulty(difficulty,start,end);
    }
	
    @GET 
    @Path("{problemId}") 
    @Produces(MediaType.APPLICATION_JSON) 
    public Problem getProblem(@PathParam("problemId") String problemId) throws NoSuchModelException, ApplicationException {
		return problemService.findByPrimaryKey(problemId);
    }
	
	@GET
    @Produces(MediaType.APPLICATION_JSON)
	@Path("all/{start}/{end}")
    public List<Problem> all(@PathParam("start") int start, @PathParam("end") int end) throws ApplicationException {
		return problemService.findAll(Problem.class,start,end);
    }	
	
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public void updateProblem(Problem problem) throws ApplicationException {
		problemService.update(problem);
	}

	@DELETE
	@Path("{problemId}")
	public void delete(@PathParam("problemId") String problemId) throws NoSuchModelException, ApplicationException {
		problemService.remove(problemId);
	}
}
