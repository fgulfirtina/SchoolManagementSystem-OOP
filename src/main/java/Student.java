import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.util.Scanner;
import java.util.Iterator;
import java.util.List;
import java.util.Arrays;

public class Student extends User implements StudentInterface {
    private int studentID;
    private float GPA;
    private Teacher advisor;
    private int enrollmentYear;
    private Grade[] grades;
    private int totalCredit;
    private Attendance[] attendance;
    private boolean hardwareTraining;
    private boolean softwareTraining;


    // For existing students
    public Student(int studentID, String TCKN, String name, String surname,
                   String email, Teacher advisor, String password,
                   Date birthDate, String phone, String address, Course[] courses,
                   float GPA, int enrollmentYear, Grade[] grades,
                   int totalCredit) {
        super(studentID, TCKN, name, surname, email, password, birthDate, phone, address, courses);
        this.studentID = studentID;
        this.GPA = GPA;
        this.grades = grades;
        this.totalCredit = totalCredit;
        this.enrollmentYear = enrollmentYear;
        this.advisor = advisor;
    }

    // For new students
    public Student(String TCKN, String name, String surname, String email, String password, Date birthDate, String phone, String address, int enrollmentYear) {
        super(TCKN, name, surname, email, password, birthDate, phone, address);
        this.enrollmentYear = enrollmentYear;
        studentID = generateStudentID();
    }

    public int getStudentID() {
        return studentID;
    }

    public void setStudentID(int studentID) {
        this.studentID = studentID;
    }

    public float getGPA() {
        return GPA;
    }

    public void setGPA(float gPA) {
        GPA = gPA;
    }

    public Teacher getAdvisor() {
        return advisor;
    }

    public void setAdvisor(Teacher advisor) {
        this.advisor = advisor;
    }

    public int getEnrollmentYear() {
        return enrollmentYear;
    }

    public void setEnrollmentYear(int enrollmentYear) {
        this.enrollmentYear = enrollmentYear;
    }

    public Grade[] getGrades() {
        return grades;
    }

    public void setGrades(Grade[] grades) {
        this.grades = grades;
    }

    public int getTotalCredit() {
        return totalCredit;
    }

    public void setTotalCredit(int totalCredit) {
        this.totalCredit = totalCredit;
    }

    public int generateStudentID() {
        return 0;
    }

    public int totalCreditCalc(Course[] courses) {
        return 0;
    }

    // ITERATOR PATTERN DESIGN - Iterator that visits every elements in list
    public void displayStudentInfo(Course course) {
        System.out.println("Student ID    : " + getStudentID());
        System.out.println("Name          : " + getName());
        System.out.println("Surname       : " + getSurname());
        System.out.println("Email         : " + getEmail());
        System.out.println("Advisor       : " + (advisor != null ? advisor.getName() + " " + advisor.getSurname() : "None"));
        System.out.println("Grades        : ");

        Course[] courses = getCourses();
        if (courses != null) {
            // ITERATOR PATTERN DESIGN - Convery Array into List in order to implement Iterator
            List<Course> courseList = Arrays.asList(courses);
            // ITERATOR PATTERN DESIGN - Initializing Iterator
            Iterator<Course> iterator = courseList.iterator();
            // ITERATOR PATTERN DESIGN - Visiting elements with Iterator
            while (iterator.hasNext()) {
                // ITERATOR PATTERN DESIGN - Taking elements with Iterator
                Course currentCourse = iterator.next();
                if (currentCourse.getCourseID().equals(course.getCourseID())) {
                    displayGrade(currentCourse);
                }
            }
        } else {
            System.out.println("No courses assigned.");
        }
    }

    public void addGrade(Grade grade) {
        // If the grades array is null or empty, create a new array
        if (grades == null) {
            grades = new Grade[1];
            grades[0] = grade;
        } else {
            // If the grades array is not empty, create a new array and copy existing grades
            Grade[] newGrades = new Grade[grades.length + 1];
            System.arraycopy(grades, 0, newGrades, 0, grades.length);
            // Add the new grade at the end
            newGrades[grades.length] = grade;
            // Update the grades array with the new array
            grades = newGrades;
        }
    }

    public void addAttendance(Attendance a) {
        // If the grades array is null or empty, create a new array
        if (attendance == null) {
            attendance = new Attendance[1];
            attendance[0] = a;
        } else {
            // If the grades array is not empty, create a new array and copy existing grades
            Attendance[] newAttendance = new Attendance[attendance.length + 1];
            System.arraycopy(attendance, 0, newAttendance, 0, attendance.length);
            // Add the new grade at the end
            newAttendance[attendance.length] = a;
            // Update the grades array with the new array
            attendance = newAttendance;
        }
    }

    public void displayCourses() {
        for (int i = 0; i < 4; i++) {
            getCourses()[i].displayCourse();
        }
    }

    public void displayGrade(Course c) {
        int i;
        for (i = 0; i < grades.length; i++) {
            if (c.getCourseID().equals(grades[i].getCourseID()))
                break;
        }
        System.out.println("-------------------------------------------------------------------------");
        System.out.println("Course    : " + c.getCourseID() + " " + c.getCourseName());
        if (grades[i].getMidterm() != -1) System.out.println("Midterm   : " + grades[i].getMidterm());
        if (grades[i].getQuiz() != -1) System.out.println("Quiz      : " + grades[i].getQuiz());
        if (grades[i].getLab() != -1 && c.isLab()) System.out.println("Lab       : " + grades[i].getLab());
        if (grades[i].getHomework() != -1) System.out.println("Homework  : " + grades[i].getHomework());
        if (grades[i].getFinalExam() != -1) System.out.println("Final     : " + grades[i].getFinalExam());
        if (grades[i].getMakeup() != -1) System.out.println("Makeup    : " + grades[i].getMakeup());
        System.out.println("-------------------------------------------------------------------------");
    }

    public Attendance[] getAttendance() {
        return attendance;
    }

    public void setAttendance(Attendance[] attendance) {
        this.attendance = attendance;
    }

    public void checkNotification(Course course) {
        File notFile = new File("notifications.txt");
        Scanner scanner;
        try {
            scanner = new Scanner(notFile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return;
        }
        String[] row = new String[5];
        scanner.nextLine();
        while (scanner.hasNextLine()) {
            row = scanner.nextLine().split(",");
            if (course.getCourseID().equals(row[2])) {
                System.out.println("-------------------------------------------------------------------------");
                System.out.println("Teacher: " + row[1]);
                System.out.println("Subject: " + row[2] + " - " + row[3]);
                System.out.println("Content: " + row[4]);
            }
        }
        scanner.close();
    }


    public void demandMeeting(Course course, Date meetingDate) {
        // Detect last row id
        Scanner meetingScan = new Scanner(System.in);
        int rowID = 1;
        File stuReqFile = new File("meetings.txt");
        Scanner scanner;
        try {
            scanner = new Scanner(stuReqFile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return;
        }
        scanner.nextLine();
        while (scanner.hasNextLine()) {
            rowID++;
            scanner.nextLine();
        }

        // Append request end of the file
        try {
            FileWriter fw = new FileWriter("meetings.txt", true);
            fw.write("\n" + rowID + "," + getEmail() + "," + course.getCourseID() + "," + meetingDate + "\n"); // Appends the string to the file
            fw.close();
        } catch (Exception o) // If there is an exception it writes to the screen.
        {
            System.out.println("An error occurred during opening the file.");
            o.printStackTrace();
        }
        System.out.println("\nMeeting has been scheduled!");
        scanner.close();
    }

    public void writeToForum(Course course) {
        // Detect last row id
        Scanner stuScan = new Scanner(System.in);
        int rowID = 1;
        File stuReqFile = new File("forum.txt");
        Scanner scanner;
        try {
            scanner = new Scanner(stuReqFile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return;
        }
        scanner.nextLine();
        while (scanner.hasNextLine()) {
            rowID++;
            scanner.nextLine();
        }
        scanner.close();
        // Append request end of the file
        System.out.println("\nPlease enter message content: ");
        String requestContent = stuScan.nextLine();
        try {
            FileWriter fw = new FileWriter("forum.txt", true);
            fw.write("\n" + rowID + "," + course.getCourseID() + "," + course.getCourseName() + "," + getEmail() + "," + getName() + " " + getSurname() + "," + requestContent); // Appends the string to the file
            fw.close();
        } catch (Exception o) // If there is an exception it writes to the screen.
        {
            System.out.println("An error occurred during opening the file.");
            o.printStackTrace();
        }
        System.out.println("\nYour message has been send to forum!");
    }

    public void displayForum(Course course) {
        File requestFile = new File("forum.txt");
        Scanner scanner;
        try {
            scanner = new Scanner(requestFile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return;
        }
        String[] row = new String[3];
        scanner.nextLine();
        while (scanner.hasNextLine()) {
            row = scanner.nextLine().split(",");
            for(int i=0; i<4; i++) {
                if(getCourses()[i].getCourseID().equals(course.getCourseID())) {
                    System.out.println("***----------------------------*** " + row[0] + " ***----------------------------***");
                    System.out.println("Course: " + row[1] + " - " + row[2]);
                    System.out.println("Student e-mail: " + row[3]);
                    System.out.println("Student : " + row[4]);
                    System.out.println("Message : " + row[5]);
                }
            }
        }
    }

    public double calculateAverage(String courseID) {
        int i,j;
        for (i = 0; i < grades.length; i++) {
            if (courseID.equals(grades[i].getCourseID()))
                break;
        }
        for (j = 0; j < getCourses().length; j++) {
            if (courseID.equals(getCourses()[j].getCourseID()))
                break;
        }

        double avg = 0.0;
        if (grades[i].getMidterm() != -1) avg += grades[i].getMidterm() * getCourses()[j].getExamEvaluation().getMidtermWeight();
        if (grades[i].getQuiz() != -1) avg += grades[i].getQuiz() * getCourses()[j].getExamEvaluation().getQuizWeight();
        if (grades[i].getLab() != -1 && getCourses()[j].isLab()) avg += grades[i].getLab() * getCourses()[j].getExamEvaluation().getLabWeight();
        if (grades[i].getHomework() != -1) avg += grades[i].getHomework() * getCourses()[j].getExamEvaluation().getHomeworkWeight();
        if (grades[i].getFinalExam() != -1) avg += grades[i].getFinalExam() * getCourses()[j].getExamEvaluation().getFinalWeight();
        if (grades[i].getMakeup() != -1) avg += grades[i].getMakeup() * getCourses()[j].getExamEvaluation().getMakeupWeight();

        return avg;
    }

    public void graduation() {
        if (GPA < 1.8) {
            System.out.println("Repeate grade!");
        } else {
            if (totalCredit > 240) {
                if (GPA <= 2.0) {
                    System.out.println("Retake conditionally passed and unpassed courses!");
                } else if (GPA >= 2.0) {
                    if (!hardwareTraining) {
                        System.out.println("Undertake hardware training!");
                    } else {
                        if (!softwareTraining) {
                            System.out.println("Undertake software training!");
                        } else {
                            System.out.println("You are graduated!");
                        }
                    }
                }
            }
	}
}

    public void isPassed(Course c) {
        int i;
        for (i = 0; i < grades.length; i++) {
            if (c.getCourseID().equals(grades[i].getCourseID()))
                break;
        }
        System.out.println("-------------------------------------------------------------------------");
        System.out.println("Course    : " + c.getCourseID() + " " + c.getCourseName());
        System.out.println("Grade     : " + grades[i].getLetterGrade());
        if(grades[i].getLetterGrade().compareTo("CC") <= 0) System.out.println("Passed.");
        else if(grades[i].getLetterGrade().equals("DC") || grades[i].getLetterGrade().equals("DD"))
            System.out.println("Conditional passed.");
        else {
            System.out.println("Failed.");
        }
        System.out.println("-------------------------------------------------------------------------");
    }
}
