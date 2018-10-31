package com.hourregistration.ferry.hourregistration;
import com.google.firebase.database.IgnoreExtraProperties;


@IgnoreExtraProperties
public class Project {
    private String projectId;
    private String projectName;
    private Double time;

    public Project(String projectID, String projectName, Double time){
        this.projectId = projectID;
        this.projectName = projectName;
        this.time = time;
    }
    public String getProjectId(){
        return projectId;
    }
    public String getProjectName(){
        return  projectName;
    }
    public Double getTime() {return  time;}


}

