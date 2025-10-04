public class Assignment {
	private String name;
	private Course course;
	private Date dueDate;
	private int maxPoint;
	private String description;

	public Assignment(String name, Course course, Date dueDate, int maxPoint, String description) {
		this.name = name;
		this.course = course;
		this.dueDate = dueDate;
		this.maxPoint = maxPoint;
		this.description = description;
	}

	// Getters and Setters
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Course getCourse() {
		return course;
	}

	public void setCourse(Course course) {
		this.course = course;
	}

	public Date getDueDate() {
		return dueDate;
	}

	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}

	public int getMaxPoint() {
		return maxPoint;
	}

	public void setMaxPoint(int maxPoint) {
		this.maxPoint = maxPoint;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	// Display method
	public void display() {
		System.out.println("----------------------------------------------------------");
		System.out.println("Course: " + course.getCourseID() + " " + course.getCourseName());
		System.out.println("Assignment Name: " + name);
		System.out.print("Due Date: ");
		System.out.println(dueDate.toString());
		System.out.println("Max Points: " + maxPoint);
		System.out.println("Description: " + description);
		System.out.println("----------------------------------------------------------");
	}
}