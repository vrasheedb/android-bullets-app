package univeral.oya.bullets;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class TaskDB extends RealmObject {

    @PrimaryKey
    private int id;

    private String Task;
    private String Body;
    private Boolean Completed;

    public TaskDB(){

    }

    public TaskDB(int id,String task, String body, Boolean completed) {
        this.id = id;
        this.Task = task;
        this.Body = body;
        this.Completed = completed;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getTask() { return  Task; }
    public void setTask(String task) {
        Task = task;
    }

    public String getBody() { return Body; }
    public void setBody(String body) { Body = body; }

    public Boolean getCompleted() { return Completed; }
    public void setCompleted(Boolean completed) { Completed = completed; }
}
