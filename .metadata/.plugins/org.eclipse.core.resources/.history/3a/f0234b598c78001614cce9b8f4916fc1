package org.gauntlet.problems.defaults;

import javax.ws.rs.Path;
import org.gauntlet.core.api.ApplicationException;
import org.gauntlet.problems.api.dao.IProblemDAOService;
import org.gauntlet.problems.api.model.ProblemCategory;
import org.osgi.service.log.LogService;


@Path("problems")
public class Controller {
	private volatile LogService logger;
	private volatile IProblemDAOService problemService;
	
	private void start() throws ApplicationException {
		ProblemCategory pc = new ProblemCategory("test","test");
		problemService.provideProblemCategory(pc);
	}
}
