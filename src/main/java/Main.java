public class Main {
    public static void main(String[] args) {
        // SINGLETON PATTERN DESIGN - Private static area that holds only one instance
        SchoolManagementSystem school = SchoolManagementSystem.getInstance();
        school.Menu();
    }
}

