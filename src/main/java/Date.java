public class Date {
	private int day;
	private int month;
	private int year;

	public Date(String date) {
		String[] parts = date.split("/");
		if (parts.length == 3) {
			this.day = Integer.parseInt(parts[0]);
			this.month = Integer.parseInt(parts[1]);
			this.year = Integer.parseInt(parts[2]);
		} else {
			// Handle invalid date format
			System.out.println("Invalid date format: " + date);
		}
	}

	public String toString() {
		return String.format("%02d/%02d/%04d", day, month, year);
	}

	public int getDay() {
		return day;
	}

	public void setDay(int day) {
		this.day = day;
	}

	public int getMonth() {
		return month;
	}

	public void setMonth(int month) {
		this.month = month;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}
}

