package com.himanshu.springboot.requests;

import com.himanshu.springboot.model.Task;

import java.util.List;

/**
 * @author Himanshu Varshney on 12/16/2020
 */


public class BulkRequest {
    private List<Task> tasks;

    public BulkRequest() {
    }

    public BulkRequest(List<Task> tasks) {
        this.tasks = tasks;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }

    @Override
    public String toString() {
        return "BulkRequest{" +
                "tasks=" + tasks +
                '}';
    }
}