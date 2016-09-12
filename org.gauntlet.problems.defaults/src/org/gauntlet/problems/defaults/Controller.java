package org.gauntlet.problems.defaults;

import javax.ws.rs.Path;
import org.gauntlet.core.api.ApplicationException;
import org.gauntlet.problems.api.dao.IProblemDAOService;
import org.gauntlet.problems.api.model.ProblemCategory;
import org.gauntlet.problems.api.model.ProblemDifficulty;
import org.gauntlet.problems.api.model.ProblemSource;
import org.osgi.service.log.LogService;


@Path("problems")
public class Controller {
	private volatile LogService logger;
	private volatile IProblemDAOService problemService;
	
	private void start() throws ApplicationException {
		ProblemSource ps = new ProblemSource("MW4NSAT","MW4NSAT");
		problemService.provideProblemSource(ps);
		
		ProblemCategory pc = new ProblemCategory("Heart of Algebra/Linear Equations","Heart of Algebra/Linear Equations");
		problemService.provideProblemCategory(pc);
		
		ProblemDifficulty pd = new ProblemDifficulty("Easy","Easy");
		problemService.provideProblemDifficulty(pd);		
	}
}
