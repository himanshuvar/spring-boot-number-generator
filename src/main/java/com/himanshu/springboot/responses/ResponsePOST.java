package com.himanshu.springboot.responses;

/**
 * @author Himanshu Varshney on 12/16/2020
 */

public class ResponsePOST {
    private String task;

    public ResponsePOST(String task) {
        this.task = task;
    }

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }
}