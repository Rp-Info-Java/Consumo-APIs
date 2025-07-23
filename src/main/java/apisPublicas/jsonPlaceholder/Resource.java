package apisPublicas.jsonPlaceholder;

public class Resource {
    private String userId;
    private String id;
    private String title;
    private String body;

    public Resource() {
        super();
    }

    public String getUserId() {
        return userId;
    }

    public String getTitle() {
        return title;
    }

    public String getId() {
        return id;
    }

    public String getBody() {
        return body;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
