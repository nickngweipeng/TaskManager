package com.example.nickng.taskmanager;

import java.io.Serializable;

/**
 * Created by 15056201 on 25/5/2017.
 */

public class Task implements Serializable {
    private int id;
    private String taskName;
    private String description;

    public Task(int id, String taskName, String description) {
        this.id = id;
        this.taskName = taskName;
        this.description = description;
    }

    public Task(String taskName, String description) {
        this.taskName = taskName;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public String getTaskName() {
        return taskName;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return id + " " + taskName + "\n" + description;
    }
}
