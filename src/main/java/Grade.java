public class Grade {
	private String courseID;
	private int midterm;
	private int quiz;
	private int lab;
	private int homework;
	private int finalExam;
	private int makeup;
	private String letterGrade;
	
	public Grade(String courseID, int midterm, int quiz, int lab, int homework,int finalExam, int makeup) {
		super();
		this.courseID = courseID;
		this.midterm = midterm;
		this.finalExam = finalExam;
		this.quiz = quiz;
		this.lab = lab;
		this.homework = homework;
		this.makeup = makeup;
	}
	
	public int getMidterm() {
		return midterm;
	}
	
	public void setMidterm(int midterm) {
		this.midterm = midterm;
	}
	
	public int getFinalExam() {
		return finalExam;
	}
	
	public void setFinalExam(int finalExam) {
		this.finalExam = finalExam;
	}
	
	public int getQuiz() {
		return quiz;
	}
	
	public void setQuiz(int quiz) {
		this.quiz = quiz;
	}
	
	public int getLab() {
		return lab;
	}
	
	public void setLab(int lab) {
		this.lab = lab;
	}
	
	public int getHomework() {
		return homework;
	}
	
	public void setHomework(int homework) {
		this.homework = homework;
	}
	
	public int getMakeup() {
		return makeup;
	}
	
	public void setMakeup(int makeup) {
		this.makeup = makeup;
	}

	public String getCourseID() {
		return courseID;
	}

	public void setCourseID(String courseID) {
		this.courseID = courseID;
	}

	public String getLetterGrade() {
		return letterGrade;
	}

	public void setLetterGrade(String letterGrade) {
		this.letterGrade = letterGrade;
	}
}
