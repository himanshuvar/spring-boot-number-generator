package com.himanshu.springboot.responses;

/**
 * @author Himanshu Varshney on 12/16/2020
 */

public class ResponseGetStatus {
    private String status;

    public ResponseGetStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
