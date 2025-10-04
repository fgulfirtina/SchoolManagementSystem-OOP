
public interface CourseInterface {

    String getCourseID();

    void setCourseID(String courseID);

    String getCourseName();

    void setCourseName(String courseName);

    int getCredit();

    void setCredit(int credit);

    Book[] getBooks();

    void setBooks(Book[] books);

    Teacher[] getTeachers();

    void setTeachers(Teacher[] teachers);

    String getPreRequisites();

    void setPreRequisites(String preRequisites);

    Teacher getCoordinator();

    void setCoordinator(Teacher coordinator);

    boolean isLab();

    void setLab(boolean lab);

    boolean isElective();

    void setElective(boolean isElective);

    Student[] getStudent();

    void setStudent(Student[] student);

    ExamEvaluation getExamEvaluation();

    void setExamEvaluation(ExamEvaluation examEvaluation);

    int getStudentCount();

    void setStudentCount(int studentCount);

    void calculateLetterGrades(boolean curve);

    void displayCourse();

    void addTeacher(Teacher teacher);

    void addStudent(Student student);

    void addAssignment(Assignment assignment);

}
