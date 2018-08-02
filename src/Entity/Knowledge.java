package Entity;

import java.util.ArrayList;

public class Knowledge {
    private String name;
    private int id;
    private int chapterID;
    private ArrayList<Resource> resources;

    public Knowledge(){
        resources = new ArrayList<>();
    }

    public Knowledge(String name,int id,int chapterID){
        this.name = name;
        this.id = id;
        this.chapterID = chapterID;
        resources = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getChapterID() {
        return chapterID;
    }

    public void setChapterID(int chapterID) {
        this.chapterID = chapterID;
    }

    public ArrayList<Resource> getResources() {
        return resources;
    }

    public void setResources(ArrayList<Resource> resources) {
        this.resources = resources;
    }
}
