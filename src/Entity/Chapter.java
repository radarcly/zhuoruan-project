package Entity;

import java.util.ArrayList;

public class Chapter {
    private String name;
    private ArrayList<Knowledge> knowledges;
    private int courseID;
    private int id;

    public Chapter(){
        knowledges = new ArrayList<>();
    }
    public Chapter(String name,int id,int courseID){
        this.id = id;
        this.name = name;
        this.courseID = courseID;
        knowledges = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Knowledge> getKnowledges() {
        return knowledges;
    }

    public void setKnowledges(ArrayList<Knowledge> knowledges) {
        this.knowledges = knowledges;
    }

    public void addKnoeledge(Knowledge knowledge){
        knowledges.add(knowledge);
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
}
