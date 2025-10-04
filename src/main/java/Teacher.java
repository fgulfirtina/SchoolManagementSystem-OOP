import java.io.*;
import java.util.Scanner;

public class Teacher extends User implements  TeacherInterface {
    private String title;

    public Teacher(int teacherID, String TCKN, String title, String name, String surname, String email, String password, Date birthDate, String phone, String address, Course[] courses) {
        super(teacherID, TCKN, name, surname, email, password, birthDate, phone, address, courses);
        this.title = title;
    }

    public int getTeacherID() {
        return getID();
    }

    public void setTeacherID(int teacherID) {
        setID(teacherID);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public static void addAssignment(Course course, Date dueDate) {
        Scanner sc = new Scanner(System.in);

        if (course != null) {
            System.out.print("Assignment name: ");
            String name = sc.nextLine();

            int maxPoint = 0;
            boolean validInput = false;
            while (!validInput) {
                System.out.print("Maximum points: ");
                try {
                    maxPoint = sc.nextInt();
                    sc.nextLine(); // Consume newline
                    if (maxPoint > 0 && maxPoint <= 100) {
                        validInput = true;
                    } else {
                        System.out.println("Maximum points should be in the following interval: (0, 100]");
                    }
                } catch (Exception e) {
                    System.out.println("Invalid input. Please enter a number.");
                    sc.nextLine(); // Consume invalid input
                }
            }

            System.out.print("Description: ");
            String description = sc.nextLine();

            Assignment assignment = new Assignment(name, course, dueDate, maxPoint, description);
            course.addAssignment(assignment);

            try (FileWriter writer = new FileWriter("homeworks.txt", true)) {
                writer.write("\n" + course.getCourseID() + "," + name + "," + dueDate.toString() + "," + maxPoint + "," + description);
            } catch (IOException e) {
                System.out.println("An error occurred while giving assignment.");
                e.printStackTrace();
            }

            System.out.println("Assignment added successfully.");
        } else {
            System.out.println("Course not found.");
        }
    }

    public void giveNotification(Course course) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Notification: ");
        String notification = sc.nextLine();

        File notFile = new File("notifications.txt");
        int notCount = 0;

        try (Scanner scanner = new Scanner(notFile)) {
            // Skip the header if it exists
            if (scanner.hasNextLine()) {
                scanner.nextLine();
            }

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                if (!line.trim().isEmpty()) {
                    String[] row = line.split(",");
                    try {
                        notCount = Integer.parseInt(row[0]);
                    } catch (NumberFormatException e) {
                        System.out.println("Error parsing notification count. Setting count to 0.");
                        notCount = 0;
                    }
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Notification file not found. Creating a new one.");
        }

        notCount++;
        try (FileWriter writer = new FileWriter("notifications.txt", true)) {
            writer.write("\n" + notCount + "," + title + " " + getName() + " " + getSurname() + "," + course.getCourseID() + "," + course.getCourseName() + "," + notification);
            System.out.println("Notification is given successfully.");
        } catch (IOException e) {
            System.out.println("An error occurred while giving notification.");
            e.printStackTrace();
        }
    }

    public void displayTeacherInfo() {
        System.out.println("Teacher ID : " + getTeacherID());
        System.out.println("Name       : " + getName());
        System.out.println("Surname    : " + getSurname());
        System.out.println("Email      : " + getEmail());
        System.out.println("Title      : " + getTitle());
        System.out.println("Courses    : " + getCourses()[0].getCourseID() + " , " + getCourses()[1].getCourseID());
        System.out.println();
    }

    public void displayCourses() {
        for (int i = 0; i < 2; i++) {
            getCourses()[i].displayCourse();
        }
    }

    public void checkFeedbacks() {
        File feedbacks = new File("feedbacks.txt");
        Scanner scanner;
        try {
            scanner = new Scanner(feedbacks);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return;
        }
        String[] row = new String[4];
        scanner.nextLine();
        while (scanner.hasNextLine()) {
            row = scanner.nextLine().split(",");
            if (getCourses()[0].getCourseID().equals(row[0]) || getCourses()[1].getCourseID().equals(row[0])) {
                System.out.println(row[0] + " : " + row[1]);
            }
        }
    }

    public void checkStudentsInformation(Course course) {

        Student[] stu = course.getStudent();
        for (int i = 0; i < course.getStudentCount(); i++) {
            System.out.println("*************************************************************************");
            stu[i].displayStudentInfo(course);
            System.out.println("*************************************************************************");
        }
    }

    public void enterGrade(Course course) {
        Scanner sc = new Scanner(System.in);
        Student[] stu = course.getStudent();
        int choice;
        int grade;

        // Teacher chooses grade
        System.out.println("(1) Midterm\n(2) Quiz\n(3) Lab\n(4) Homework\n(5) Final\n(6) Makeup");
        do {
            System.out.print("Which grade would you like to enter: ");
            choice = sc.nextInt();
            if (choice < 1 || choice > 6) System.out.println("Invalid choice!");
            if (choice == 3 && !course.isLab()) System.out.println("This course does not have a lab!");
        } while (choice < 1 || choice > 6 || (choice == 3 && !course.isLab()));

        // Teacher enters grade if student already does not have that grade
        for (int i = 0; i < course.getStudentCount(); i++) {
            int j;
            for (j = 0; j < stu[i].getGrades().length; j++) {
                if (stu[i].getGrades()[j].getCourseID().equals(course.getCourseID()))
                    break;
            }
            System.out.println("*************************************************************************");
            System.out.println("Student ID     : " + stu[i].getID());
            System.out.println("Student        : " + stu[i].getName() + " " + stu[i].getSurname());
            if (choice == 1 && stu[i].getGrades()[j].getMidterm() == -1) {
                do {
                    System.out.print("Midterm grade  : ");
                    grade = sc.nextInt();
                    if (grade < 0 || grade > 100) System.out.println("Invalid grade!");
                } while (grade < 0 || grade > 100);
                stu[i].getGrades()[j].setMidterm(grade);
            } else if (choice == 2 && stu[i].getGrades()[j].getQuiz() == -1) {
                do {
                    System.out.print("Quiz grade     : ");
                    grade = sc.nextInt();
                    if (grade < 0 || grade > 100) System.out.println("Invalid grade!");
                } while (grade < 0 || grade > 100);
                stu[i].getGrades()[j].setQuiz(grade);
            } else if (choice == 3 && stu[i].getGrades()[j].getLab() == -1 && course.isLab()) {
                do {
                    System.out.print("Lab grade      : ");
                    grade = sc.nextInt();
                    if (grade < 0 || grade > 100) System.out.println("Invalid grade!");
                } while (grade < 0 || grade > 100);
                stu[i].getGrades()[j].setLab(grade);
            } else if (choice == 4 && stu[i].getGrades()[j].getHomework() == -1) {
                do {
                    System.out.print("Homework grade : ");
                    grade = sc.nextInt();
                    if (grade < 0 || grade > 100) System.out.println("Invalid grade!");
                } while (grade < 0 || grade > 100);
                stu[i].getGrades()[j].setHomework(grade);
            } else if (choice == 5 && stu[i].getGrades()[j].getFinalExam() == -1) {
                do {
                    System.out.print("Final grade    : ");
                    grade = sc.nextInt();
                    if (grade < 0 || grade > 100) System.out.println("Invalid grade!");
                } while (grade < 0 || grade > 100);
                stu[i].getGrades()[j].setFinalExam(grade);
            } else if (choice == 6 && stu[i].getGrades()[j].getMakeup() == -1 && stu[i].getGrades()[j].getFinalExam() < 20) {
                do {
                    System.out.print("Makeup grade   : ");
                    grade = sc.nextInt();
                    if (grade < 0 || grade > 100) System.out.println("Invalid grade!");
                } while (grade < 0 || grade > 100);
                stu[i].getGrades()[j].setMakeup(grade);
            } else
                System.out.println("This student already has this grade.\nIf you would like to change it please select --> \"(3) Change grading\" ");
            System.out.println("*************************************************************************");

            // Now update the file with new grades
            try {
                File file = new File("grades.txt");
                Scanner fileScanner = new Scanner(file);
                StringBuilder fileContent = new StringBuilder();

                // Skip the header
                if (fileScanner.hasNextLine()) {
                    fileContent.append(fileScanner.nextLine()).append("\n");
                }

                while (fileScanner.hasNextLine()) {
                    String line = fileScanner.nextLine();
                    String[] parts = line.split(",");
                    int studentID = Integer.parseInt(parts[0]);
                    String courseID = parts[1];

                    if (stu[i].getID() == studentID && courseID.equals(course.getCourseID())) {
                        if (choice == 1 && stu[i].getGrades()[j].getMidterm() != -1) {
                            parts[2] = Integer.toString(stu[i].getGrades()[j].getMidterm());
                        } else if (choice == 2 && stu[i].getGrades()[j].getQuiz() != -1) {
                            parts[3] = Integer.toString(stu[i].getGrades()[j].getQuiz());
                        } else if (choice == 3 && stu[i].getGrades()[j].getLab() != -1) {
                            parts[4] = Integer.toString(stu[i].getGrades()[j].getLab());
                        } else if (choice == 4 && stu[i].getGrades()[j].getHomework() != -1) {
                            parts[5] = Integer.toString(stu[i].getGrades()[j].getHomework());
                        } else if (choice == 5 && stu[i].getGrades()[j].getFinalExam() != -1) {
                            parts[6] = Integer.toString(stu[i].getGrades()[j].getFinalExam());
                        } else if (choice == 6 && stu[i].getGrades()[j].getMakeup() != -1) {
                            parts[7] = Integer.toString(stu[i].getGrades()[j].getMakeup());
                        }
                    }
                    fileContent.append(String.join(",", parts)).append("\n");
                }

                fileScanner.close();

                // Write the updated content back to the file
                BufferedWriter writer = new BufferedWriter(new FileWriter("grades.txt"));
                writer.write(fileContent.toString());
                writer.close();
            } catch (IOException e) {
                System.out.println("An error occurred when entering grades.");
                e.printStackTrace();
            }
        }
        System.out.println("Grades have been entered successfully.");
    }

    public void changeGrade(Course course) {
        Scanner sc = new Scanner(System.in);
        Student[] stu = course.getStudent();
        int choice;
        int grade;

        // Teacher chooses grade
        System.out.println("(1) Midterm\n(2) Quiz\n(3) Lab\n(4) Homework\n(5) Final\n(6) Makeup");
        do {
            System.out.print("Which grade would you like to change: ");
            choice = sc.nextInt();
            if (choice < 1 || choice > 6) System.out.println("Invalid choice!");
            if (choice == 3 && !course.isLab()) System.out.println("This course does not have a lab!");
        } while (choice < 1 || choice > 6 || (choice == 3 && !course.isLab()));

        // Teacher changes grade if student already has that grade
        for (int i = 0; i < course.getStudentCount(); i++) {
            int j;
            for (j = 0; j < stu[i].getGrades().length; j++) {
                if (stu[i].getGrades()[j].getCourseID().equals(course.getCourseID()))
                    break;
            }
            System.out.println("*************************************************************************");
            System.out.println("Student ID     : " + stu[i].getID());
            System.out.println("Student        : " + stu[i].getName() + " " + stu[i].getSurname());
            if (choice == 1 && stu[i].getGrades()[j].getMidterm() != -1) {
                System.out.println("Current grade: " + stu[i].getGrades()[j].getMidterm());
                do {
                    System.out.print("Midterm grade  : ");
                    grade = sc.nextInt();
                    if (grade < 0 || grade > 100) System.out.println("Invalid grade!");
                } while (grade < 0 || grade > 100);
                stu[i].getGrades()[j].setMidterm(grade);
            } else if (choice == 2 && stu[i].getGrades()[j].getQuiz() != -1) {
                System.out.println("Current grade: " + stu[i].getGrades()[j].getQuiz());
                do {
                    System.out.print("Quiz grade     : ");
                    grade = sc.nextInt();
                    if (grade < 0 || grade > 100) System.out.println("Invalid grade!");
                } while (grade < 0 || grade > 100);
                stu[i].getGrades()[j].setQuiz(grade);
            } else if (choice == 3 && stu[i].getGrades()[j].getLab() != -1 && course.isLab()) {
                System.out.println("Current grade: " + stu[i].getGrades()[j].getLab());
                do {
                    System.out.print("Lab grade      : ");
                    grade = sc.nextInt();
                    if (grade < 0 || grade > 100) System.out.println("Invalid grade!");
                } while (grade < 0 || grade > 100);
                stu[i].getGrades()[j].setLab(grade);
            } else if (choice == 4 && stu[i].getGrades()[j].getHomework() != -1) {
                System.out.println("Current grade: " + stu[i].getGrades()[j].getHomework());
                do {
                    System.out.print("Homework grade : ");
                    grade = sc.nextInt();
                    if (grade < 0 || grade > 100) System.out.println("Invalid grade!");
                } while (grade < 0 || grade > 100);
                stu[i].getGrades()[j].setHomework(grade);
            } else if (choice == 5 && stu[i].getGrades()[j].getFinalExam() != -1) {
                System.out.println("Current grade: " + stu[i].getGrades()[j].getFinalExam());
                do {
                    System.out.print("Final grade    : ");
                    grade = sc.nextInt();
                    if (grade < 0 || grade > 100) System.out.println("Invalid grade!");
                } while (grade < 0 || grade > 100);
                stu[i].getGrades()[j].setFinalExam(grade);
            } else if (choice == 6 && stu[i].getGrades()[j].getMakeup() != -1 && stu[i].getGrades()[j].getFinalExam() < 20) {
                System.out.println("Current grade: " + stu[i].getGrades()[j].getMakeup());
                do {
                    System.out.print("Makeup grade   : ");
                    grade = sc.nextInt();
                    if (grade < 0 || grade > 100) System.out.println("Invalid grade!");
                } while (grade < 0 || grade > 100);
                stu[i].getGrades()[j].setMakeup(grade);
            } else
                System.out.println("This student does not have this grade.\nIf you would like to enter it please select --> \"(2) Enter grading\" ");
            System.out.println("*************************************************************************");

            // Now update the file with new grades
            try {
                File file = new File("grades.txt");
                Scanner fileScanner = new Scanner(file);
                StringBuilder fileContent = new StringBuilder();

                // Skip the header
                if (fileScanner.hasNextLine()) {
                    fileContent.append(fileScanner.nextLine()).append("\n");
                }

                while (fileScanner.hasNextLine()) {
                    String line = fileScanner.nextLine();
                    String[] parts = line.split(",");
                    int studentID = Integer.parseInt(parts[0]);
                    String courseID = parts[1];

                    if (stu[i].getID() == studentID && courseID.equals(course.getCourseID())) {
                        if (choice == 1 && stu[i].getGrades()[j].getMidterm() != -1) {
                            parts[2] = Integer.toString(stu[i].getGrades()[j].getMidterm());
                        } else if (choice == 2 && stu[i].getGrades()[j].getQuiz() != -1) {
                            parts[3] = Integer.toString(stu[i].getGrades()[j].getQuiz());
                        } else if (choice == 3 && stu[i].getGrades()[j].getLab() != -1) {
                            parts[4] = Integer.toString(stu[i].getGrades()[j].getLab());
                        } else if (choice == 4 && stu[i].getGrades()[j].getHomework() != -1) {
                            parts[5] = Integer.toString(stu[i].getGrades()[j].getHomework());
                        } else if (choice == 5 && stu[i].getGrades()[j].getFinalExam() != -1) {
                            parts[6] = Integer.toString(stu[i].getGrades()[j].getFinalExam());
                        } else if (choice == 6 && stu[i].getGrades()[j].getMakeup() != -1) {
                            parts[7] = Integer.toString(stu[i].getGrades()[j].getMakeup());
                        }
                    }
                    fileContent.append(String.join(",", parts)).append("\n");
                }

                fileScanner.close();

                // Write the updated content back to the file
                BufferedWriter writer = new BufferedWriter(new FileWriter("grades.txt"));
                writer.write(fileContent.toString());
                writer.close();
            } catch (IOException e) {
                System.out.println("An error occurred when entering grades.");
                e.printStackTrace();
            }
        }
        System.out.println("Grades have been changed successfully.");
    }

    public void submitAttendance(Course course) {
        Scanner sc = new Scanner(System.in);
        Student[] stu = course.getStudent();
        int choice;
        String attendance;

        // Teacher chooses grade
        do {
            System.out.print("Submit attendance of the week (1-14): ");
            choice = sc.nextInt();
            if (choice < 1 || choice > 14) System.out.println("Invalid choice!");
        } while (choice < 1 || choice > 14);

        // Teacher enters grade if student already does not have that grade
        for (int i = 0; i < course.getStudentCount(); i++) {
            int j;
            for (j = 0; j < stu[i].getAttendance().length; j++) {
                if (stu[i].getAttendance()[j].getCourseID().equals(course.getCourseID()))
                    break;
            }
            System.out.println("*************************************************************************");
            System.out.println("Student ID     : " + stu[i].getID());
            System.out.println("Student        : " + stu[i].getName() + " " + stu[i].getSurname());
            if(stu[i].getAttendance()[j].getPresence()[choice - 2].equals("0")) {
                System.out.println("Attendance of the previous week is not submitted for this student!");
                continue;
            }
            System.out.print("Attendance(+/-):");

            if(stu[i].getAttendance()[j].getPresence()[choice - 1].equals("0")) {
                do {
                    attendance = sc.next();
                    if(!attendance.equals("+") && !attendance.equals("-"))
                        System.out.println("Invalid choice!");
                } while (!attendance.equals("+") && !attendance.equals("-"));

                stu[i].getAttendance()[j].setPresenceAt(choice - 1,attendance);
            }
            else {
                String c;
                System.out.println(stu[i].getAttendance()[j].getPresence()[choice - 1]);
                System.out.println("This weeks attendance for this student already exists.\nWould you like to change it? (y/n)");
                do {
                    c = sc.next();
                    if(!c.equalsIgnoreCase("y") && !c.equalsIgnoreCase("n")) System.out.println("Invalid choice!");
                } while (!c.equalsIgnoreCase("y") && !c.equalsIgnoreCase("n"));

                if(c.equalsIgnoreCase("y")) {
                    do {
                        System.out.print("Attendance(+/-):");
                        attendance = sc.next();
                        if(!attendance.equals("+") && !attendance.equals("-"))
                            System.out.println("Invalid choice!");
                    } while (!attendance.equals("+") && !attendance.equals("-"));

                    stu[i].getAttendance()[j].setPresenceAt(choice - 1,attendance);
                }
                else continue;
            }

            // Now update the file with new grades
            try {
                File file = new File("attendance.txt");
                Scanner fileScanner = new Scanner(file);
                StringBuilder fileContent = new StringBuilder();

                // Skip the header
                if (fileScanner.hasNextLine()) {
                    fileContent.append(fileScanner.nextLine()).append("\n");
                }

                while (fileScanner.hasNextLine()) {
                    String line = fileScanner.nextLine();
                    String[] parts = line.split(",");
                    int studentID = Integer.parseInt(parts[0]);
                    String courseID = parts[1];

                    if (stu[i].getID() == studentID && courseID.equals(course.getCourseID())) {
                        if (!stu[i].getAttendance()[j].getPresence()[choice - 1].equals("0")) {
                            parts[choice + 1] = stu[i].getAttendance()[j].getPresence()[choice - 1];
                        }
                    }
                    fileContent.append(String.join(",", parts)).append("\n");
                }

                fileScanner.close();

                // Write the updated content back to the file
                BufferedWriter writer = new BufferedWriter(new FileWriter("attendance.txt"));
                writer.write(fileContent.toString());
                writer.close();
            } catch (IOException e) {
                System.out.println("An error occurred when submitting attendance.");
                e.printStackTrace();
            }
            System.out.println("Attendances have been entered successfully.");
        }
    }

}