package org.gauntlet.problems.rest;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.gauntlet.core.api.ApplicationException;
import org.gauntlet.core.api.dao.NoSuchModelException;
import org.gauntlet.problems.api.dao.IProblemDAOService;
import org.gauntlet.problems.api.model.ProblemPicture;
import org.gauntlet.problems.api.model.ProblemSource;

@Path("sources")
public class SourcesResource {
	private volatile IProblemDAOService problemService;

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("sources")
	public List<ProblemSource> allSources(@QueryParam("start") int start, @QueryParam("end") int end)
			throws ApplicationException {
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
	public ProblemSource getProblemSource(@PathParam("problemSourceId") long problemSourceId)
			throws NoSuchModelException, ApplicationException {
		return problemService.getProblemSourceByPrimary(problemSourceId);
	}

	@PUT
	@Path("sources")
	@Consumes(MediaType.APPLICATION_JSON)
	public void provideProblemSource(ProblemSource problemSource) throws ApplicationException {
		problemService.provideProblemSource(problemSource);
	}

	@DELETE
	@Path("sources/{problemSourceId}")
	public void deleteProbleSource(@PathParam("problemSourceId") long problemSourceId)
			throws NoSuchModelException, ApplicationException {
		problemService.deleteProblemSource(problemSourceId);
	}

	@GET
	@Path("pictures/{pictureId}")
	public Response getImage(@PathParam("pictureId") String pictureId) throws ApplicationException {
		ProblemPicture pp;
		try {
			pp = problemService.getProblemPictureByPrimary(Long.valueOf(pictureId));
			InputStream pictureStream = new ByteArrayInputStream(pp.getPicture());
			MediaType mediaType = new MediaType("image", "png");

			return Response.ok().type(mediaType).entity(pictureStream).build();
		} catch (NumberFormatException | ApplicationException | NoSuchModelException e) {
			throw new ApplicationException(e);
		}
	}
}
