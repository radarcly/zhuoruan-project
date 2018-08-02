package Entity;

public class Resource {
    private int id;
    private int knowledgeID;
    private String resourcePath;

    public Resource(){

    }

    public Resource(int id, int knowledgeID, String resourcePath) {
        this.id = id;
        this.knowledgeID = knowledgeID;
        this.resourcePath = resourcePath;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getKnowledgeID() {
        return knowledgeID;
    }

    public void setKnowledgeID(int knowledgeID) {
        this.knowledgeID = knowledgeID;
    }

    public String getResourcePath() {
        return resourcePath;
    }

    public void setResourcePath(String resourcePath) {
        this.resourcePath = resourcePath;
    }
}
