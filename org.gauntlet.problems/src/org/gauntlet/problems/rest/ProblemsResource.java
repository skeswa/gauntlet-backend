package org.gauntlet.problems.rest;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
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
import org.gauntlet.problems.api.model.ProblemCategory;
import org.gauntlet.problems.api.model.ProblemDifficulty;
import org.gauntlet.problems.api.model.ProblemSource;
import org.osgi.service.log.LogService;


@Path("problems")
public class ProblemsResource {
	private volatile LogService logger;
	private volatile IProblemDAOService problemService;
	
	/**
	 * 
	 * ========= Problems
	 */
	@GET
    @Produces(MediaType.APPLICATION_JSON)
	@Path("all/difficulty/{difficulty}/{start}/{end}")
    public List<Problem> listProducts(@PathParam("difficulty") long difficulty, @PathParam("start") int start, @PathParam("end") int end ) throws ApplicationException {
		return problemService.findByDifficulty(difficulty,start,end);
    }
	@GET
    @Produces(MediaType.APPLICATION_JSON)
	@Path("all/{start}/{end}")
    public List<Problem> all(@PathParam("start") int start, @PathParam("end") int end) throws ApplicationException {
		return problemService.findAll(start, end);
    }	
	
    @GET 
    @Path("{problemId}") 
    @Produces(MediaType.APPLICATION_JSON) 
    public Problem getProblem(@PathParam("problemId") long problemId) throws NoSuchModelException, ApplicationException {
		return problemService.getByPrimary(problemId);
    }
    
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public void updateProblem(Problem problem) throws ApplicationException {
		problemService.update(problem);
	}

	@DELETE
	@Path("{problemId}")
	public void delete(@PathParam("problemId") long problemId) throws NoSuchModelException, ApplicationException {
		problemService.delete(problemId);
	}
	
	
    @POST 
    @Path("/provide") 
    @Consumes("multipart/form-data") 
    @Produces(MediaType.APPLICATION_JSON) 
    public Problem save(@FormParam("answer") String answer,
    					@FormParam("sourcePageNumber") Integer sourcePageNumber,
    					@FormParam("sourceIndexWithinPage") Integer sourceIndexWithinPage,
    					@FormParam("multipleChoice") Boolean multipleChoice,
    					@FormParam("requiresCalculator") Boolean requiresCalculator,
    					@FormParam("sourceId") Long sourceId,
    					@FormParam("categoryId") Long categoryId,
    					@FormParam("difficultyId") Long difficultyId,
    					@FormParam("answerPicture") File answerPicture,
    					@FormParam("questionPicture") File questionPicture) throws IOException, ApplicationException { 
 
    	
    	Problem newProblem = new Problem(
    					answer,
    					null,//source, 
    					null,//category, 
    					sourcePageNumber,
    					sourceIndexWithinPage, 
    					null,//ProblemDifficulty difficulty, 
    					readImageOldWay(answerPicture),//byte[] answerPicture, 
    					readImageOldWay(questionPicture),//byte[] questionPicture,
    					multipleChoice,
    					requiresCalculator);
    	
    	newProblem = problemService.provide(newProblem);
    	
        return newProblem; 
    } 	
    
    public byte[] readImageOldWay(File file) throws IOException
    {
      InputStream is = new FileInputStream(file);
      // Get the size of the file
      long length = file.length();
      // You cannot create an array using a long type.
      // It needs to be an int type.
      // Before converting to an int type, check
      // to ensure that file is not larger than Integer.MAX_VALUE.
      if (length > Integer.MAX_VALUE)
      {
        // File is too large
      }
      // Create the byte array to hold the data
      byte[] bytes = new byte[(int) length];
      // Read in the bytes
      int offset = 0;
      int numRead = 0;
      while (offset < bytes.length && (numRead = is.read(bytes, offset, bytes.length - offset)) >= 0)
      {
        offset += numRead;
      }
      // Ensure all the bytes have been read in
      if (offset < bytes.length)
      {
        throw new IOException("Could not completely read file " + file.getName());
      }
      // Close the input stream and return bytes
      is.close();
      return bytes;
    }    

	/**
	 * 
	 * ========= Problem cats
	 */
	@GET
    @Produces(MediaType.APPLICATION_JSON)
	@Path("categories/all/{start}/{end}")
    public List<ProblemCategory> allCategories(@PathParam("start") int start, @PathParam("end") int end) throws ApplicationException {
		return problemService.findAllProblemCategories(start, end);
    }	
	
	@GET
    @Produces(MediaType.APPLICATION_JSON)
	@Path("categories/count")
    public long countCategories() throws ApplicationException {
		return problemService.countAllProblemCategories();
    }	
	
    @GET 
    @Path("categories/{problemCategoryId}") 
    @Produces(MediaType.APPLICATION_JSON) 
    public ProblemCategory getProblemCategory(@PathParam("problemCategoryId") long problemCategoryId) throws NoSuchModelException, ApplicationException {
		return problemService.getProblemCategoryByPrimary(problemCategoryId);
    }
    
	@POST
	@Path("categories/provide")
	@Consumes(MediaType.APPLICATION_JSON)
	public void provideProblemCategory(ProblemCategory problemCategory) throws ApplicationException {
		problemService.provideProblemCategory(problemCategory);
	}

	@DELETE
	@Path("categories/{problemCategoryId}")
	public void deleteProbleCategory(@PathParam("problemCategoryId") long problemCategoryId) throws NoSuchModelException, ApplicationException {
		problemService.deleteProblemCategory(problemCategoryId);
	}	
	
	/**
	 * 
	 * ========= Problem diff's
	 */
	@GET
    @Produces(MediaType.APPLICATION_JSON)
	@Path("difficulties/all/{start}/{end}")
    public List<ProblemDifficulty> allDifficulties(@PathParam("start") int start, @PathParam("end") int end) throws ApplicationException {
		return problemService.findAllProblemDifficulties(start, end);
    }	
	
	@GET
    @Produces(MediaType.APPLICATION_JSON)
	@Path("difficulties/count")
    public long countDifficulties() throws ApplicationException {
		return problemService.countAllProblemDifficulties();
    }	
	
    @GET 
    @Path("difficulties/{problemDifficultyId}") 
    @Produces(MediaType.APPLICATION_JSON) 
    public ProblemDifficulty getProblemDifficulty(@PathParam("problemDifficultyId") long problemDifficultyId) throws NoSuchModelException, ApplicationException {
		return problemService.getProblemDifficultyByPrimary(problemDifficultyId);
    }
    
	@POST
	@Path("difficulties/provide")
	@Consumes(MediaType.APPLICATION_JSON)
	public void provideProblemDifficulty(ProblemDifficulty problemDifficulty) throws ApplicationException {
		problemService.provideProblemDifficulty(problemDifficulty);
	}

	@DELETE
	@Path("difficulties/{problemDifficultyId}")
	public void deleteProbleDifficulty(@PathParam("problemDifficultyId") long problemDifficultyId) throws NoSuchModelException, ApplicationException {
		problemService.deleteProblemDifficulty(problemDifficultyId);
	}	
	
	/**
	 * 
	 * ========= Problem src's
	 */
	@GET
    @Produces(MediaType.APPLICATION_JSON)
	@Path("sources/all/{start}/{end}")
    public List<ProblemSource> allSources(@PathParam("start") int start, @PathParam("end") int end) throws ApplicationException {
		return problemService.findAllProblemSources(start, end);
    }	
	
	@GET
    @Produces(MediaType.APPLICATION_JSON)
	@Path("sources/count")
    public long countSources() throws ApplicationException {
		return problemService.countAllProblemSources();
    }	
	
    @GET 
    @Path("sources/{problemSourceId}") 
    @Produces(MediaType.APPLICATION_JSON) 
    public ProblemSource getProblemSource(@PathParam("problemSourceId") long problemSourceId) throws NoSuchModelException, ApplicationException {
		return problemService.getProblemSourceByPrimary(problemSourceId);
    }
    
	@POST
	@Path("sources/provide")
	@Consumes(MediaType.APPLICATION_JSON)
	public void provideProblemSource(ProblemSource problemSource) throws ApplicationException {
		problemService.provideProblemSource(problemSource);
	}

	@DELETE
	@Path("sources/{problemSourceId}")
	public void deleteProbleSource(@PathParam("problemSourceId") long problemSourceId) throws NoSuchModelException, ApplicationException {
		problemService.deleteProblemSource(problemSourceId);
	}		
}
