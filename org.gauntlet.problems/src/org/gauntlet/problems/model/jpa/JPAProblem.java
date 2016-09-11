package org.gauntlet.problems.model.jpa;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.gauntlet.core.model.Constants;
import org.gauntlet.core.model.JPABaseEntity;
import org.gauntlet.problems.api.model.ProblemCategory;
import org.gauntlet.problems.api.model.ProblemDifficulty;
import org.gauntlet.problems.api.model.ProblemSource;

@Entity
@Inheritance(strategy=InheritanceType.TABLE_PER_CLASS)
@Table(name=Constants.CNX_TABLE_NAME_PREFIX+Constants.GNT_TABLE_NAME_SEPARATOR
	+"problem")
public class JPAProblem extends JPABaseEntity implements Serializable {
	private String answer;
	
	@ManyToOne(targetEntity = ProblemSource.class)
	@JoinColumn
	private ProblemSource source;
	
	@ManyToOne(targetEntity = ProblemCategory.class)
	@JoinColumn
	private ProblemCategory category;
	
	@ManyToOne(targetEntity = ProblemDifficulty.class)
	@JoinColumn	
	private ProblemDifficulty difficulty;	
	
	@Lob
	@Basic(fetch=FetchType.LAZY) // this gets ignored anyway, but it is recommended for blobs
	private byte[] answerPicture;

	@Lob
	@Basic(fetch=FetchType.LAZY) // this gets ignored anyway, but it is recommended for blobs
	private byte[] questionPicture;	
	
	private Integer sourcePageNumber;
	
	private Integer sourceIndexWithinPage;

	private boolean multipleChoice = true;
	
	private boolean requiresCalculator = false;

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	public ProblemSource getSource() {
		return source;
	}

	public void setSource(ProblemSource source) {
		this.source = source;
	}

	public ProblemCategory getCategory() {
		return category;
	}

	public void setCategory(ProblemCategory category) {
		this.category = category;
	}

	public ProblemDifficulty getDifficulty() {
		return difficulty;
	}

	public void setDifficulty(ProblemDifficulty difficulty) {
		this.difficulty = difficulty;
	}

	public byte[] getAnswerPicture() {
		return answerPicture;
	}

	public void setAnswerPicture(byte[] answerPicture) {
		this.answerPicture = answerPicture;
	}

	public byte[] getQuestionPicture() {
		return questionPicture;
	}

	public void setQuestionPicture(byte[] questionPicture) {
		this.questionPicture = questionPicture;
	}

	public Integer getSourcePageNumber() {
		return sourcePageNumber;
	}

	public void setSourcePageNumber(Integer sourcePageNumber) {
		this.sourcePageNumber = sourcePageNumber;
	}

	public Integer getSourceIndexWithinPage() {
		return sourceIndexWithinPage;
	}

	public void setSourceIndexWithinPage(Integer sourceIndexWithinPage) {
		this.sourceIndexWithinPage = sourceIndexWithinPage;
	}

	public boolean isMultipleChoice() {
		return multipleChoice;
	}

	public void setMultipleChoice(boolean multipleChoice) {
		this.multipleChoice = multipleChoice;
	}

	public boolean isRequiresCalculator() {
		return requiresCalculator;
	}

	public void setRequiresCalculator(boolean requiresCalculator) {
		this.requiresCalculator = requiresCalculator;
	} 
}
