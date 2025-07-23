package apisPublicas.dummyJson;

public class Dummy {
    private String id;
    private String todo;
    private String completed;
    private String userId;

    public Dummy(String id, String todo, String completed, String userId) {
        this.id = id;
        this.todo = todo;
        this.completed = completed;
        this.userId = userId;
    }

    public Dummy() {
        super();
    }

    public String getId() {
        return id;
    }

    public String getTodo() {
        return todo;
    }

    public String getCompleted() {
        return completed;
    }

    public String getUserId() {
        return userId;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setTodo(String todo) {
        this.todo = todo;
    }

    public void setCompleted(String completed) {
        this.completed = completed;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
