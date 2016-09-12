package org.gauntlet.problems.api.model;

import java.io.Serializable;

import org.gauntlet.core.model.BaseEntity;

public class ProblemDifficulty extends BaseEntity implements Serializable {
	public ProblemDifficulty() {
	}
	
	public ProblemDifficulty(String name, String code) {
		setName(name);
		setCode(code);
	}
}
