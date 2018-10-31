package com.hourregistration.ferry.hourregistration;
import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class Task {
    private String taskId;
    private String projectName;
    private String taskName;

    public Task(String taskId, String projectName, String taskName){
        this.taskId = taskId;
        this.projectName = projectName;
        this.taskName = taskName;
    }
    public String getTaskId(){
        return taskId;
    }
    public String getTaskName(){
        return  taskName;
    }
    public String getProjectName() {return projectName;}
}

