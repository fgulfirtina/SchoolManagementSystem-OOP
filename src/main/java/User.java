


public class User {
	private int ID;
	private String name;
	private String surname;
	private String email;
	private String password;
	private Date birthDate;
	private Course[] courses;


	private String phone;
	private String TCKN;
	private String address;


	public User(int ID, String TCKN, String name, String surname, String email,
				String password, Date birthDate, String phone, String address, Course[] courses) {
		this.ID = ID;
		this.TCKN = TCKN;
		this.name = name;
		this.surname = surname;
		this.email = email;
		this.password = password;
		this.birthDate = birthDate;
		this.phone = phone;
		this.address = address;
		this.courses = courses;
	}
	public User(String TCKN, String name, String surname, String email,
				String password, Date birthDate, String phone, String address) {
		this.TCKN = TCKN;
		this.name = name;
		this.surname = surname;
		this.email = email;
		this.password = password;
		this.birthDate = birthDate;
		this.phone = phone;
		this.address = address;
	}

	public User(String email,String password) {
		this.email = email;
		this.password = password;
	}


	// Getters and Setters for each attribute
	public int getID() {
		return ID;
	}

	public void setID(int ID) {
		this.ID = ID;
	}

	public String getName() {
		return name;
	}

	public String getSurname() {
		return surname;
	}


	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Date getBirthDate() {
		return birthDate;
	}


	public Course[] getCourses() {
		return courses;
	}

	public void setCourses(Course[] courses) {
		this.courses = courses;
	}


	public void display() {
		System.out.println("----------------------------------------------------------");
		System.out.println("TCKN: " + TCKN);
		System.out.println("Name: " + name);
		System.out.println("Surname: " + surname);
		System.out.println("Email: " + email);
		System.out.println("Password: " + password);
		System.out.println("Birth Date: " + birthDate);
		System.out.println("Phone: " + phone);
		System.out.println("Address: " + address);
		System.out.println("----------------------------------------------------------");
	}


    public String getTCKN() {
		return TCKN;
    }

	public String getPhone() {
		return phone;
	}

	public String getAddress() {
		return address;
	}
}
