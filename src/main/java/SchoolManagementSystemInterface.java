
public interface SchoolManagementSystemInterface {
	void Menu();

	boolean checkLogin(String mail, String password);

	void handleSignInRequests();

	void checkGrade(int t);

	void attendLecture(int t);

	void checkNotifications(int t);

	void demandMeeting(int t);

	void submitHomework(int t);

	void writeToForum(int t);

	void displayForum(int t);

	void checkSyllabus(int t);

	void checkSyllabusStudent(int t);

	void submitAttendance(int t);

	void enterGrade(int t);

	void changeGrading(int t);

	void checkStudentInfo(int t);

	void publishLetterGrades(int t);

	void giveLecture(int t);

	void giveAssignment(int t);

	void giveNotification(int t);

	void checkFeedBacks(int t);

	void objectionToExam(int t);

	void checkObjections(int t);
}
