package Entity;

public class Other {
    private int id;
    private int courseID;
    private String otherPath;

    public Other(){}

    public Other(int id, int courseID, String otherPath) {
        this.id = id;
        this.courseID = courseID;
        this.otherPath = otherPath;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCourseID() {
        return courseID;
    }

    public void setCourseID(int courseID) {
        this.courseID = courseID;
    }

    public String getOtherPath() {
        return otherPath;
    }

    public void setOtherPath(String otherPath) {
        this.otherPath = otherPath;
    }
}
