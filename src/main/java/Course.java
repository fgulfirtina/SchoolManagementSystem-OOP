public class Course implements CourseInterface{
    private String courseID;
    private String courseName;
    private int credit;
    private Book[] books;
    private Teacher[] teachers;
    private String preRequisites;
    private Teacher coordinator;
    private boolean lab;
    private boolean isElective;
    private Student[] student;
    private ExamEvaluation examEvaluation;
    private Assignment[] assignments;
    private int assignmentCount;
    private int studentCount;

    public Course(String courseID, String courseName, int credit, Book[] books,
                  String preRequisites, boolean lab, boolean isElective,
                  ExamEvaluation examEvaluation)  {
        this.courseID = courseID; //
        this.courseName = courseName; //
        this.credit = credit;//
        this.books = books; //
        this.preRequisites = preRequisites; //
        this.lab = lab; //
        this.isElective = isElective; //
        this.examEvaluation = examEvaluation; //
        this.assignments = new Assignment[10]; // Initialize with an initial size
        this.assignmentCount = 0;
        studentCount = 0;
    }

    public String getCourseID() {
        return courseID;
    }

    public void setCourseID(String courseID) {
        this.courseID = courseID;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public int getCredit() {
        return credit;
    }

    public void setCredit(int credit) {
        this.credit = credit;
    }

    public Book[] getBooks() {
        return books;
    }

    public void setBooks(Book[] books) {
        this.books = books;
    }

    public Teacher[] getTeachers() {
        return teachers;
    }

    public void setTeachers(Teacher[] teachers) {
        this.teachers = teachers;
    }

    public String getPreRequisites() {
        return preRequisites;
    }

    public void setPreRequisites(String preRequisites) {
        this.preRequisites = preRequisites;
    }

    public Teacher getCoordinator() {
        return coordinator;
    }

    public void setCoordinator(Teacher coordinator) {
        this.coordinator = coordinator;
    }

    public boolean isLab() {
        return lab;
    }

    public void setLab(boolean lab) {
        this.lab = lab;
    }

    public boolean isElective() {
        return isElective;
    }

    public void setElective(boolean isElective) {
        this.isElective = isElective;
    }

    public Student[] getStudent() {
        return student;
    }

    public void setStudent(Student[] student) {
        this.student = student;
    }

    public ExamEvaluation getExamEvaluation() {
        return examEvaluation;
    }

    public void setExamEvaluation(ExamEvaluation examEvaluation) {
        this.examEvaluation = examEvaluation;
    }


    public int getStudentCount() {
        return studentCount;
    }

    public void setStudentCount(int studentCount) {
        this.studentCount = studentCount;
    }

    public void calculateLetterGrades(boolean curve) {
        if (curve) {  // if there is a bell curve
            double mean = 0.0;
            double std_dev = 0.0;
            double sumOfSquares = 0.0;
            double z;

            for (int i= 0; i<studentCount; i++) {
                mean += student[i].calculateAverage(getCourseID());
            }
            mean /= student.length;


            for (Student students : student) {
                sumOfSquares += Math.pow(students.calculateAverage(getCourseID()) - mean, 2);
            }
            std_dev = sumOfSquares / student.length;



            for (Student students : student) {  // setting letter grades
                for (Grade grades : students.getGrades()) {
                    if (grades.getCourseID().equals(courseID)) {
                        z = (students.calculateAverage(courseID) - mean) / std_dev;
                        if (z >= 1.0) {
                            grades.setLetterGrade("AA");
                        } else if (z >= 0.5) {
                            grades.setLetterGrade("BA");
                        } else if (z >= 0.0) {
                            grades.setLetterGrade("BB");
                        } else if (z >= -0.5) {
                            grades.setLetterGrade("CB");
                        } else if (z >= -1.0) {
                            grades.setLetterGrade("CC");
                        } else if (z >= -1.5) {
                            grades.setLetterGrade("DC");
                        } else if (z >= -2.0) {
                            grades.setLetterGrade("DD");
                        } else if (z >= -2.5) {
                            grades.setLetterGrade("FD");
                        } else {
                            grades.setLetterGrade("FF");
                        }
                    }
                }
            }
        } else {  // if there is no bell curve
            for (Student students : student) {
                double average = students.calculateAverage(courseID);
                for (Grade grade : students.getGrades()) {
                    if (grade.getCourseID().equals(courseID)) {
                        if (average >= 90) {
                            grade.setLetterGrade("AA");
                        } else if (average >= 85) {
                            grade.setLetterGrade("BA");
                        } else if (average >= 80) {
                            grade.setLetterGrade("BB");
                        } else if (average >= 75) {
                            grade.setLetterGrade("CB");
                        } else if (average >= 70) {
                            grade.setLetterGrade("CC");
                        } else if (average >= 65) {
                            grade.setLetterGrade("DC");
                        } else if (average >= 50) {
                            grade.setLetterGrade("FD");
                        } else {
                            grade.setLetterGrade("FF");
                        }
                    }
                }
            }
        }
    }

    public void displayCourse() {
        System.out.println("Course ID : " + courseID + "   Course Name : " + courseName);
    }

    public void addTeacher(Teacher teacher) {
        // If the teachers array is null or empty, create a new array
        if (teachers == null) {
            teachers = new Teacher[1];
            teachers[0] = teacher;
            coordinator = teacher;
        } else {
            // If the teachers array is not empty, create a new array and copy existing teachers
            Teacher[] newTeachers = new Teacher[teachers.length + 1];
            System.arraycopy(teachers, 0, newTeachers, 0, teachers.length);
            // Add the new teacher at the end
            newTeachers[teachers.length] = teacher;
            // Update the teachers array with the new array
            teachers = newTeachers;
        }
    }

    public void addStudent(Student student) {
        // If the student array is null or empty, create a new array with the new student
        if (this.student == null) {
            this.student = new Student[1];
            this.student[0] = student;
            studentCount++;
        } else {
            // If the student array is not empty, create a new array with an additional slot for the new student
            Student[] newStudents = new Student[this.student.length + 1];
            System.arraycopy(this.student, 0, newStudents, 0, this.student.length);
            // Add the new student at the end of the new array
            newStudents[this.student.length] = student;
            // Update the student array with the new array
            this.student = newStudents;
            studentCount++;
        }
    }

    public void addAssignment(Assignment assignment) {
        assignments[assignmentCount] = assignment;
        assignmentCount++;
    }

}
