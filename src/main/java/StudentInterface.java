

import java.util.Date;

public interface StudentInterface {
	
    int getStudentID();
    
    void setStudentID(int studentID);

    float getGPA();
    
    void setGPA(float GPA);

    Teacher getAdvisor();
    
    void setAdvisor(Teacher advisor);

    int getEnrollmentYear();
    
    void setEnrollmentYear(int enrollmentYear);

    Grade[] getGrades();
    
    void setGrades(Grade[] grades);

    int getTotalCredit();
    
    void setTotalCredit(int totalCredit);

    Attendance[] getAttendance();
    
    void setAttendance(Attendance[] attendance);

    int generateStudentID();

    int totalCreditCalc(Course[] courses);

    void displayStudentInfo(Course course);

    void addGrade(Grade grade);

    void addAttendance(Attendance attendance);

    void displayCourses();

    void displayGrade(Course course);

    void checkNotification(Course course);


    void graduation();
}
