import java.io.File;
import java.io.*;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.util.Random;

public class SchoolManagementSystem  implements SchoolManagementSystemInterface {
    private Teacher[] teachers;
    private Student[] students;
    private Course[] courses;
    private User[] requests;
    private Book[] books;

    private ExamEvaluation[] examEvaluation;
    private User admin;
    private static int teacherCount;
    private static int studentCount;
    private static int courseCount;
    private static int requestCount;
    private static int bookCount;
    private static int examEvaluationCount;

    private static final String ANSI_RED = "\u001B[31m";
    private static final String ANSI_RESET = "\u001B[0m";
    private static final String ANSI_BOLD = "\u001B[1m";

    // SINGLETON PATTERN DESIGN - Private static area that holds only one instance
    private static SchoolManagementSystem instance;

    // SINGLETON PATTERN DESIGN - Private constructor
    SchoolManagementSystem() {
        teacherCount = 0;
        studentCount = 0;
        courseCount = 0;
        requestCount = 0;
        bookCount = 0;
        examEvaluationCount = 0;

        teachers = new Teacher[20];
        students = new Student[100];
        courses = new Course[50];
        requests = new User[50];
        books = new Book[50];
        examEvaluation = new ExamEvaluation[50];
        admin = new User("admin@deu.edu.tr", "admin123");
        FileReads();
    }

    public static synchronized SchoolManagementSystem getInstance() {
        if (instance == null) {
            instance = new SchoolManagementSystem();
        }
        return instance;
    }

    public void Menu() {
        Scanner sc = new Scanner(System.in);
        int choice = 0;
        int programChoice = 0;

        while (true) {
            System.out.println("-----------------------------------------------------");
            System.out.println("(1) Log in\n(2) Sign-in request\n(3) Quit");
            System.out.println("-----------------------------------------------------");
            System.out.print("Please choose: ");
            do {
                try { // Checking if input is an integer
                    choice = sc.nextInt();
                } catch (InputMismatchException e) {
                    System.out.println(ANSI_RED + "Invalid operation!" + ANSI_RESET);
                    System.out.print("Please choose: ");
                    sc.next();
                    continue;
                }
                if (choice != 1 && choice != 2 && choice != 3) {
                    System.out.println(ANSI_RED + "Invalid operation!" + ANSI_RESET);
                    System.out.print("Please choose: ");
                }
            } while (choice != 1 && choice != 2 && choice != 3);

            if (choice == 1) {
                String mail;
                String password;
                do {
                    System.out.println("↦↦↦↦↦↦↦↦↦↦↦↦↦↦↦↦↦↦↦↦↦↦↦↦↦↦↦↦↦↦↦↦↦↦↦↦↦↦↦↦↦↦↦↦↦↦↦↦↦↦↦↦↦");
                    try {
                        System.out.print("Mail: ");
                        mail = sc.next();
                        if (!isValidEmail(mail)) {
                            throw new IllegalArgumentException("Invalid email format.");
                        }

                        System.out.print("Password: ");
                        password = sc.next();
                        System.out.println("↦↦↦↦↦↦↦↦↦↦↦↦↦↦↦↦↦↦↦↦↦↦↦↦↦↦↦↦↦↦↦↦↦↦↦↦↦↦↦↦↦↦↦↦↦↦↦↦↦↦↦↦↦");

                    } catch (IllegalArgumentException e) {
                        System.out.println("Error: " + e.getMessage());
                        mail = "";
                        password = "";
                    } catch (InputMismatchException e) {
                        System.out.println("Error: Invalid input type. Please enter a valid string.");
                        sc.next();  // Clear the invalid input
                        mail = "";
                        password = "";
                    }
                } while (!checkLogin(mail, password));

                System.out.println("\nLogged in successfully!");

                boolean exit = false;

                if (mail.endsWith("@ogr.deu.edu.tr")) { // Student Login
                    int t;
                    for (t = 0; t < studentCount; t++) { // Finding the student
                        if (mail.equals(students[t].getEmail())) {
                            break;
                        }
                    }
                    System.out.println("-----------------------------------------------------");
                    System.out.println("(1) Normal program\n(2) Summer School Program");
                    System.out.println("-----------------------------------------------------");
                    System.out.print("Please choose: ");
                    do {
                        try { // Checking if input is an integer
                            programChoice = sc.nextInt();
                        } catch (InputMismatchException e) {
                            System.out.println(ANSI_RED + "Invalid operation!" + ANSI_RESET);
                            System.out.print("Please choose: ");
                            sc.next();
                            continue;
                        }
                        if (programChoice != 1 && programChoice != 2) {
                            System.out.println(ANSI_RED + "Invalid operation!" + ANSI_RESET);
                            System.out.print("Please choose: ");
                        }
                    } while (programChoice != 1 && programChoice != 2);

                    if (programChoice == 2) {
                        System.out.println("\nYou are directing to payment screen . . .");
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                        System.out.print("\nAmount to pay: 1200TL");
                        String confirm;
                        do {
                            System.out.print("\nConfirm payment ? (y/n): ");
                            confirm = sc.next();
                            if (!confirm.equalsIgnoreCase("y") && !confirm.equalsIgnoreCase("n"))
                                System.out.println(ANSI_RED + "Invalid choice!" + ANSI_RESET);
                        } while (!confirm.equalsIgnoreCase("y") && !confirm.equalsIgnoreCase("n"));
                        System.out.println("\nPlease wait . . .");
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                        if (confirm.equalsIgnoreCase("y")) {
                            System.out.println("\nPayment withdrawn!");
                        } else {
                            break;
                        }
                    }

                    // Student Menu
                    do {
                        System.out.println("\n▀▄▀▄▀▄▀▄▀▄▀▄▀▄▀▄▀▄▀▄▀▄▀▄▀▄ MENU ▄▀▄▀▄▀▄▀▄▀▄▀▄▀▄▀▄▀▄▀▄▀▄▀▄▀");
                        System.out.println("Please select the option that you desire to transact with:");
                        System.out.println("↦↦↦↦↦↦↦↦↦↦↦↦↦↦↦↦↦↦↦↦↦↦↦↦↦↦↦↦↦↦↦↦↦↦↦↦↦↦↦↦↦↦↦↦↦↦↦↦↦↦↦↦↦");
                        System.out.println("\n(1)  Check Grade");
                        System.out.println("(2)  Attend Lecture");
                        System.out.println("(3)  Check Notifications");
                        System.out.println("(4)  Demand Meeting");
                        System.out.println("(5)  Submit Homework");
                        System.out.println("(6)  Write to Forum");
                        System.out.println("(7)  Display Forum");
                        System.out.println("(8)  Objection to Exam");
                        System.out.println("(9)  Check Syllabus");
                        System.out.println("(10) Exit");

                        boolean validChoice = false;
                        do {
                            String input = sc.next();
                            try {
                                choice = Integer.parseInt(input);
                                if (choice < 1 || choice > 10) {
                                    System.out.println(ANSI_RED + "Invalid operation!" + ANSI_RESET);
                                } else {
                                    validChoice = true;
                                }
                            } catch (NumberFormatException e) {
                                System.out.println(ANSI_RED + "Invalid input! Please enter a number between 1 and 10." + ANSI_RESET);
                            }
                        } while (!validChoice);

                        if (choice == 1) checkGrade(t);
                        if (choice == 2) attendLecture(t);
                        if (choice == 3) checkNotifications(t);
                        if (choice == 4) demandMeeting(t);
                        if (choice == 5) submitHomework(t);
                        if (choice == 6) writeToForum(t);
                        if (choice == 7) displayForum(t);
                        if (choice == 8) objectionToExam(t);
                        if (choice == 9) checkSyllabusStudent(t);
                        if (choice == 10) exit = true;
                    } while (!exit);


                } else if (mail.equals(admin.getEmail())) {
                    // Admin Menu
                    do {
                        System.out.println("\n▀▄▀▄▀▄▀▄▀▄▀▄▀▄▀▄▀▄▀▄▀▄▀▄▀▄ MENU ▄▀▄▀▄▀▄▀▄▀▄▀▄▀▄▀▄▀▄▀▄▀▄▀▄▀");
                        System.out.println("Please select the option that you desire to transact with:");
                        System.out.println("----------------------------------------------------------");
                        System.out.println("(1) Check sign-in requests.");
                        System.out.println("(2) Exit");

                        do {
                            choice = sc.nextInt();
                            if (choice != 1 && choice != 2)
                                System.out.println(ANSI_RED + "Invalid operation!" + ANSI_RESET);
                        } while (choice != 1 && choice != 2);

                        if (choice == 1) {
                            handleSignInRequests();
                        } else {
                            exit = true;
                        }

                    }
                    while (!exit);
                } else if (mail.endsWith("@deu.edu.tr")) { // Teacher Login
                    int t;
                    for (t = 0; t < teacherCount; t++) { // Finding the teacher
                        if (mail.equals(teachers[t].getEmail())) {
                            break;
                        }
                    }

                    // Teacher Menu
                    do {
                        System.out.println("\n▀▄▀▄▀▄▀▄▀▄▀▄▀▄▀▄▀▄▀▄▀▄▀▄▀▄ MENU ▄▀▄▀▄▀▄▀▄▀▄▀▄▀▄▀▄▀▄▀▄▀▄▀▄▀");
                        System.out.println("Please select the option that you desire to transact with:");
                        System.out.println("----------------------------------------------------------");
                        System.out.println("(1)  Check Syllabus");
                        System.out.println("(2)  Enter Grade");
                        System.out.println("(3)  Change Grading");
                        System.out.println("(4)  Check Student Information");  // ITERATOR PATTERN DESIGN - Iterator that visits every elements in list
                        System.out.println("(5)  Submit Attendance");
                        System.out.println("(6)  Give Homework");
                        System.out.println("(7)  Give Notification");
                        System.out.println("(8)  Check Feedbacks");
                        System.out.println("(9)  Give Lecture");
                        System.out.println("(10) Check Objection");
                        System.out.println("(11) Publish Letter Grades");
                        System.out.println("(12) Exit");

                        boolean validChoice = false;
                        do {
                            String input = sc.next();
                            try {
                                choice = Integer.parseInt(input);
                                if (choice < 1 || choice > 12) {
                                    System.out.println(ANSI_RED + "Invalid operation!" + ANSI_RESET);
                                } else {
                                    validChoice = true;
                                }
                            } catch (NumberFormatException e) {
                                System.out.println(ANSI_RED + "Invalid input! Please enter a number between 1 and 10." + ANSI_RESET);
                            }
                        } while (!validChoice);

                        if (choice == 1) checkSyllabus(t);
                        if (choice == 2) enterGrade(t);
                        if (choice == 3) changeGrading(t);
                        if (choice == 4)
                            checkStudentInfo(t);  // ITERATOR PATTERN DESIGN - Iterator that visits every elements in list
                        if (choice == 5) submitAttendance(t);
                        if (choice == 6) giveAssignment(t);
                        if (choice == 7) giveNotification(t);
                        if (choice == 8) checkFeedBacks(t);
                        if (choice == 9) giveLecture(t);
                        if (choice == 10) checkObjections(t);
                        if (choice == 11) publishLetterGrades(t);
                        if (choice == 12) exit = true;

                    } while (!exit);

                } else {
                    System.out.println("Invalid extension!");
                }

            } else if (choice == 2) {
                System.out.println("For Sign in request you have to fill the form in the given order: ");
                System.out.print("Name: ");
                String name = sc.next();
                name = FixString(name);
                while (!name.matches("^[A-Za-zİığŞşÖöÜü]+$")) {
                    System.out.println(ANSI_RED + "Name should only contain letters!" + ANSI_RESET);
                    System.out.print("Name: ");
                    name = sc.next();
                    name = FixString(name);
                }
                System.out.print("Surname: ");
                String surname = sc.next();
                surname = FixString(surname);
                while (!surname.matches("^[A-Za-zİığŞşÖöÜü]+$")) {
                    System.out.println(ANSI_RED + "Surname should only contain letters!" + ANSI_RESET);
                    System.out.print("Surname: ");
                    surname = sc.next();
                    surname = FixString(surname);
                }
                System.out.print("TCKN: ");
                String TCKN = sc.next();
                while (!isValidTCKN(TCKN)) {
                    System.out.println(ANSI_RED + "Invalid TCKN!" + ANSI_RESET);
                    System.out.print("TCKN: ");
                    TCKN = sc.next();
                }
                System.out.print("Birthdate (dd/MM/yyyy): ");
                String birthdate = sc.next();
                while (!isValidDate(birthdate)) {
                    System.out.println(ANSI_RED + "Invalid date!" + ANSI_RESET);
                    System.out.print("Birthdate (dd/MM/yyyy): ");
                    birthdate = sc.next();
                }
                System.out.print("Phone number: ");
                System.out.print("Phone number (starting with +90): ");
                String phone = sc.next();
                while (!phone.matches("^\\+90[0-9]{10}$")) {
                    System.out.println(ANSI_RED + "Phone number should start with +90 and contain exactly 12 digits after +90!" + ANSI_RESET);
                    System.out.print("Phone number: ");
                    phone = sc.next();
                }

                sc.nextLine();
                System.out.print("Address: ");
                String address = sc.nextLine();

                String email = generateEmail(name, surname);
                String password = birthdate.replace("/", "");

                // Write to file
                try {
                    File myFile = new File("signInRequests.txt");
                    FileWriter myWriter = new FileWriter(myFile, true);
                    myWriter.write(TCKN + "," + name + "," + surname + "," + email + "," + password + "," + birthdate + "," + phone + "," + address + "\n");
                    myWriter.close();
                    System.out.println("\nYour request has been submitted successfully!");
                } catch (IOException e) {
                    System.out.println("An error occurred while writing to the file.");
                    e.printStackTrace();
                }
            } else break;
        }
    }

    public void displayForum(int t) {
        Scanner sc = new Scanner(System.in);
        int choice;
        System.out.println(ANSI_BOLD + "\n------------------------------ FORUM -----------------------------" + ANSI_RESET);
        String courseID;
        students[t].displayCourses();
        do {
            System.out.print("Select course ID (case sensitive): ");
            courseID = sc.next();
            if (!courseID.equals(students[t].getCourses()[0].getCourseID()) && !courseID.equals(students[t].getCourses()[1].getCourseID()) &&
                    !courseID.equals(students[t].getCourses()[2].getCourseID()) && !courseID.equals(students[t].getCourses()[3].getCourseID())) {
                System.out.println(ANSI_RED + "Please select from the listed courses." + ANSI_RESET);
            }
        } while (!courseID.equals(students[t].getCourses()[0].getCourseID()) && !courseID.equals(students[t].getCourses()[1].getCourseID()) &&
                !courseID.equals(students[t].getCourses()[2].getCourseID()) && !courseID.equals(students[t].getCourses()[3].getCourseID()));
        students[t].displayForum(findCourse(courseID));
        System.out.println("-------------------------------------------------------------------------\n");

        System.out.println("(1) Back to Menu");
        do {
            choice = sc.nextInt();
            if (choice != 1) System.out.println(ANSI_RED + "Invalid operation!" + ANSI_RESET);
        } while (choice != 1);
    }

    public void writeToForum(int t) {
        Scanner sc = new Scanner(System.in);
        int choice;
        System.out.println(ANSI_BOLD + "\n------------------------------ FORUM -----------------------------" + ANSI_RESET);
        String courseID;
        students[t].displayCourses();
        do {
            System.out.print("Select course ID (case sensitive): ");
            courseID = sc.next();
            if (!courseID.equals(students[t].getCourses()[0].getCourseID()) && !courseID.equals(students[t].getCourses()[1].getCourseID()) &&
                    !courseID.equals(students[t].getCourses()[2].getCourseID()) && !courseID.equals(students[t].getCourses()[3].getCourseID())) {
                System.out.println(ANSI_RED + "Please select from the listed courses." + ANSI_RESET);
            }
        } while (!courseID.equals(students[t].getCourses()[0].getCourseID()) && !courseID.equals(students[t].getCourses()[1].getCourseID()) &&
                !courseID.equals(students[t].getCourses()[2].getCourseID()) && !courseID.equals(students[t].getCourses()[3].getCourseID()));
        students[t].writeToForum(findCourse(courseID));
        System.out.println("-------------------------------------------------------------------------\n");

        System.out.println("(1) Back to Menu");
        do {
            choice = sc.nextInt();
            if (choice != 1) System.out.println(ANSI_RED + "Invalid operation!" + ANSI_RESET);
        } while (choice != 1);
    }

    public void submitHomework(int t) {

        System.out.println(ANSI_BOLD + "\n---------------- SUBMIT HOMEWORK ---------------------" + ANSI_RESET);

        String filePath = "homeworks.txt";
        int assingmentcount = 1;
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            boolean isHeader = true;

            while ((line = br.readLine()) != null) {
                if (isHeader) {
                    isHeader = false; // Skip the header line
                    continue;
                }
                String[] fields = line.split(",");

                String courseID = fields[0];
                String assignmentName = fields[1];
                String dueDate = fields[2];
                int maxPoints = Integer.parseInt(fields[3]);
                String description = fields[4];

                for (int i = 0; i < 4; i++) {
                    if (students[t].getCourses()[i].getCourseID().equals(courseID)) {
                        System.out.println("--------------------------------------------");
                        System.out.println("Assignment: " + assingmentcount);
                        System.out.println(courseID + " " + findCourse(courseID).getCourseName());
                        System.out.println("Due Date: " + dueDate);
                        System.out.println("Description: " + description);
                        System.out.println("--------------------------------------------");
                        assingmentcount++;
                    }

                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        Scanner scan = new Scanner(System.in);

        System.out.println("(1) Return menu");
        System.out.println("(2) Submit homework");
        int choice1;
        do {
            choice1 = scan.nextInt();
            if (choice1 != 1 && choice1 != 2) {
                System.out.println("Invalid choice!");
            }
        } while (choice1 != 1 && choice1 != 2);

        if (choice1 == 2) {
            System.out.print("Please choose the homework you want to submit: ");

            int choice = 0;
            do {
                choice = scan.nextInt();
                if (!(choice > 0 && choice < assingmentcount)) {
                    System.out.println("Please choose correct index to submit!!");
                }
            } while (!(choice > 0 && choice < assingmentcount));

            System.out.println("Succesfully submitted");
        }


    }

    public void demandMeeting(int t) {
        Scanner sc = new Scanner(System.in);
        int choice;
        System.out.println(ANSI_BOLD + "\n----------------------------- DEMAND MEETING ----------------------------" + ANSI_RESET);
        String courseID;
        students[t].displayCourses();
        do {
            System.out.print("Select course ID (case sensitive): ");
            courseID = sc.next();
            if (!courseID.equals(students[t].getCourses()[0].getCourseID()) && !courseID.equals(students[t].getCourses()[1].getCourseID()) &&
                    !courseID.equals(students[t].getCourses()[2].getCourseID()) && !courseID.equals(students[t].getCourses()[3].getCourseID())) {
                System.out.println(ANSI_RED + "Please select from the listed courses." + ANSI_RESET);
            }
        } while (!courseID.equals(students[t].getCourses()[0].getCourseID()) && !courseID.equals(students[t].getCourses()[1].getCourseID()) &&
                !courseID.equals(students[t].getCourses()[2].getCourseID()) && !courseID.equals(students[t].getCourses()[3].getCourseID()));
        System.out.print("Meeting date (dd/mm/yyyy): ");
        String meetingDateString = sc.next();
        while (!isValidDate(meetingDateString)) {
            System.out.println(ANSI_RED + "Invalid date!" + ANSI_RESET);
            System.out.print("Meeting date (dd/mm/yyyy): ");
            meetingDateString = sc.next();
        }
        Date meetingDate = new Date(meetingDateString);
        students[t].demandMeeting(findCourse(courseID), meetingDate);
        System.out.println("-------------------------------------------------------------------------\n");
        System.out.println("(1) Back to Menu");
        do {
            choice = sc.nextInt();
            if (choice != 1) System.out.println(ANSI_RED + "Invalid operation!" + ANSI_RESET);
        } while (choice != 1);
    }

    public void checkNotifications(int t) {
        Scanner sc = new Scanner(System.in);
        int choice;
        System.out.println(ANSI_BOLD + "\n------------------------------ NOTIFICATION -----------------------------" + ANSI_RESET);
        String courseID;
        students[t].displayCourses();
        do {
            System.out.print("Select course ID (case sensitive): ");
            courseID = sc.next();
            if (!courseID.equals(students[t].getCourses()[0].getCourseID()) && !courseID.equals(students[t].getCourses()[1].getCourseID()) &&
                    !courseID.equals(students[t].getCourses()[2].getCourseID()) && !courseID.equals(students[t].getCourses()[3].getCourseID())) {
                System.out.println(ANSI_RED + "Please select from the listed courses." + ANSI_RESET);
            }
        } while (!courseID.equals(students[t].getCourses()[0].getCourseID()) && !courseID.equals(students[t].getCourses()[1].getCourseID()) &&
                !courseID.equals(students[t].getCourses()[2].getCourseID()) && !courseID.equals(students[t].getCourses()[3].getCourseID()));
        students[t].checkNotification(findCourse(courseID));
        System.out.println("-------------------------------------------------------------------------\n");

        System.out.println("(1) Back to Menu");
        do {
            choice = sc.nextInt();
            if (choice != 1) System.out.println(ANSI_RED + "Invalid operation!" + ANSI_RESET);
        } while (choice != 1);
    }

    public void attendLecture(int t) {
        int choice;
        Scanner sc = new Scanner(System.in);
        System.out.println(ANSI_BOLD + "\n-------------------------------- LECTURE --------------------------------" + ANSI_RESET);
        String courseID;
        students[t].displayCourses();
        do {
            System.out.print("Select course ID (case sensitive): ");
            courseID = sc.next();
            if (!courseID.equals(students[t].getCourses()[0].getCourseID()) && !courseID.equals(students[t].getCourses()[1].getCourseID()) &&
                    !courseID.equals(students[t].getCourses()[2].getCourseID()) && !courseID.equals(students[t].getCourses()[3].getCourseID())) {
                System.out.println(ANSI_RED + "Please select from the listed courses." + ANSI_RESET);
            }
        } while (!courseID.equals(students[t].getCourses()[0].getCourseID()) && !courseID.equals(students[t].getCourses()[1].getCourseID()) &&
                !courseID.equals(students[t].getCourses()[2].getCourseID()) && !courseID.equals(students[t].getCourses()[3].getCourseID()));
        System.out.print("\nWelcome to " + findCourse(courseID).getCourseID() + " - " + findCourse(courseID).getCourseName());
        System.out.println("\nLecture is in progress. . .");
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Lecture has ended.\n");
        System.out.println("-------------------------------------------------------------------------\n");
        System.out.println("(1) Back to Menu");
        do {
            choice = sc.nextInt();
            if (choice != 1) System.out.println(ANSI_RED + "Invalid operation!" + ANSI_RESET);
        } while (choice != 1);

    }

    public void checkGrade(int t) {

        System.out.println(ANSI_BOLD + "\n----------------------------- CHECK GRADE -------------------------------" + ANSI_RESET);

        students[t].displayGrade(students[t].getCourses()[0]);
        students[t].displayGrade(students[t].getCourses()[1]);
        students[t].displayGrade(students[t].getCourses()[2]);
        students[t].displayGrade(students[t].getCourses()[3]);

        Scanner sc = new Scanner(System.in);
        int choice;
        System.out.println("(1) Back to Menu");
        do {
            choice = sc.nextInt();
            if (choice != 1) System.out.println(ANSI_RED + "Invalid operation!" + ANSI_RESET);
        } while (choice != 1);

    }

    public static String FixString(String name) {
        if (name == null || name.isEmpty()) {
            return ""; // Return empty string if input is null or empty
        }
        String[] words = name.split(" "); // Split input into words
        StringBuilder result = new StringBuilder();
        for (String word : words) {
            if (!word.isEmpty()) {
                if (result.length() > 0) {
                    result.append(" "); // Add space before appending next word
                }
                word = word.toLowerCase(); // Convert word to lowercase
                // Capitalize first letter of word
                word = Character.toUpperCase(word.charAt(0)) + word.substring(1);
                // Handle capitalization after apostrophe
                int apostropheIndex = word.indexOf("'");
                if (apostropheIndex != -1 && word.length() > apostropheIndex + 1) {
                    char letterAfterApostrophe = Character.toUpperCase(word.charAt(apostropheIndex + 1));
                    word = word.substring(0, apostropheIndex + 1) + letterAfterApostrophe + word.substring(apostropheIndex + 2);
                }
                result.append(word); // Append modified word to result
            }
        }
        return result.toString(); // Return the fixed string
    }

    public boolean isValidTCKN(String TCKN) {
        if (TCKN.length() != 11 || TCKN.charAt(0) == '0') return false;
        int totalOdd = 0;
        int totalEven = 0;
        for (int i = 0; i < 9; i += 2) {
            totalOdd += Character.getNumericValue(TCKN.charAt(i));
        }
        for (int i = 1; i < 8; i += 2) {
            totalEven += Character.getNumericValue(TCKN.charAt(i));
        }
        int digit10 = ((totalOdd * 7) - totalEven) % 10;
        int digit11 = (totalOdd + totalEven + digit10) % 10;
        return digit10 == Character.getNumericValue(TCKN.charAt(9)) && digit11 == Character.getNumericValue(TCKN.charAt(10));
    }

    public boolean isValidDate(String date) {
        try {
            String[] parts = date.split("/");
            int day = Integer.parseInt(parts[0]);
            int month = Integer.parseInt(parts[1]);
            int year = Integer.parseInt(parts[2]);
            if (year < 1900 || year > 2024) return false;
            if (month < 1 || month > 12) return false;
            if (day < 1 || day > 31) return false;
            if ((month == 4 || month == 6 || month == 9 || month == 11) && day > 30) return false;
            if (month == 2) {
                if (year % 4 == 0 && (year % 100 != 0 || year % 400 == 0)) {
                    return day <= 29;
                } else {
                    return day <= 28;
                }
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private void readStudentsFromFile() {
        File myFile = new File("students.txt");
        Scanner myReader = null;
        try {
            myReader = new Scanner(myFile);
            myReader.nextLine(); // Skip the first line assuming it's a header
            while (myReader.hasNextLine()) {
                String line = myReader.nextLine();
                String[] attributes = line.split(",");

                int studentID = Integer.parseInt(attributes[0]);
                String TCKN = attributes[1];
                String name = attributes[2];
                String surname = attributes[3];
                String email = attributes[4];
                String password = attributes[5];
                Date birthDate = new Date(attributes[6]);
                String phone = attributes[7];
                String address = attributes[8];
                int teacherID = Integer.parseInt(attributes[9]);
                Teacher lecturer = getAdvisor(teacherID);
                float gpa = Float.parseFloat(attributes[10]);
                int enrollmentYear = Integer.parseInt(attributes[11]);
                int totalCredit = Integer.parseInt(attributes[12]);

                Course[] c = new Course[4];
                int temp1 = -1;
                int temp2 = -1;
                int temp3 = -1;
                int temp4 = -1;

                for (int i = 0; i < courseCount; i++) {
                    if (courses[i].getCourseID().equals(attributes[13])) {
                        c[0] = courses[i];
                        temp1 = i;
                    }
                    if (courses[i].getCourseID().equals(attributes[14])) {
                        c[1] = courses[i];
                        temp2 = i;
                    }
                    if (courses[i].getCourseID().equals(attributes[15])) {
                        c[2] = courses[i];
                        temp3 = i;
                    }
                    if (courses[i].getCourseID().equals(attributes[16])) {
                        c[3] = courses[i];
                        temp4 = i;
                    }

                }

                students[studentCount] = (new Student(studentID, TCKN, name, surname, email, lecturer, password, birthDate, phone, address, c, gpa, enrollmentYear, null, totalCredit));
                courses[temp1].addStudent(students[studentCount]);
                courses[temp2].addStudent(students[studentCount]);
                courses[temp3].addStudent(students[studentCount]);
                courses[temp4].addStudent(students[studentCount]);

                studentCount++;
            }
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred while reading the file.");
            e.printStackTrace();
        } catch (NumberFormatException e) {
            System.out.println("An error occurred while parsing numbers.");
            e.printStackTrace();
        } finally {
            if (myReader != null) {
                myReader.close();
            }
        }
    }

    private void readTeachersFromFile(Course[] courses) {
        try {
            File myFile = new File("teachers.txt");
            Scanner myReader = new Scanner(myFile);
            myReader.nextLine(); // skipping header line
            while (myReader.hasNextLine()) {
                String line = myReader.nextLine();
                String[] attributes = line.split(",");
                int teacherID = Integer.parseInt(attributes[0]);
                String TCKN = attributes[1];
                String title = attributes[2];
                String name = attributes[3];
                String surname = attributes[4];
                String email = attributes[5];
                String password = attributes[6];
                Date birthDate = new Date(attributes[7]);
                String phone = attributes[8];
                String address = attributes[9];

                Course[] c = new Course[5];
                int temp1 = -1;
                int temp2 = -1;
                for (int i = 0; i < courseCount; i++) {
                    if (courses[i].getCourseID().equals(attributes[10])) {
                        c[0] = courses[i];
                        temp1 = i;
                    }
                    if (courses[i].getCourseID().equals(attributes[11])) {
                        c[1] = courses[i];
                        temp2 = i;
                    }
                }
                teachers[teacherCount] = new Teacher(teacherID, TCKN, title, name, surname, email, password, birthDate, phone, address, c);

                if (temp2 != -1 && temp1 != -1) {
                    courses[temp1].addTeacher(teachers[teacherCount]);
                    courses[temp2].addTeacher(teachers[teacherCount]);
                }

                teacherCount++;
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred while reading the file.");
            e.printStackTrace();
        }

    }

    private void readCoursesFromFile() {
        try {
            File myFile = new File("courses.txt");
            Scanner myReader = new Scanner(myFile);
            myReader.nextLine(); // skipping the header
            while (myReader.hasNextLine()) {
                String line = myReader.nextLine();
                String[] attributes = line.split(",");
                String courseID = attributes[0];
                String courseName = attributes[1];
                int credit = Integer.parseInt(attributes[2]);
                boolean isElective = Boolean.parseBoolean(attributes[3]);
                // Assuming preRequisites is a single course ID
                String preRequisitesID = attributes[4];
                boolean lab = Boolean.parseBoolean(attributes[5]);

                // Find related objects
                Book[] books1 = findCourse(books, courseID);
                ExamEvaluation examEvaluation1 = findCourse(examEvaluation, courseID);

                // Create Course object
                courses[courseCount] = new Course(courseID, courseName, credit, books1, preRequisitesID, lab, isElective, examEvaluation1);
                courseCount++;
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred while reading the file.");
            e.printStackTrace();
        }
    }

    private void readSignInRequests() {
        File myFile = new File("signInRequests.txt");

        try (Scanner myReader = new Scanner(myFile)) {
            if (!myReader.hasNextLine()) {
                System.out.println("There are no sign-in requests.");
                return; // Exit the method if the file is empty
            }

            myReader.nextLine(); // skipping the header

            while (myReader.hasNextLine()) {
                String line = myReader.nextLine();
                if (line.trim().isEmpty()) {
                    continue; // Skip empty lines
                }

                String[] attributes = line.split(",");
                if (attributes.length != 8) {
                    System.out.println("Incorrect data format in line: " + line);
                    continue; // Skip lines with incorrect format
                }

                String TCKN = attributes[0];
                String name = attributes[1];
                String surname = attributes[2];
                String email = attributes[3];
                String password = attributes[4];
                Date birthDate = new Date(attributes[5]);
                String phone = attributes[6];
                String address = attributes[7];

                requests[requestCount] = new User(TCKN, name, surname, email, password, birthDate, phone, address);
                requestCount++;
            }
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred while reading the file.");
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println("An error occurred while processing the file.");
            e.printStackTrace();
        }
    }

    private void readBooks() {
        File myFile = new File("books.txt");

        try (Scanner myReader = new Scanner(myFile)) {
            if (!myReader.hasNextLine()) {
                System.out.println("There are no books on file.");
                return; // Exit the method if the file is empty
            }

            myReader.nextLine(); // skipping the header

            while (myReader.hasNextLine()) {
                String line = myReader.nextLine();
                if (line.trim().isEmpty()) {
                    continue; // Skip empty lines
                }

                String[] attributes = line.split(",");
                if (attributes.length != 7) {
                    System.out.println("Incorrect data format in line: " + line);
                    continue; // Skip lines with incorrect format
                }

                int bookId = Integer.parseInt(attributes[0]);
                String bookName = attributes[1];
                String author = attributes[2];
                int chapters = Integer.parseInt(attributes[3]);
                int pages = Integer.parseInt(attributes[4]);
                int edition = Integer.parseInt(attributes[5]);
                String courseCode = attributes[6];
                books[bookCount] = new Book(bookId, bookName, author, chapters, pages, edition, courseCode);
                bookCount++;
            }
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred while reading the file.");
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println("An error occurred while processing the file.");
            e.printStackTrace();
        }
    }

    private void readExamEval() {
        File myFile = new File("examEvaluation.txt");

        try (Scanner myReader = new Scanner(myFile)) {
            if (!myReader.hasNextLine()) {
                System.out.println("There are no evaluations on file.");
                return; // Exit the method if the file is empty
            }

            myReader.nextLine(); // skipping the header

            while (myReader.hasNextLine()) {
                String line = myReader.nextLine();
                if (line.trim().isEmpty()) {
                    continue; // Skip empty lines
                }
                String[] attributes = line.split(",");
                if (attributes.length != 7) {
                    System.out.println("Incorrect data format in line: " + line);
                    continue; // Skip lines with incorrect format
                }
                //courseCode,midtermWeight,finalWeight,quizWeight,homeworkWeight,labWeight,makeupWeight
                String courseCode = attributes[0];
                Double midtermWeight = Double.valueOf(attributes[1]);
                Double finalWeight = Double.valueOf(attributes[2]);
                Double quizWeight = Double.valueOf((attributes[3]));
                Double homeworkWeight = Double.valueOf((attributes[4]));
                Double labWeight = Double.valueOf((attributes[5]));
                Double makeupWeight = Double.valueOf(attributes[6]);
                examEvaluation[examEvaluationCount] = new ExamEvaluation(midtermWeight, finalWeight, quizWeight, homeworkWeight, labWeight, makeupWeight, courseCode);
                examEvaluationCount++;
            }
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred while reading the file.");
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println("An error occurred while processing the file.");
            e.printStackTrace();
        }
    }

    private void readGrades() {
        File myFile = new File("grades.txt");

        try (Scanner myReader = new Scanner(myFile)) {
            if (!myReader.hasNextLine()) {
                System.out.println("There are no evaluations on file.");
                return; // Exit the method if the file is empty
            }

            myReader.nextLine(); // skipping the header

            while (myReader.hasNextLine()) {
                String line = myReader.nextLine();
                if (line.trim().isEmpty()) {
                    continue; // Skip empty lines
                }
                String[] attributes = line.split(",");
                if (attributes.length != 8) {
                    System.out.println("Incorrect data format in line: " + line);
                    continue; // Skip lines with incorrect format
                }
                //StudentID,CourseID,midtermGrade,quizGrade,labGrade,homeworkGrade,finalGrade,makeUpGrade
                int StudentID = Integer.parseInt(attributes[0]);
                String CourseID = attributes[1];
                int midterm = Integer.parseInt(attributes[2]);
                int quiz = Integer.parseInt((attributes[3]));
                int lab = Integer.parseInt((attributes[4]));
                int homework = Integer.parseInt((attributes[5]));
                int makeup = Integer.parseInt(attributes[6]);
                int _final = Integer.parseInt(attributes[7]);


                Grade g = new Grade(CourseID, midterm, quiz, lab, homework, makeup, _final);
                findStudent(StudentID).addGrade(g);

            }
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred while reading the file.");
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println("An error occurred while processing the file.");
            e.printStackTrace();
        }

    }

    private void readAttendance() {
        File myFile = new File("attendance.txt");

        try (Scanner myReader = new Scanner(myFile)) {
            if (!myReader.hasNextLine()) {
                System.out.println("There are no evaluations on file.");
                return; // Exit the method if the file is empty
            }

            myReader.nextLine(); // skipping the header

            while (myReader.hasNextLine()) {
                String line = myReader.nextLine();
                if (line.trim().isEmpty()) {
                    continue; // Skip empty lines
                }
                String[] attributes = line.split(",");
                if (attributes.length != 16) {
                    System.out.println("Incorrect data format in line: " + line);
                    continue; // Skip lines with incorrect format
                }
                //StudentID,CourseID,midtermGrade,quizGrade,labGrade,homeworkGrade,finalGrade,makeUpGrade
                int StudentID = Integer.parseInt(attributes[0]);
                String CourseID = attributes[1];
                String[] attendance = new String[14];
                for (int i = 0; i < 14; i++) {
                    attendance[i] = attributes[i + 2];
                }

                Attendance a = new Attendance(CourseID, attendance);
                findStudent(StudentID).addAttendance(a);
            }
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred while reading the file.");
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println("An error occurred while processing the file.");
            e.printStackTrace();
        }
    }

    private void FileReads() {

        readSignInRequests();
        readBooks();
        readExamEval();
        readCoursesFromFile();
        readTeachersFromFile(courses);
        readStudentsFromFile();
        readGrades();
        readAttendance();
    }

        public static boolean isValidEmail(String email) {
            // Basic email format validation
            String emailRegex = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";
            return email.matches(emailRegex);
        }

    public boolean checkLogin(String mail, String password) {
        boolean flag = false;
        boolean wrongPassword = false;
        boolean notSuchUser = false;
        if (mail.endsWith("@ogr.deu.edu.tr")) {
            for (int i = 0; i < studentCount; i++) {
                if (students[i].getEmail().equals(mail)) {
                    if (students[i].getPassword().equals(password)) {
                        flag = true;
                        notSuchUser = false;
                        break;
                    } else {
                        wrongPassword = true;
                    }
                } else {
                    notSuchUser = true;
                }
            }
        } else if (mail.equals(admin.getEmail())) {
            if (password.equals(admin.getPassword())) {
                flag = true;
            } else {
                wrongPassword = true;
            }
        } else if (mail.endsWith("@deu.edu.tr")) {
            for (int i = 0; i < teacherCount; i++) {
                if (teachers[i].getEmail().equals(mail)) {
                    if (teachers[i].getPassword().equals(password)) {
                        flag = true;
                        notSuchUser = false;
                        break;
                    } else {
                        wrongPassword = true;
                    }
                } else {
                    notSuchUser = true;
                }
            }
        } else {  /// adminstator login check maybe up for deu.edu.tr maybe different extension
            notSuchUser = true;
        }

        if (notSuchUser && isValidEmail(mail)) {
            System.out.println("Email or Password is incorrect");
        } else if (wrongPassword) {
            System.out.println("Wrong Password Try again");
        }
        return flag;
    }

    private void checkSignInRequests() {

        for (int i = 0; i < requestCount; i++) {
            System.out.println(ANSI_BOLD + "Request: " + (i + 1) + ANSI_RESET);
            requests[i].display();
        }


    }

    private ExamEvaluation findCourse(ExamEvaluation[] array, String courseCode) {
        for (ExamEvaluation examEval : array) {
            if (examEval.getCourseCode().equals(courseCode)) {
                return examEval;
            }
        }
        return null; // Return null if no matching course code is found
    }

    private Book[] findCourse(Book[] array, String courseCode) {
        List<Book> matchingBooks = new ArrayList<>();

        for (Book book : array) {
            if (book != null && book.getCourseCode().equals(courseCode)) {
                matchingBooks.add(book);
            }
        }

        // Convert the list back to an array
        Book[] result = new Book[matchingBooks.size()];
        return matchingBooks.toArray(result);
    }

    private Course findCourse(String courseID) {
        for (int i = 0; i < courseCount; i++) {
            if (courses[i].getCourseID().equals(courseID)) {
                return courses[i];
            }
        }
        return null;
    }

    private Teacher getAdvisor(int teacherID) {

        for (int i = 0; i < teacherCount; i++) {

            if (teachers[i].getID() == teacherID) {
                return teachers[i];
            }
        }
        return null;
    }



    private Student findStudent(int StudentID) {
        for (int i = 0; i < studentCount; i++) {

            if (students[i].getID() == StudentID) {
                return students[i];
            }
        }
        return null;
    }

    public void checkSyllabus(int t) {
        try {
            int count = 0;
            File file = new File("courseHours.txt");
            System.out.println(ANSI_BOLD + "\n--------------------------- CHECK SYLLABUS -----------------------------" + ANSI_RESET);
            Scanner scanner = new Scanner(file);
            scanner.nextLine();
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] parts = line.split(",");
                String courseCode = parts[0];
                String startingHour = parts[1];
                String endingHour = parts[2];
                String day = parts[3];
                if (teachers[t].getCourses()[0].getCourseID().equals(courseCode) || teachers[t].getCourses()[1].getCourseID().equals(courseCode)) {
                    if (count == 0) {
                        System.out.println();
                        System.out.println("Syllabus: ");
                    }
                    System.out.println(" Course: " + courseCode + "\n Day: " + day + "\n Starting Hour: " + startingHour + "\n Ending Hour: " + endingHour);
                    count++;
                    System.out.println("------------------------------------------------------------------------");
                }

            }
            scanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + e.getMessage());
        }
        Scanner sc = new Scanner(System.in);
        int choice;
        System.out.println("(1) Back to Menu");
        do {
            choice = sc.nextInt();
            if (choice != 1) System.out.println(ANSI_RED + "Invalid operation!" + ANSI_RESET);
        } while (choice != 1);
    }

    public void checkSyllabusStudent(int t) {
        try {
            int count = 0;
            File file = new File("courseHours.txt");
            System.out.println(ANSI_BOLD + "\n--------------------------- CHECK SYLLABUS -----------------------------" + ANSI_RESET);
            Scanner scanner = new Scanner(file);
            scanner.nextLine();
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] parts = line.split(",");
                String courseID = parts[0];
                String startingHour = parts[1];
                String endingHour = parts[2];
                String day = parts[3];
                if (courseID.equals(students[t].getCourses()[0].getCourseID()) || courseID.equals(students[t].getCourses()[1].getCourseID()) ||
                        courseID.equals(students[t].getCourses()[2].getCourseID()) || courseID.equals(students[t].getCourses()[3].getCourseID())) {
                    {
                        if (count == 0) {
                            System.out.println();
                            System.out.println("Syllabus: ");
                        }
                        System.out.println(" Course: " + courseID + "\n Day: " + day + "\n Starting Hour: " + startingHour + "\n Ending Hour: " + endingHour);
                        count++;
                        System.out.println("------------------------------------------------------------------------");
                    }
                }
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + e.getMessage());
        }
        Scanner sc = new Scanner(System.in);
        int choice;
        System.out.println("(1) Back to Menu");
        do {
            choice = sc.nextInt();
            if (choice != 1) System.out.println(ANSI_RED + "Invalid operation!" + ANSI_RESET);
        } while (choice != 1);
    }

    public void submitAttendance(int t) {
        int choice;
        Scanner sc = new Scanner(System.in);
        System.out.println(ANSI_BOLD + "\n--------------------------- SUBMIT ATTENDANCE -----------------------------" + ANSI_RESET);
        String courseID;
        teachers[t].displayCourses();
        do {
            System.out.print("Select course ID (case sensitive): ");
            courseID = sc.next();
            if (!courseID.equals(teachers[t].getCourses()[0].getCourseID()) && !courseID.equals(teachers[t].getCourses()[1].getCourseID())) {
                System.out.println(ANSI_RED + "Please select from the listed courses." + ANSI_RESET);
            }
        } while (!courseID.equals(teachers[t].getCourses()[0].getCourseID()) && !courseID.equals(teachers[t].getCourses()[1].getCourseID()));
        teachers[t].submitAttendance(findCourse(courseID));
        System.out.println("-------------------------------------------------------------------------\n");
        System.out.println("(1) Back to Menu");
        do {
            choice = sc.nextInt();
            if (choice != 1) System.out.println(ANSI_RED + "Invalid operation!" + ANSI_RESET);
        } while (choice != 1);
    }

    public void enterGrade(int t) {
        int choice;
        Scanner sc = new Scanner(System.in);
        System.out.println(ANSI_BOLD + "\n----------------------------- ENTER GRADE --------------------------------" + ANSI_RESET);
        String courseID;
        teachers[t].displayCourses();
        do {
            System.out.print("Select course ID (case sensitive): ");
            courseID = sc.next();
            if (!courseID.equals(teachers[t].getCourses()[0].getCourseID()) && !courseID.equals(teachers[t].getCourses()[1].getCourseID())) {
                System.out.println(ANSI_RED + "Please select from the listed courses." + ANSI_RESET);
            }
        } while (!courseID.equals(teachers[t].getCourses()[0].getCourseID()) && !courseID.equals(teachers[t].getCourses()[1].getCourseID()));
        teachers[t].enterGrade(findCourse(courseID));
        System.out.println("-------------------------------------------------------------------------\n");
        System.out.println("(1) Back to Menu");
        do {
            choice = sc.nextInt();
            if (choice != 1) System.out.println(ANSI_RED + "Invalid operation!" + ANSI_RESET);
        } while (choice != 1);
    }

    public void changeGrading(int t) {
        int choice;
        Scanner sc = new Scanner(System.in);
        System.out.println(ANSI_BOLD + "\n---------------------------- CHANGE GRADE --------------------------------" + ANSI_RESET);
        String courseID;
        teachers[t].displayCourses();
        do {
            System.out.print("Select course ID (case sensitive): ");
            courseID = sc.next();
            if (!courseID.equals(teachers[t].getCourses()[0].getCourseID()) && !courseID.equals(teachers[t].getCourses()[1].getCourseID())) {
                System.out.println(ANSI_RED + "Please select from the listed courses." + ANSI_RESET);
            }
        } while (!courseID.equals(teachers[t].getCourses()[0].getCourseID()) && !courseID.equals(teachers[t].getCourses()[1].getCourseID()));
        teachers[t].changeGrade(findCourse(courseID));
        System.out.println("-------------------------------------------------------------------------\n");
        System.out.println("(1) Back to Menu");
        do {
            choice = sc.nextInt();
            if (choice != 1) System.out.println(ANSI_RED + "Invalid operation!" + ANSI_RESET);
        } while (choice != 1);
    }

    public void checkStudentInfo(int t) {
        // ITERATOR PATTERN DESIGN - Iterator that visits every elements in list
        int choice;
        Scanner sc = new Scanner(System.in);
        System.out.println(ANSI_BOLD + "\n---------------------- CHECK STUDENT INFORMATION ------------------------" + ANSI_RESET);
        String courseID;
        teachers[t].displayCourses();
        do {
            System.out.print("Select course ID (case sensitive): ");
            courseID = sc.next();
            if (!courseID.equals(teachers[t].getCourses()[0].getCourseID()) && !courseID.equals(teachers[t].getCourses()[1].getCourseID())) {
                System.out.println(ANSI_RED + "Please select from the listed courses." + ANSI_RESET);
            }
        } while (!courseID.equals(teachers[t].getCourses()[0].getCourseID()) && !courseID.equals(teachers[t].getCourses()[1].getCourseID()));
        // ITERATOR PATTERN DESIGN - Iterator that visits every elements in list
        teachers[t].checkStudentsInformation(findCourse(courseID));

        System.out.println("-------------------------------------------------------------------------\n");
        System.out.println("(1) Back to Menu");
        do {
            choice = sc.nextInt();
            if (choice != 1) System.out.println(ANSI_RED + "Invalid operation!" + ANSI_RESET);
        } while (choice != 1);
    }

    public void publishLetterGrades(int t) {
        int choice;
        Scanner sc = new Scanner(System.in);
        System.out.println(ANSI_BOLD + "\n---------------------------- PUBLISH GRADE -----------------------------" + ANSI_RESET);
        String courseID;
        teachers[t].displayCourses();
        do {
            System.out.print("Select course ID (case sensitive): ");
            courseID = sc.next();
            if (!courseID.equals(teachers[t].getCourses()[0].getCourseID()) && !courseID.equals(teachers[t].getCourses()[1].getCourseID())) {
                System.out.println(ANSI_RED + "Please select from the listed courses." + ANSI_RESET);
            }
        } while (!courseID.equals(teachers[t].getCourses()[0].getCourseID()) && !courseID.equals(teachers[t].getCourses()[1].getCourseID()));

        String choose;
        do {
            System.out.print("\nUse bell curve ? (y/n): ");
            choose = sc.next();
            if (!choose.equalsIgnoreCase("y") && !choose.equalsIgnoreCase("n"))
                System.out.println(ANSI_RED + "Invalid choice!" + ANSI_RESET);
        } while (!choose.equalsIgnoreCase("y") && !choose.equalsIgnoreCase("n"));

        boolean curve = choose.equalsIgnoreCase("y");
        for (int i = 0; i < findCourse(courseID).getStudent()[0].getGrades().length; i++) {
            if (findCourse(courseID).getStudent()[0].getGrades()[i].getCourseID().equals(courseID)) {
                if (findCourse(courseID).getStudent()[0].getGrades()[i].getFinalExam() != -1 ||
                        findCourse(courseID).getStudent()[0].getGrades()[i].getMakeup() != -1) {
                    findCourse(courseID).calculateLetterGrades(curve);
                    System.out.println("Letter grades are published successfully.");
                } else {
                    System.out.println("Students do not have a Final or Makeup grade.");
                }
                break;
            }
        }

        System.out.println("-------------------------------------------------------------------------\n");
        System.out.println("(1) Back to Menu");
        do {
            choice = sc.nextInt();
            if (choice != 1) System.out.println(ANSI_RED + "Invalid operation!" + ANSI_RESET);
        } while (choice != 1);
    }

    public void giveLecture(int t) {
        int choice;
        Scanner sc = new Scanner(System.in);
        System.out.println(ANSI_BOLD + "\n-------------------------------- LECTURE --------------------------------" + ANSI_RESET);
        String courseID;
        teachers[t].displayCourses();
        do {
            System.out.print("Select course ID (case sensitive): ");
            courseID = sc.next();
            if (!courseID.equals(teachers[t].getCourses()[0].getCourseID()) && !courseID.equals(teachers[t].getCourses()[1].getCourseID())) {
                System.out.println(ANSI_RED + "Please select from the listed courses." + ANSI_RESET);
            }
        } while (!courseID.equals(teachers[t].getCourses()[0].getCourseID()) && !courseID.equals(teachers[t].getCourses()[1].getCourseID()));
        System.out.print("\nWelcome to " + findCourse(courseID).getCourseID() + " - " + findCourse(courseID).getCourseName());
        System.out.println("\nLecture is in progress. . .");
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Lecture has ended.\n");
        System.out.println("-------------------------------------------------------------------------\n");
        System.out.println("(1) Back to Menu");
        do {
            choice = sc.nextInt();
            if (choice != 1) System.out.println(ANSI_RED + "Invalid operation!" + ANSI_RESET);
        } while (choice != 1);
    }

    public void giveAssignment(int t) {
        int choice;
        Scanner sc = new Scanner(System.in);
        System.out.println(ANSI_BOLD + "\n---------------------------- GIVE ASSIGNMENT ----------------------------" + ANSI_RESET);
        String courseID;
        teachers[t].displayCourses();
        do {
            System.out.print("Select course ID (case sensitive): ");
            courseID = sc.next();
            if (!courseID.equals(teachers[t].getCourses()[0].getCourseID()) && !courseID.equals(teachers[t].getCourses()[1].getCourseID())) {
                System.out.println(ANSI_RED + "Please select from the listed courses." + ANSI_RESET);
            }
        } while (!courseID.equals(teachers[t].getCourses()[0].getCourseID()) && !courseID.equals(teachers[t].getCourses()[1].getCourseID()));
        System.out.print("Due date (dd/mm/yyyy): ");
        String dueDateString = sc.next();
        while (!isValidDate(dueDateString)) {
            System.out.println(ANSI_RED + "Invalid date!" + ANSI_RESET);
            System.out.print("Due date (dd/mm/yyyy): ");
            dueDateString = sc.next();
        }
        Date dueDate = new Date(dueDateString);
        Course c = findCourse(courseID);
        Teacher.addAssignment(c, dueDate);
        System.out.println("-------------------------------------------------------------------------\n");
        System.out.println("(1) Back to Menu");
        do {
            choice = sc.nextInt();
            if (choice != 1) System.out.println(ANSI_RED + "Invalid operation!" + ANSI_RESET);
        } while (choice != 1);
    }

    public void giveNotification(int t) {
        Scanner sc = new Scanner(System.in);
        int choice;
        System.out.println(ANSI_BOLD + "\n------------------------------ NOTIFICATION -----------------------------" + ANSI_RESET);
        String courseID;
        teachers[t].displayCourses();
        do {
            System.out.print("Select course ID (case sensitive): ");
            courseID = sc.next();
            if (!courseID.equals(teachers[t].getCourses()[0].getCourseID()) && !courseID.equals(teachers[t].getCourses()[1].getCourseID())) {
                System.out.println(ANSI_RED + "Please select from the listed courses." + ANSI_RESET);
            }
        } while (!courseID.equals(teachers[t].getCourses()[0].getCourseID()) && !courseID.equals(teachers[t].getCourses()[1].getCourseID()));
        teachers[t].giveNotification(findCourse(courseID));
        System.out.println("-------------------------------------------------------------------------\n");
        System.out.println("(1) Back to Menu");
        do {
            choice = sc.nextInt();
            if (choice != 1) System.out.println(ANSI_RED + "Invalid operation!" + ANSI_RESET);
        } while (choice != 1);
    }

    public void checkFeedBacks(int t) {
        int choice;
        Scanner sc = new Scanner(System.in);
        System.out.println(ANSI_BOLD + "\n-------------------------------- FEEDBACK -------------------------------" + ANSI_RESET);
        teachers[t].displayCourses();
        teachers[t].checkFeedbacks();
        System.out.println("-------------------------------------------------------------------------\n");
        System.out.println("(1) Back to Menu");
        do {
            choice = sc.nextInt();
            if (choice != 1) System.out.println(ANSI_RED + "Invalid operation!" + ANSI_RESET);
        } while (choice != 1);
    }

    public static String FixTurkishString(String str) {
        return str.trim().replaceAll("\\s+", "").toLowerCase()
                .replace('ğ', 'g')
                .replace('ı', 'i')
                .replace('ö', 'o')
                .replace('ü', 'u')
                .replace('ş', 's')
                .replace('ç', 'c');
    }

    public static String generateEmail(String name, String surname) {
        String fixedName = FixTurkishString(name);
        String fixedSurname = FixTurkishString(surname);
        return fixedName + fixedSurname + "@ogr.deu.edu.tr";
    }

    public void deleteRequestAndUpdateFile(int requestNum) {
        if (requestNum <= 0 || requestNum > requestCount) {
            System.out.println("Invalid request number.");
            return;
        }

        // Find the index of the record to delete
        int indexToDelete = requestNum - 1;

        // Delete the record from the requests array
        for (int i = indexToDelete; i < requestCount - 1; i++) {
            requests[i] = requests[i + 1]; // Shift elements to the left
        }
        requestCount--;

        // Rewrite the signInRequests.txt file
        File file = new File("signInRequests.txt");
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            List<String> lines = new ArrayList<>();
            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
            reader.close();

            // Remove the line corresponding to the deleted entry
            lines.remove(indexToDelete + 1); // Add 1 to skip the header line

            // Rewrite the file
            BufferedWriter writer = new BufferedWriter(new FileWriter(file));
            for (String updatedLine : lines) {
                writer.write(updatedLine);
                writer.newLine();
            }
            writer.close();

        } catch (IOException e) {
            System.out.println("An error occurred while updating the file.");
            e.printStackTrace();
        }
    }

    public void addStudentToFile(Student student) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter("students.txt", true)); // true for append mode

            // Format the student details
            StringBuilder formattedStudent = new StringBuilder();

            formattedStudent.append(student.getStudentID()).append(",");
            formattedStudent.append(student.getTCKN()).append(",");
            formattedStudent.append(student.getName()).append(",");
            formattedStudent.append(student.getSurname()).append(",");
            formattedStudent.append(student.getEmail()).append(",");
            formattedStudent.append(student.getPassword()).append(",");
            formattedStudent.append(student.getBirthDate()).append(",");
            formattedStudent.append(student.getPhone()).append(",");
            formattedStudent.append(student.getAddress()).append(",");
            formattedStudent.append(student.getAdvisor().getTeacherID()).append(",");
            formattedStudent.append(student.getGPA()).append(","); // GPA formatted to 2 decimal places
            formattedStudent.append(student.getEnrollmentYear()).append(",");
            formattedStudent.append(student.getTotalCredit()).append(",");

            // Append courses if they exist, otherwise append an empty string
            for (int i = 0; i < 4; i++) {
                if (i < student.getCourses().length) {
                    formattedStudent.append(student.getCourses()[i].getCourseID());
                }
                if (i < 3) { // Add comma if it's not the last course
                    formattedStudent.append(",");
                }
            }
            // Write the formatted student details to the file
            writer.write("\n" + formattedStudent);
            writer.close();
        } catch (IOException e) {
            System.out.println("An error occurred while adding the student to the file.");
            e.printStackTrace();
        }
    }

    public void handleSignInRequests() {
        int innerChoice;
        Scanner sc = new Scanner(System.in);
        boolean exit = false;
        do {
            checkSignInRequests();
            System.out.println("(1) Manage sign-in requests.");
            System.out.println("(2) Back to Menu");
            innerChoice = sc.nextInt();

            if (innerChoice == 1) {
                System.out.println("Please choose a request that you want to add into the system!");
                Course[] c = new Course[4];
                int[] tempIndexes = {-1, -1, -1, -1};
                String[] courseIDs = {"CME1010", "CME1020", "CME1030", "CME1040"};

                for (int i = 0; i < courseCount; i++) {
                    for (int j = 0; j < courseIDs.length; j++) {
                        if (courses[i].getCourseID().equals(courseIDs[j])) {
                            c[j] = courses[i];
                            tempIndexes[j] = i;
                        }
                    }
                }

                int requestnum = sc.nextInt();
                Random random = new Random();
                int teacherSelector = random.nextInt(10) + 1;
                int teacherID = Integer.parseInt("10005101" + String.format("%02d", teacherSelector));
                User s = requests[requestnum - 1];
                int ID = Integer.parseInt("2024510" + String.format("%03d", studentCount));
                students[studentCount] = new Student(ID, s.getTCKN(), s.getName(), s.getSurname(), s.getEmail(), getAdvisor(teacherID),
                        s.getPassword(), s.getBirthDate(), s.getPhone(), s.getAddress(), c, 0, 2024, null, 0);
                studentCount++;
                for (int tempIndex : tempIndexes) {
                    if (tempIndex != -1) {
                        courses[tempIndex].addStudent(students[studentCount - 1]);
                    }
                }
                addStudentToFile(students[studentCount - 1]);
                deleteRequestAndUpdateFile(requestnum);
                System.out.println("Student Successfully Entered into the System");
            } else if (innerChoice != 2) {
                System.out.println("Invalid choice. Please try again.");
            }
        } while (innerChoice != 2);

        if (exit) {
            return; // Optionally handle an exit scenario
        }
    }

    public void objectionToExam(int t) {
        System.out.println(ANSI_BOLD + "\n------------------------- OBJECTION TO EXAM ---------------------------" + ANSI_RESET);

        String course;
        Scanner scan = new Scanner(System.in);
        boolean check;
        students[t].displayCourses();
        do {
            System.out.println("Please select the courseID: (Case sensitive)  ");
            System.out.print("CourseID: ");
            course = scan.nextLine();
            check = students[t].getCourses()[0].getCourseID().equals(course) || students[t].getCourses()[1].getCourseID().equals(course) ||
                    students[t].getCourses()[2].getCourseID().equals(course) || students[t].getCourses()[3].getCourseID().equals(course);
            if (!check) {
                System.out.println("Please enter valid course!");
            }
        }
        while (!check);
        boolean check2;
        String examType;
        students[t].displayGrade(findCourse(course));
        do {
            System.out.println("Please select exam type u want to object:  (midterm,homework,quiz,final)");
            System.out.print("Type: ");
            examType = scan.nextLine().toLowerCase();
            check2 = (examType.equals("midterm") || examType.equals("homework") || examType.equals("quiz") || examType.equals("final"));
            if (!check2) {
                System.out.println("Given type is wrong!");
            }
        }
        while (!check2);

        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter("objectionToExam.txt", true)); // true for append mode

            // Format the student details

            String formattedStudent = course + "," +
                    students[t].getStudentID() + "," +
                    examType;

            // Append courses if they exist, otherwise append an empty string
            // Write the formatted student details to the file
            writer.write("\n" + formattedStudent);
            writer.close();
        } catch (IOException e) {
            System.out.println("An error occurred while adding the student to the file.");
            e.printStackTrace();
        }
        System.out.println("Transaction Completed!");
        Scanner sc = new Scanner(System.in);
        int choice;
        System.out.println("(1) Back to Menu");
        do {
            choice = sc.nextInt();
            if (choice != 1) System.out.println(ANSI_RED + "Invalid operation!" + ANSI_RESET);
        } while (choice != 1);

    }

    public void checkObjections(int t) {
        String fileName = "objectionToExam.txt";
        try {
            BufferedReader reader = new BufferedReader(new FileReader(fileName));
            String line;
            while ((line = reader.readLine()) != null) {
                // Split the line by commas to parse the fields
                String[] parts = line.split(",");
                if (parts.length == 3) {
                    String courseID = parts[0];
                    String studentID = parts[1];
                    String examType = parts[2];

                    if (teachers[t].getCourses()[0].getCourseID().equals(courseID)) {
                        System.out.println("-------------------------------------------------------------------------");
                        System.out.println("Name: " + findStudent(Integer.valueOf(studentID)).getName());
                        System.out.println("Student Id: " + studentID);
                        System.out.println("Objected Exam: " + examType);
                        findStudent(Integer.valueOf(studentID)).displayGrade(teachers[t].getCourses()[0]);

                    }
                    if (teachers[t].getCourses()[1].getCourseID().equals(courseID)) {
                        System.out.println("-------------------------------------------------------------------------");
                        System.out.println("Name: " + findStudent(Integer.valueOf(studentID)).getName());
                        System.out.println("Student Id: " + studentID);
                        System.out.println("Objected Exam: " + examType);
                        findStudent(Integer.valueOf(studentID)).displayGrade(teachers[t].getCourses()[1]);
                    }

                } else {
                    System.out.println("Invalid line format: " + line);
                }
            }
            reader.close();
        } catch (IOException e) {
            System.out.println("An error occurred while reading the objections file.");
            e.printStackTrace();
        }
        Scanner sc = new Scanner(System.in);
        int choice;
        System.out.println("(1) Back to Menu");
        do {
            choice = sc.nextInt();
            if (choice != 1) System.out.println(ANSI_RED + "Invalid operation!" + ANSI_RESET);
        } while (choice != 1);
    }

}




