
public interface TeacherInterface {
	
    int getTeacherID();
    
    void setTeacherID(int teacherID);
    
    String getTitle();
    
    void setTitle(String title);
    
    void displayTeacherInfo();
    
    void displayCourses();
    
    void checkFeedbacks();
    
    void checkStudentsInformation(Course course);
    
    void enterGrade(Course course);
    
    void changeGrade(Course course);
    
    void submitAttendance(Course course);
    
    void giveNotification(Course course);
}
