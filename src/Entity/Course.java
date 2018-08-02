package Entity;
import java.util.ArrayList;
import java.util.Date;
public class Course {
    private int id;
    private String name;
    private String description;
    private String imgPath;
    private int teacherID;
    private Date startTime;
    private ArrayList<Chapter> chapters;
    private ArrayList<Homework> homeworks;
    private ArrayList<Other> others;
    private int studentNum;

    public Course(){
        this.chapters = new ArrayList<>();
        this.homeworks = new ArrayList<>();
        this.studentNum=0;
    }

    public Course(int id, String name, String description, String imgPath, int teacherID, Date startTime) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.imgPath = imgPath;
        this.teacherID = teacherID;
        this.startTime = startTime;
        this.chapters = new ArrayList<>();
        this.homeworks = new ArrayList<>();
        this.studentNum = 0;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

    public int getTeacherID() {
        return teacherID;
    }

    public void setTeacherID(int teacherID) {
        this.teacherID = teacherID;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public void addChapter(Chapter chapter){
        this.chapters.add(chapter);
    }

    public void addHomework(Homework homework){
        this.homeworks.add(homework);
    }

    public ArrayList<Chapter> getChapters() {
        return chapters;
    }

    public void setChapters(ArrayList<Chapter> chapters) {
        this.chapters = chapters;
    }

    public ArrayList<Homework> getHomeworks() {
        return homeworks;
    }

    public void setHomeworks(ArrayList<Homework> homeworks) {
        this.homeworks = homeworks;
    }

    public int getStudentNum() {
        return studentNum;
    }

    public void setStudentNum(int studentNum) {
        this.studentNum = studentNum;
    }

    public ArrayList<Other> getOthers() {
        return others;
    }

    public void setOthers(ArrayList<Other> others) {
        this.others = others;
    }
}
