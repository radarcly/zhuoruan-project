package Entity;

import java.util.ArrayList;

public class Homework {
    private String name;
    private int id;
    private int courseID;
    private ArrayList<ChooseQuestion> chooseQuestions = new ArrayList<>();
    private ArrayList<AnswerQuestion> answerQuestions = new ArrayList<>();
    public Homework(){}

    public Homework(String name,int id,int courseID){
        this.name = name;
        this.id = id;
        this.courseID = courseID;
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

    public int getCourseID() {
        return courseID;
    }

    public void setCourseID(int courseID) {
        this.courseID = courseID;
    }

    public ArrayList<ChooseQuestion> getChooseQuestions() {
        return chooseQuestions;
    }

    public void setChooseQuestions(ArrayList<ChooseQuestion> chooseQuestions) {
        this.chooseQuestions = chooseQuestions;
    }

    public ArrayList<AnswerQuestion> getAnswerQuestions() {
        return answerQuestions;
    }

    public void setAnswerQuestions(ArrayList<AnswerQuestion> answerQuestions) {
        this.answerQuestions = answerQuestions;
    }
}
