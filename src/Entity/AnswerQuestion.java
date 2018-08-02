package Entity;

public class AnswerQuestion {
    private int id;
    private String name;
    private int HomeworkID;
    private String answer;

    public AnswerQuestion(int id, String name, int homeworkID) {
        this.id = id;
        this.name = name;
        HomeworkID = homeworkID;
    }

    public AnswerQuestion() { }

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

    public int getHomeworkID() {
        return HomeworkID;
    }

    public void setHomeworkID(int homeworkID) {
        HomeworkID = homeworkID;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }
}
