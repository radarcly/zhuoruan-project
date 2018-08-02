package Entity;

import java.util.ArrayList;

public class ChooseQuestion {
    private int id;
    private String name;
    private int homeworkID;
    private ArrayList<Option> options = new ArrayList<Option>();
    private int optionID;

    public ChooseQuestion(int id, String name,int homeworkID) {
        this.id = id;
        this.name = name;
        this.homeworkID = homeworkID;
    }

    public ChooseQuestion() {}

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

    public ArrayList<Option> getOptions() {
        return options;
    }

    public void setOptions(ArrayList<Option> options) {
        this.options = options;
    }

    public int getHomeworkID() {
        return homeworkID;
    }

    public void setHomeworkID(int homeworkID) {
        this.homeworkID = homeworkID;
    }

    public int getOptionID() {
        return optionID;
    }

    public void setOptionID(int optionID) {
        this.optionID = optionID;
    }
}
