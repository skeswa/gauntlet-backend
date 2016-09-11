package org.gauntlet.problems.api.model;

import java.io.Serializable;

import org.gauntlet.core.model.BaseEntity;

public class Problem extends BaseEntity implements Serializable {
	private String answer;
	
	private ProblemSource source;
	
	private ProblemCategory category;
	
	private Integer sourcePageNumber;
	
	private Integer sourceIndexWithinPage;

	private ProblemDifficulty difficulty;

	private byte[] answerPicture;
	
	private byte[] questionPicture;
	
	private boolean multipleChoice = true;
	
	private boolean requiresCalculator = false; 
}
