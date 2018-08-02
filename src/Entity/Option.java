package Entity;

public class Option {
    private int id;
    private String name;
    private int questionID;

    public Option(){}

    public Option(int id, String name,int questionID) {
        this.id = id;
        this.name = name;
        this.questionID = questionID;
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

    public int getQuestionID() {
        return questionID;
    }

    public void setQuestionID(int questionID) {
        this.questionID = questionID;
    }
}
