public class Book {
    private int bookID;
    private String bookName;
    private String author;
    private int chapters;
    private int page;
    private int edition;
    private String courseCode; // Added this field

    public Book(int bookID, String bookName, String author, int chapters, int page, int edition, String courseCode) {
        super();
        this.bookID = bookID;
        this.bookName = bookName;
        this.author = author;
        this.chapters = chapters;
        this.page = page;
        this.edition = edition;
        this.courseCode = courseCode; // Initialize the new field
    }

    public int getBookID() {
        return bookID;
    }

    public void setBookID(int bookID) {
        this.bookID = bookID;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getChapters() {
        return chapters;
    }

    public void setChapters(int chapters) {
        this.chapters = chapters;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getEdition() {
        return edition;
    }

    public void setEdition(int edition) {
        this.edition = edition;
    }

    public String getCourseCode() {
        return courseCode; // Getter for courseCode
    }

    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode; // Setter for courseCode
    }
}



