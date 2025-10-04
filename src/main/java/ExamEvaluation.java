public class ExamEvaluation {
	private double midtermWeight;
	private double finalWeight;
	private double quizWeight;
	private double homeworkWeight;
	private double labWeight;
	private double makeupWeight;
	private String courseCode;

	public ExamEvaluation(double midtermWeight, double finalWeight, double quizWeight, double homeworkWeight,
						  double labWeight, double makeupWeight, String courseCode) {
		super();
		this.midtermWeight = midtermWeight;
		this.finalWeight = finalWeight;
		this.quizWeight = quizWeight;
		this.homeworkWeight = homeworkWeight;
		this.labWeight = labWeight;
		this.makeupWeight = makeupWeight;
		this.courseCode = courseCode; // Initialize the new field
	}

	// Getter and setter for courseCode
	public String getCourseCode() {
		return courseCode;
	}

	public void setCourseCode(String courseCode) {
		this.courseCode = courseCode;
	}

	// Other getters and setters remain unchanged
	public double getMidtermWeight() {
		return midtermWeight;
	}

	public void setMidtermWeight(double midtermWeight) {
		this.midtermWeight = midtermWeight;
	}

	public double getFinalWeight() {
		return finalWeight;
	}

	public void setFinalWeight(double finalWeight) {
		this.finalWeight = finalWeight;
	}

	public double getQuizWeight() {
		return quizWeight;
	}

	public void setQuizWeight(double quizWeight) {
		this.quizWeight = quizWeight;
	}

	public double getHomeworkWeight() {
		return homeworkWeight;
	}

	public void setHomeworkWeight(double homeworkWeight) {
		this.homeworkWeight = homeworkWeight;
	}

	public double getLabWeight() {
		return labWeight;
	}

	public void setLabWeight(double labWeight) {
		this.labWeight = labWeight;
	}

	public double getMakeupWeight() {
		return makeupWeight;
	}

	public void setMakeupWeight(double makeupWeight) {
		this.makeupWeight = makeupWeight;
	}
}
