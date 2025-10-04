public class Attendance {
    private String courseID;
    private String[] presence;

    public Attendance(String courseID,String[] presence) {
        this.courseID = courseID;
        this.presence = presence;
    }

    public String getCourseID() {
        return courseID;
    }

    public void setCourseID(String courseID) {
        this.courseID = courseID;
    }

    public String[] getPresence() {
        return presence;
    }

    public void setPresence(String[] presence) {
        this.presence = presence;
    }

    public void setPresenceAt(int index, String value) {
        if (index >= 0 && index < presence.length) {
            presence[index] = value;
        } else {
            throw new IndexOutOfBoundsException("Invalid index: " + index);
        }
    }
}
